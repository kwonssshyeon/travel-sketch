package com.ssafy.trip.domain.memberplace.dto;

import com.ssafy.trip.domain.attraction.model.Category;
import com.ssafy.trip.domain.attraction.model.Gugun;
import com.ssafy.trip.domain.attraction.model.Sido;
import com.ssafy.trip.domain.member.model.Member;
import com.ssafy.trip.domain.memberplace.model.MemberPlace;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberPlaceRecord {
    private Long id;
    private LocalDate createdAt;
    private LocalDate modifiedAt;
    private String name;
    private Double latitude;
    private Double longitude;
    private LocalDate visitedAt;
    private String description;
    private String address;
    private String image;
    private int likeCount;
    private LocalDate deletedAt;
    private Boolean isLiked;

    private Member member;
    private Sido sido;
    private Gugun gugun;
    private Category category;

    public MemberPlace toModel() {
        return MemberPlace.builder()
                .id(id)
                .createdAt(createdAt)
                .modifiedAt(modifiedAt)
                .name(name)
                .latitude(latitude)
                .longitude(longitude)
                .visitedAt(visitedAt)
                .description(description)
                .address(address)
                .image(image)
                .likeCount(likeCount)
                .deletedAt(deletedAt)
                .member(member)
                .sido(sido)
                .gugun(gugun)
                .category(category)
                .build();
    }
}
