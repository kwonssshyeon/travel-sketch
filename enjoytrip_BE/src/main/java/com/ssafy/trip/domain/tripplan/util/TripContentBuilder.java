package com.ssafy.trip.domain.tripplan.util;

import com.ssafy.trip.domain.tripplan.model.PlanItem;
import com.ssafy.trip.domain.tripplan.model.TripPlan;

public class TripContentBuilder {
    private final StringBuilder content = new StringBuilder();

    public void appendPlanInfo(TripPlan tripPlan) {
        content.append("여행제목: ").append(tripPlan.getTitle()).append("\n")
                .append("여행설명: ").append(tripPlan.getDescription()).append("\n")
                .append("계절: ").append(tripPlan.getSeason()).append("\n")
                .append("여행지 카테고리 목록: ");
    }

    public void appendItem(PlanItem planItem) {
        content.append(planItem.getItemType()).append(", ");
    }

    public String build() {
        return content.toString();
    }
}
