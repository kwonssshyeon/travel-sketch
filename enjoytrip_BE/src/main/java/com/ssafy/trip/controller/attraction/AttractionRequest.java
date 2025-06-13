package com.ssafy.trip.controller.attraction;

import com.ssafy.trip.common.dto.PageRequest;
import com.ssafy.trip.common.dto.Sort;
import com.ssafy.trip.domain.attraction.dto.AttractionSearchCondition;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class AttractionRequest {

	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class SearchCondition {
		private Integer sidoCode;
		private Integer gugunCode;
		private Integer categoryId;
	
		public AttractionSearchCondition toEntity() {
			return AttractionSearchCondition.builder()
					.sidoCode(sidoCode)
					.gugunCode(gugunCode)
					.categoryId(categoryId)
					.build();
		}
	}

	@Getter
	@AllArgsConstructor
	public static class PagingCondition {
		private Integer sidoCode;
		private Integer gugunCode;
		private Integer categoryId;
		private String sort;


		public AttractionSearchCondition toEntity(PageRequest pageRequest) {
			return AttractionSearchCondition.builder()
					.sidoCode(sidoCode)
					.gugunCode(gugunCode)
					.categoryId(categoryId)
					.sort(sort == null ? null : Sort.POPULAR)
					.offset(pageRequest.getOffset())
					.size(pageRequest.getSize())
					.build();
		}
	}

	@Getter
	@AllArgsConstructor
	public static class Nearby {
		private Double lat;
		private Double lon;
		private Integer distance = 1000; // 기본 1km
		private Integer categoryId;
	}
	
}
