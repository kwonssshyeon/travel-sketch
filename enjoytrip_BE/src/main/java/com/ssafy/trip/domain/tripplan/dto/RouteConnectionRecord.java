package com.ssafy.trip.domain.tripplan.dto;

import com.ssafy.trip.domain.tripplan.model.RouteConnection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RouteConnectionRecord {
    private Long id;
    private Long fromItemId;
    private Long toItemId;
    private String distance;
    private String duration;

    public RouteConnection toModel() {
        return RouteConnection.builder()
                .id(id)
                .fromItemId(fromItemId)
                .toItemId(toItemId)
                .distance(distance)
                .duration(duration)
                .build();
    }
}
