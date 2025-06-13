package com.ssafy.trip.domain.attraction.dto;

import com.ssafy.trip.domain.attraction.model.Attraction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AttractionSummaryRecord {
    private Long id;
    private String name;
    private String image;
    private String address;
    private int likeCount;
    private String category;
    private double latitude;
    private double longitude;

    public Attraction toModel() {
        return Attraction.builder()
                .id(id)
                .title(name)
                .image1(image)
                .address1(address)
                .likeCount(likeCount)
                .latitude(latitude)
                .longitude(longitude)
                .build();
    }
}
