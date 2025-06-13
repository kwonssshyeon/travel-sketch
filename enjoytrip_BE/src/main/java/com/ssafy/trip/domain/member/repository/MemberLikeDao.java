package com.ssafy.trip.domain.member.repository;

import java.util.List;

import com.ssafy.trip.domain.attraction.dto.AttractionRecord;
import com.ssafy.trip.domain.member.dto.MemberLikeSearchCondition;
import org.apache.ibatis.annotations.Mapper;

import com.ssafy.trip.domain.article.model.Article;
import com.ssafy.trip.domain.memberplace.model.MemberPlace;
import com.ssafy.trip.domain.tripplan.model.TripPlan;

@Mapper
public interface MemberLikeDao {
	List<Article> selectArticleLikesByMemberId(MemberLikeSearchCondition condition);
	
	Integer countArticleLikesByMemberId(Long memberId);
	
    List<AttractionRecord> selectAttractionLikesByMemberId(MemberLikeSearchCondition condition);
    
	Integer countAttractionLikesByMemberId(Long memberId);
    
    List<MemberPlace> selectMemberPlaceLikesByMemberId(MemberLikeSearchCondition condition);
    
	Integer countMemberPlaceLikesByMemberId(Long memberId);
    
    List<TripPlan> selectTripPlanLikesByMemberId(MemberLikeSearchCondition condition);
    
	Integer countTripPlanLikesByMemberId(Long memberId);

}
