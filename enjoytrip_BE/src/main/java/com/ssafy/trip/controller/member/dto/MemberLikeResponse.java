package com.ssafy.trip.controller.member.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.ssafy.trip.domain.article.model.Article;
import com.ssafy.trip.domain.attraction.dto.AttractionRecord;
import com.ssafy.trip.domain.memberplace.model.MemberPlace;
import com.ssafy.trip.domain.tripplan.model.TripPlan;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class MemberLikeResponse {
	
	@Getter
	@Builder
	@AllArgsConstructor
	public static class AttractionMemberLike {
		private Long id;
		private String title;
		private String address;
		private String image;
		private String categoryName;
		
		public static AttractionMemberLike from(AttractionRecord attraction) {
			return AttractionMemberLike.builder()
						.id(attraction.getId())
						.title(attraction.getTitle())
						.image(attraction.getImage1())
						.address(attraction.getAddress1())
						.categoryName(attraction.getCategory().getCategoryName())
						.build();
		}
	}
	
	@Getter
	@Builder
	@AllArgsConstructor
	public static class TripPlanMemberLike {
		private Long id;
		private String title;
		private LocalDate startDate;
		private LocalDate endDate;
		
		public static TripPlanMemberLike from(TripPlan plan) {
			return TripPlanMemberLike.builder()
						.id(plan.getId())
						.title(plan.getTitle())
						.startDate(plan.getStartDate())
						.endDate(plan.getEndDate())
						.build();
		}
	}
	
	@Getter
	@Builder
	@AllArgsConstructor
	public static class MemberPlaceMemberLike {
		private Long id;
		private String title;
		private String address;
		private String image;
		private String categoryName;
		
		public static MemberPlaceMemberLike from(MemberPlace memberPlace) {
			return MemberPlaceMemberLike.builder()
					.id(memberPlace.getId())
					.title(memberPlace.getName())
					.address(memberPlace.getAddress())
					.image(memberPlace.getImage())
					.categoryName(memberPlace.getCategory().getCategoryName())
					.build();
		}
	}
	
	
	@Getter
	@Builder
	@AllArgsConstructor
	public static class ArticleMemberLike {
		private Long id;
		private String title;
		private LocalDateTime createdAt;
		
		public static ArticleMemberLike from(Article article) {
			return ArticleMemberLike.builder()
						.id(article.getId())
						.title(article.getTitle())
						.createdAt(article.getCreatedAt())
						.build();
		}
	}
	
	
	
}
