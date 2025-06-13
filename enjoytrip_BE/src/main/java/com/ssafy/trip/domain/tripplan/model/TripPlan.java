package com.ssafy.trip.domain.tripplan.model;

import com.ssafy.trip.domain.tripplan.model.enums.Season;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;

@ToString
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TripPlan {
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

    public void setAuthor(Long memberId) {
    	this.memberId = memberId;
    }

    public void addLikeCount() {
        this.likeCount++;
    }
    public void updateViewCount() {
        this.viewCount+=1;
    }
    public void subLikeCount() {
        this.likeCount--;
    }
}
