package com.ssafy.trip.domain.member.dto;

import com.ssafy.trip.common.dto.PageRequest;
import lombok.*;

@Getter
@AllArgsConstructor
@Builder
public class MemberSearchCondition {
    private String keyword;
    private String status;
    private int offset;
    private int size;

    public MemberSearchCondition(PageRequest pageRequest, String keyword, String status) {
        this.keyword = keyword;
        this.status = status;
        this.offset = pageRequest.getOffset();
        this.size = pageRequest.getSize();
    }
}
