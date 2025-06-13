package com.ssafy.trip.service.tripplan;

import com.ssafy.trip.common.dto.PageResponse;
import com.ssafy.trip.common.dto.PageRequest;
import com.ssafy.trip.common.exception.BaseException;
import com.ssafy.trip.common.exception.ErrorCode;
import com.ssafy.trip.controller.tripplan.PlanRequest;
import com.ssafy.trip.controller.tripplan.PlanRequest.CreateDetail.TripPlanDayInfo.ItemInfo;
import com.ssafy.trip.controller.tripplan.PlanResponse;
import com.ssafy.trip.domain.tripplan.dto.PlanItemRecord;
import com.ssafy.trip.domain.tripplan.dto.TripPlanDayRecord;
import com.ssafy.trip.domain.tripplan.dto.TripPlanRecord;
import com.ssafy.trip.domain.tripplan.model.PlanItem;
import com.ssafy.trip.domain.tripplan.repository.PlanItemDao;
import com.ssafy.trip.domain.tripplan.model.RouteConnection;
import com.ssafy.trip.domain.tripplan.model.TripPlan;
import com.ssafy.trip.domain.tripplan.repository.TripPlanDao;
import com.ssafy.trip.domain.tripplan.model.TripPlanDay;
import com.ssafy.trip.domain.tripplan.dto.TripPlanSearchCondition;
import com.ssafy.trip.domain.tripplan.model.enums.Season;
import com.ssafy.trip.domain.tripplan.util.TripContentBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TripPlanService {
    private final TripPlanDao tripPlanDao;
    private final PlanItemDao planItemDao;
    private final TripStyleService tripStyleService;

    /**
     * 여행 계획 리스트 페이징 조회
     */
    public PageResponse<PlanResponse.Summary> getAllTripPlans(PlanRequest.SearchCondition condition, PageRequest pageRequest, Long loginMemberId) {
        Long userId = condition.getMine() != null ? loginMemberId : null;
        TripPlanSearchCondition searchCondition = condition.toCondition(userId, loginMemberId, pageRequest);
        
        List<TripPlanRecord> tripPlans = tripPlanDao.selectAll(searchCondition);
        int totalCount = tripPlanDao.countAll(searchCondition);
        return PageResponse.from(pageRequest, totalCount, tripPlans, PlanResponse.Summary::from);
    }

    /**
     * 진행 상태별 나의 여행 계획 리스트 조회
     */
    public PageResponse<PlanResponse.Summary> getMyPlansByStatus(String status, PageRequest pageRequest, Long memberId) {
        TripPlanSearchCondition condition = TripPlanSearchCondition.builder()
                .offset(pageRequest.getOffset())
                .size(pageRequest.getSize())
                .memberId(memberId)
                .status(status)
                .now(LocalDate.now())
                .build();
        List<TripPlanRecord> tripPlans = tripPlanDao.selectAllMine(condition);
        int totalCount = tripPlanDao.countAllMine(condition);
        return PageResponse.from(pageRequest, totalCount, tripPlans, PlanResponse.Summary::from);
    }

    /**
     * 여행 계획 등록
     */
    public PlanResponse.Summary create(PlanRequest.Create request, Long memberId) {
    	TripPlan plan = calculateTripPlanDate(request);
    	plan.setAuthor(memberId);
    	tripPlanDao.insert(plan);
    	return PlanResponse.Summary.from(plan);
    }

    /**
     * 여행 계획 등록 내부 함수 1
     * 여행 계획 날짜, 계절 계산 함수
     * 여행 총 일수가 100일을 넘는 경우 계절을 null로 설정
     */
    private TripPlan calculateTripPlanDate(PlanRequest.Create request) {
        int totalDays = (int) (request.getEndDate().toEpochDay() - request.getStartDate().toEpochDay()) + 1;
        if (totalDays <= 0) {
            throw new BaseException(ErrorCode.TRIPPLAN_INVALID_DATE);
        }
        Season season = (totalDays < 100) ? Season.from(request.getStartDate()) : null;
        return request.toEntity(totalDays, season);
    }


    /**
     * 여행 계획 상세 등록
     */
    @Transactional
    public void createDetail(PlanRequest.CreateDetail request, Long tripPlanId) {
        TripContentBuilder tripContent = new TripContentBuilder();

        TripPlan tripPlan = updateTripPlan(request, tripPlanId, tripContent);
        List<TripPlanDay> tripPlanDays = saveTripPlanDays(request.getTripPlanDayInfo(), tripPlan.getId());
        savePlanItemsWithRoutes(request.getTripPlanDayInfo(), tripPlanDays, tripContent);

        tripStyleService.generateAndSaveStyles(tripPlanId, tripContent.toString());
    }


    private TripPlan updateTripPlan(PlanRequest.CreateDetail request, Long tripPlanId, TripContentBuilder tripContent) {
        TripPlan tripPlan = request.toEntity(tripPlanId);
        tripPlanDao.update(tripPlan);
        tripContent.appendPlanInfo(tripPlan);
        return tripPlan;
    }

    private List<TripPlanDay> saveTripPlanDays(List<PlanRequest.CreateDetail.TripPlanDayInfo> dayInfos, Long tripPlanId) {
        List<TripPlanDay> tripPlanDays = dayInfos.stream()
            .map(info -> info.toEntity(tripPlanId))
            .toList();

        tripPlanDao.insertTripPlanDays(tripPlanDays);
        return tripPlanDays;
    }

    private void savePlanItemsWithRoutes(
        List<PlanRequest.CreateDetail.TripPlanDayInfo> dayInfos,
        List<TripPlanDay> tripPlanDays,
        TripContentBuilder tripContent
    ) {
        for (int i = 0; i < dayInfos.size(); i++) {
            PlanRequest.CreateDetail.TripPlanDayInfo dayInfo = dayInfos.get(i);
            Long tripPlanDayId = tripPlanDays.get(i).getId();
            saveItemsForDay(dayInfo.getItems(), tripPlanDayId, tripContent);
        }
    }

    private void saveItemsForDay(List<ItemInfo> items, Long tripPlanDayId, TripContentBuilder tripContent) {
        Long prevId = null;
        for (ItemInfo itemInfo : items) {
            PlanItem planItem = itemInfo.toEntity(tripPlanDayId, itemInfo.getAttractionId(), itemInfo.getMemberPlaceId());
            planItemDao.insert(planItem);
            tripContent.appendItem(planItem);
            if (prevId != null && itemInfo.getRouteFromPrevious() != null) {
                RouteConnection routeConnection = itemInfo.getRouteFromPrevious().toEntity(prevId, planItem.getId());
                planItemDao.insertRoute(routeConnection);
            }

            prevId = planItem.getId();
        }
    }

    @Transactional
    public void update(PlanRequest.CreateDetail request, Long tripPlanId, Long memberId) {
        // 이전 정보 삭제
        TripPlanRecord oldTripPlan = deleteForUpdate(tripPlanId);
        if (!oldTripPlan.getMemberId().equals(memberId)) {
            throw new BaseException(ErrorCode.TRIPPLAN_AUTHENTICATION_FAILED);
        }
        // 새로운 정보 저장
        TripContentBuilder tripContent = new TripContentBuilder();
        updateTripPlan(request, tripPlanId, tripContent);
        List<TripPlanDay> tripPlanDays = oldTripPlan.getTripPlanDays().stream().map(TripPlanDayRecord::toModel).toList();
        savePlanItemsWithRoutes(request.getTripPlanDayInfo(), tripPlanDays, tripContent);
    }

    public TripPlanRecord deleteForUpdate(Long tripPlanId) {
        // 이전 정보 삭제
        TripPlanRecord oldTripPlan = tripPlanDao.selectById(tripPlanId);
        List<Long> tripItemIds = oldTripPlan.getTripPlanDays().stream()
                .flatMap(tripPlanDay -> tripPlanDay.getPlanItems().stream())
                .map(PlanItemRecord::getId)
                .toList();
        planItemDao.deleteAllByIds(tripItemIds);
        return oldTripPlan;
    }

    /**
     * 여행 계획 상세 조회
     * + 조회수 증가
     */
    public PlanResponse.Info getDetail(Long id) {
    	TripPlanRecord tripPlanRecord = tripPlanDao.selectById(id);
        TripPlan tripPlan = tripPlanRecord.toModel();
        tripPlan.updateViewCount();
        tripPlanDao.updateViewCount(tripPlan);
    	return PlanResponse.Info.from(tripPlanRecord);
    }

    /**
     * 인기 여행 계획 조회 (좋아요 개수)
     */
    public PlanResponse.Info getTrendingDetail() {
        Long tripPlanId = tripPlanDao.selectMostLikedId();
        TripPlanRecord tripPlan = tripPlanDao.selectById(tripPlanId);
        return PlanResponse.Info.from(tripPlan);
    }

    /**
     * 여행 계획 삭제
     */
    public void delete(Long tripPlanId, Long memberId) {
        TripPlanRecord tripPlan = tripPlanDao.selectSummaryById(tripPlanId);
        if (!tripPlan.getMemberId().equals(memberId)) {
            throw new BaseException(ErrorCode.TRIPPLAN_AUTHENTICATION_FAILED);
        }
        tripPlanDao.delete(tripPlanId);
    }
}
