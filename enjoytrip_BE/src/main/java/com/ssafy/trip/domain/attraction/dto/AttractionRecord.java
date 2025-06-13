package com.ssafy.trip.domain.attraction.dto;

import com.ssafy.trip.domain.attraction.model.Attraction;
import com.ssafy.trip.domain.attraction.model.Category;
import com.ssafy.trip.domain.attraction.model.Gugun;
import com.ssafy.trip.domain.attraction.model.Sido;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AttractionRecord {

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

    private Sido sido;
    private Gugun gugun;
    private Category category;
    // 좋아요 여부 판단
    private boolean isLiked;

    public Attraction toModel() {
        return Attraction.builder()
                .id(id)
                .contentId(contentId)
                .title(title)
                .image1(image1)
                .image2(image2)
                .mapLevel(mapLevel)
                .latitude(latitude)
                .longitude(longitude)
                .tel(tel)
                .address1(address1)
                .address2(address2)
                .homepage(homepage)
                .overview(overview)
                .likeCount(likeCount)
                .build();
    }
}
