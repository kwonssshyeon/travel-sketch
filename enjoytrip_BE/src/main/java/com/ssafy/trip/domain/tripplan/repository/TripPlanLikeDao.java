package com.ssafy.trip.domain.tripplan.repository;

import com.ssafy.trip.domain.tripplan.model.TripPlanLike;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TripPlanLikeDao {
    int exists(TripPlanLike tripPlanLike);
    int reInsert(TripPlanLike tripPlanLike);
    int insert(TripPlanLike tripPlanLike);

    int delete(TripPlanLike tripPlanLike);
}
