package com.ssafy.trip.domain.member.dto;

import com.ssafy.trip.common.dto.PageRequest;
import lombok.Getter;

@Getter
public class MemberLikeSearchCondition {
	
	private final Integer offset;
	private final Integer size;
	private final Long memberId;
	
	public MemberLikeSearchCondition(PageRequest request, Long memberId) {
		this.offset = request.getOffset();
		this.size = request.getSize();
		this.memberId = memberId;
	}
	
}
