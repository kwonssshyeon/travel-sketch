package com.ssafy.trip.common.dto;

import lombok.Getter;

@Getter
public enum Sort {
    LATEST("created_at"),
    POPULAR("like_count");
    private final String value;
    Sort(String value) {
        this.value = value;
    }
}
