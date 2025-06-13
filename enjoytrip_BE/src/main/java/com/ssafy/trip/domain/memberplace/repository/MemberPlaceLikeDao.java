package com.ssafy.trip.domain.memberplace.repository;

import com.ssafy.trip.domain.memberplace.model.MemberPlaceLike;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberPlaceLikeDao {
    int insert(MemberPlaceLike memberPlaceLike);

    int reInsert(MemberPlaceLike memberPlaceLike);

    int exists(MemberPlaceLike memberPlaceLike);
    int delete(MemberPlaceLike memberPlaceLike);
}
