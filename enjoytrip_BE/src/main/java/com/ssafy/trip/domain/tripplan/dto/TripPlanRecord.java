package com.ssafy.trip.domain.tripplan.dto;

import com.ssafy.trip.domain.member.dto.MemberRecord;
import com.ssafy.trip.domain.tripplan.model.TripPlan;
import com.ssafy.trip.domain.tripplan.model.enums.Season;
import com.ssafy.trip.domain.tripplan.model.enums.TripStyle;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TripPlanRecord {
    private Long id;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private LocalDateTime deletedAt;
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private Long memberId;
    private int likeCount;
    private int viewCount;
    private int totalDays;
    private Season season;

    private List<TripStyle> tripStyles;
    private List<TripPlanDayRecord> tripPlanDays;
    private MemberRecord member;

    private int isLiked;


    public TripPlan toModel() {
        return TripPlan.builder()
                .id(id)
                .createdAt(createdAt)
                .modifiedAt(modifiedAt)
                .deletedAt(deletedAt)
                .title(title)
                .description(description)
                .startDate(startDate)
                .endDate(endDate)
                .memberId(memberId)
                .likeCount(likeCount)
                .viewCount(viewCount)
                .totalDays(totalDays)
                .season(season)
                .build();
    }
}
