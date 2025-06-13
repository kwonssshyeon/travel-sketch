package com.ssafy.trip.service.tripplan;

import com.ssafy.trip.domain.tripplan.dto.TripPlanRecord;
import com.ssafy.trip.domain.tripplan.model.TripPlan;
import com.ssafy.trip.domain.tripplan.repository.TripPlanDao;
import com.ssafy.trip.domain.tripplan.model.TripPlanLike;
import com.ssafy.trip.domain.tripplan.repository.TripPlanLikeDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TripPlanInteractionService {
    private final TripPlanLikeDao tripPlanLikeDao;
    private final TripPlanDao tripPlanDao;

    public void like(long tripPlanId, long memberId) {
        TripPlanLike tripPlanLike = new TripPlanLike(tripPlanId, memberId);
        int exists = tripPlanLikeDao.exists(tripPlanLike);

        if (exists > 0) tripPlanLikeDao.reInsert(tripPlanLike);
        else tripPlanLikeDao.insert(tripPlanLike);

        TripPlanRecord record = tripPlanDao.selectSummaryById(tripPlanId);
        TripPlan tripPlan = record.toModel();

        tripPlan.addLikeCount();
        tripPlanDao.updateLikeCount(tripPlan);
    }

    public void unlike(long tripPlanId, long memberId) {
        TripPlanLike tripPlanLike = new TripPlanLike(tripPlanId, memberId);
        tripPlanLikeDao.delete(tripPlanLike);

        TripPlanRecord record = tripPlanDao.selectSummaryById(tripPlanId);
        TripPlan tripPlan = record.toModel();

        tripPlan.subLikeCount();
        tripPlanDao.updateLikeCount(tripPlan);
    }
}
