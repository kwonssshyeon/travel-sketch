package com.ssafy.trip.domain.tripplan.dto;

import com.ssafy.trip.domain.tripplan.model.PlanItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PlanItemRecord {
    private Long id;
    private String description;
    private int itemOrder;
    private String itemType;
    private Long tripPlanDayId; // 부모 plan
    private Long attractionId; // nullable
    private Long memberPlaceId; // nullable

    private String address;
    private String name;
    private String image;
    private String category;
    private double latitude;
    private double longitude;
    private int likeCount;

    private RouteConnectionRecord routeFromPrevious;

    public PlanItem toModel() {
        return PlanItem.builder()
                .id(id)
                .description(description)
                .itemOrder(itemOrder)
                .itemType(itemType)
                .tripPlanDayId(tripPlanDayId)
                .attractionId(attractionId)
                .memberPlaceId(memberPlaceId)
                .routeFromPrevious(routeFromPrevious != null ? routeFromPrevious.toModel() : null)
                .build();
    }
}
