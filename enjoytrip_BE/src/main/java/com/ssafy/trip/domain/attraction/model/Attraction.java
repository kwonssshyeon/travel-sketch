package com.ssafy.trip.domain.attraction.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Attraction {
	
	private Long id;
	private Integer contentId;
	private String title;
	private String image1;
	private String image2;
	private Integer mapLevel;
	private Double latitude;
	private Double longitude;
	private String tel;
	private String address1;
	private String address2;
	private String homepage;
	private String overview;
	private int likeCount;

	public void addLikeCount() {
		likeCount++;
	}
	public void subLikeCount() {
		likeCount--;
	}
}
