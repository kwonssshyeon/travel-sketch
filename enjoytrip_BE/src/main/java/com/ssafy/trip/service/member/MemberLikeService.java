package com.ssafy.trip.service.member;

import java.util.List;

import com.ssafy.trip.domain.attraction.dto.AttractionRecord;
import org.springframework.stereotype.Service;

import com.ssafy.trip.common.dto.PageResponse;
import com.ssafy.trip.common.dto.PageRequest;
import com.ssafy.trip.controller.member.dto.MemberLikeResponse;
import com.ssafy.trip.domain.article.model.Article;
import com.ssafy.trip.domain.member.repository.MemberLikeDao;
import com.ssafy.trip.domain.member.dto.MemberLikeSearchCondition;
import com.ssafy.trip.domain.memberplace.model.MemberPlace;
import com.ssafy.trip.domain.tripplan.model.TripPlan;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberLikeService {
    private final MemberLikeDao mlDao;

    public PageResponse<?> getLikedArticles(Long memberId, PageRequest request) {
        List<Article> list = mlDao.selectArticleLikesByMemberId(new MemberLikeSearchCondition(request, memberId));
        Integer totalCount = mlDao.countArticleLikesByMemberId(memberId);
        return PageResponse.from(request, totalCount, list, MemberLikeResponse.ArticleMemberLike::from);
    }

    public PageResponse<?> getLikedAttractions(Long memberId, PageRequest request) {
        List<AttractionRecord> list = mlDao.selectAttractionLikesByMemberId(new MemberLikeSearchCondition(request, memberId));
        Integer totalCount = mlDao.countAttractionLikesByMemberId(memberId);
        return PageResponse.from(request, totalCount, list, MemberLikeResponse.AttractionMemberLike::from);
    }

    public PageResponse<?> getLikedMemberPlaces(Long memberId, PageRequest request) {
    	List<MemberPlace> list = mlDao.selectMemberPlaceLikesByMemberId(new MemberLikeSearchCondition(request, memberId));
        Integer totalCount = mlDao.countMemberPlaceLikesByMemberId(memberId);
        return PageResponse.from(request, totalCount, list, MemberLikeResponse.MemberPlaceMemberLike::from);
    }

    public PageResponse<?> getLikedTripPlans(Long memberId, PageRequest request) {
    	List<TripPlan> list = mlDao.selectTripPlanLikesByMemberId(new MemberLikeSearchCondition(request, memberId));
        Integer totalCount = mlDao.countTripPlanLikesByMemberId(memberId);
        return PageResponse.from(request, totalCount, list, MemberLikeResponse.TripPlanMemberLike::from);
    }
}
