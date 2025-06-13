package com.ssafy.trip.domain.attraction.repository;

import java.util.List;

import com.ssafy.trip.domain.attraction.dto.AttractionRecord;
import com.ssafy.trip.domain.attraction.dto.AttractionSearchCondition;
import com.ssafy.trip.domain.attraction.model.Attraction;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AttractionDao {
	List<AttractionRecord> selectWithPaging(AttractionSearchCondition condition);

	List<AttractionRecord> selectListNearby(AttractionSearchCondition condition);

	int getTotalCount(AttractionSearchCondition condition);

	List<AttractionRecord> selectList(AttractionSearchCondition condition);

	AttractionRecord selectDetail(Long attractionId, Long loginMemberId);

	AttractionRecord selectById(Long id);

	/**
	 * Trie 구성을 위한 관광지명, ID 조회
	 */
	List<AttractionRecord> selectAllName();

	int updateLikeCount(Attraction attraction);
	
}
