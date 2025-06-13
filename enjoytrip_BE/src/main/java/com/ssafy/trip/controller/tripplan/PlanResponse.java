package com.ssafy.trip.controller.tripplan;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ssafy.trip.domain.tripplan.dto.PlanItemRecord;
import com.ssafy.trip.domain.tripplan.dto.RouteConnectionRecord;
import com.ssafy.trip.domain.tripplan.dto.TripPlanDayRecord;
import com.ssafy.trip.domain.tripplan.dto.TripPlanRecord;
import com.ssafy.trip.domain.tripplan.model.TripPlan;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class PlanResponse {
    @Getter
    @Builder
    @AllArgsConstructor
    public static class Summary {
        private Long id;
        private String title;
        private String description;
        private LocalDate startDate;
        private LocalDate endDate;
        private int likeCount;
        private int viewCount;
        private int totalDays;
        private LocalDateTime createdAt;
        // 작성자 정보
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String memberName;
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String memberProfileUrl;
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String categories;
        // 좋아요 여부
        private boolean isLiked;

        public static Summary from(TripPlan plan) {
            return Summary.builder()
                    .id(plan.getId())
                    .title(plan.getTitle())
                    .description(plan.getDescription())
                    .startDate(plan.getStartDate())
                    .endDate(plan.getEndDate())
                    .createdAt(plan.getCreatedAt())
                    .totalDays(plan.getTotalDays())
                    .build();
        }

        public static Summary from(TripPlanRecord plan) {
            return Summary.builder()
                    .id(plan.getId())
                    .title(plan.getTitle())
                    .description(plan.getDescription())
                    .startDate(plan.getStartDate())
                    .endDate(plan.getEndDate())
                    .createdAt(plan.getCreatedAt())
                    .likeCount(plan.getLikeCount())
                    .viewCount(plan.getViewCount())
                    .totalDays(plan.getTotalDays())
                    .isLiked(plan.getIsLiked()==1)
                    .categories(plan.getTripStyles() != null ? plan.getTripStyles().stream().map(Objects::toString).collect(Collectors.joining(",")) : null)
                    .memberName(plan.getMember() != null ? plan.getMember().getName() : null)
                    .memberProfileUrl(plan.getMember() != null ? plan.getMember().getProfileImage() : null)
                    .build();
        }
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class Info {
        private Long id;
        private String title;
        private String description;
        private LocalDate startDate;
        private LocalDate endDate;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;
        // 작성자 정보
        private String memberName;
        private String memberProfileUrl;
        // 각 여행지 정보
        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        private List<DailySchedule> dailySchedules;
        // 통계
        private int likeCount;
        private int viewCount;
        private String categories;

        public static Info from(TripPlanRecord tripPlan) {
            return Info.builder()
                    .id(tripPlan.getId())
                    .title(tripPlan.getTitle())
                    .description(tripPlan.getDescription())
                    .startDate(tripPlan.getStartDate())
                    .endDate(tripPlan.getEndDate())
                    .createdAt(tripPlan.getCreatedAt())
                    .modifiedAt(tripPlan.getModifiedAt())
                    .memberName(tripPlan.getMember().getName())
                    .memberProfileUrl(tripPlan.getMember().getProfileImage())
                    .likeCount(tripPlan.getLikeCount())
                    .viewCount(tripPlan.getViewCount())
                    .categories(tripPlan.getTripStyles() == null ? null
                            : tripPlan.getTripStyles().stream().map(Objects::toString).collect(Collectors.joining(",")))
                    .dailySchedules(tripPlan.getTripPlanDays().stream()
                            .map(DailySchedule::from)
                            .toList()
                    )
                    .build();
        }

        @Getter
        @Builder
        @AllArgsConstructor
        public static class DailySchedule {
            private Long id;
            private LocalDate date;
            private int dayOrder;
            @JsonInclude(JsonInclude.Include.NON_EMPTY)
            private List<Item> items;

            public static DailySchedule from(TripPlanDayRecord day) {
                if (day == null) {
                    return null;
                }
                return DailySchedule.builder()
                        .id(day.getId())
                        .date(day.getDate())
                        .dayOrder(day.getDayOrder())
                        .items(day.getPlanItems() != null ? day.getPlanItems().stream().map(Item::from).collect(Collectors.toList()) : List.of())
                        .build();
            }

            @Getter
            @Builder
            @AllArgsConstructor
            public static class Item {
                private Long id;
                private String description;
                private int itemOrder;
                private String itemType;
                // 여행지 정보 조회를 위해 id를 포함
                private Long attractionId; // nullable
                private Long memberPlaceId; // nullable
                // 여행지 정보
                private String placeName;
                private String placeImageUrl;
                private String placeAddress;
                private String placeCategory;
                private Double latitude;
                private Double longitude;
                // 이동 경로 정보
                private Route routeFromPrevious;

                public static Item from(PlanItemRecord item) {
                    if (item == null) {
                        return null;
                    }
                    return Item.builder()
                            .id(item.getId())
                            .description(item.getDescription())
                            .itemOrder(item.getItemOrder())
                            .itemType(item.getItemType())
                            .attractionId(item.getAttractionId())
                            .memberPlaceId(item.getMemberPlaceId())
                            .placeName(item.getName())
                            .placeImageUrl(item.getImage())
                            .placeAddress(item.getAddress())
                            .placeCategory(item.getCategory())
                            .latitude(item.getLatitude())
                            .longitude(item.getLongitude())
                            .routeFromPrevious(Route.from(item.getRouteFromPrevious()))
                            .build();
                }

                @Getter
                @Builder
                @AllArgsConstructor
                public static class Route {
                    private String distance;
                    private String duration;

                    public static Route from(RouteConnectionRecord routeConnection) {
                        if (routeConnection == null) {
                            return null;
                        }
                        return Route.builder()
                                .distance(routeConnection.getDistance())
                                .duration(routeConnection.getDuration())
                                .build();
                    }
                }
            }
        }
    }
}
