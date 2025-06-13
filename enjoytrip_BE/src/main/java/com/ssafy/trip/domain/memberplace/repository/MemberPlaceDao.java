package com.ssafy.trip.domain.memberplace.repository;

import java.util.List;

import com.ssafy.trip.domain.memberplace.dto.MemberPlaceRecord;
import com.ssafy.trip.domain.memberplace.model.MemberPlace;
import com.ssafy.trip.domain.memberplace.dto.MemberPlaceSearchCondition;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberPlaceDao {
	
	List<MemberPlaceRecord> selectAll(MemberPlaceSearchCondition condition);
	
	Long countAll(MemberPlaceSearchCondition condition);

	MemberPlaceRecord select(Long id);
	
	Integer insert(MemberPlace mp);
	
	Integer update(MemberPlace mp);
	
	Integer delete(Long id);

	Integer updateLike(MemberPlace mp);
}
