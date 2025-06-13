package com.ssafy.trip.domain.tripplan.dto;

import com.ssafy.trip.domain.tripplan.model.TripPlan;
import com.ssafy.trip.domain.tripplan.model.TripPlanDay;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TripPlanDayRecord {
    private Long id;
    private LocalDate date;
    private int dayOrder;
    private Long tripPlanId;

    private List<PlanItemRecord> planItems;

    public TripPlanDay toModel() {
        return TripPlanDay.builder()
                .id(id)
                .date(date)
                .dayOrder(dayOrder)
                .tripPlanId(tripPlanId)
                .planItems(planItems.stream().map(PlanItemRecord::toModel).toList())
                .build();
    }

}
