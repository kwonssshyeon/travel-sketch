package com.ssafy.trip.domain.tripplan.repository;

import com.ssafy.trip.domain.tripplan.dto.TripPlanRecord;
import com.ssafy.trip.domain.tripplan.dto.TripPlanSearchCondition;
import com.ssafy.trip.domain.tripplan.dto.TripPlanStyleRecord;
import com.ssafy.trip.domain.tripplan.model.TripPlan;
import com.ssafy.trip.domain.tripplan.model.TripPlanDay;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TripPlanDao {

    List<TripPlanRecord> selectAll(TripPlanSearchCondition condition);

    List<TripPlanRecord> selectAllMine(TripPlanSearchCondition condition);

    int countAll(TripPlanSearchCondition condition);

    int countAllMine(TripPlanSearchCondition condition);

    TripPlanRecord selectSummaryById(Long id);

    TripPlanRecord selectById(Long id);

    void insert(TripPlan tripPlan);

    void update(TripPlan tripPlan);

    void delete(Long tripPlanId);

    void insertTripPlanDays(List<TripPlanDay> tripPlanDay);

    void insertTripStyles(List<TripPlanStyleRecord> tripPlanStyles);

    Long selectMostLikedId();

    void updateTripPlanDays(List<TripPlanDay> tripPlanDay);

    void deleteTripPlanDays(List<Long> tripPlanDayId);

    void updateLikeCount(TripPlan tripPlan);

    void updateViewCount(TripPlan tripPlan);
}