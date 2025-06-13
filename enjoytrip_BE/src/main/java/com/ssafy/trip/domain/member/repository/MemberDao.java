package com.ssafy.trip.domain.member.repository;

import com.ssafy.trip.domain.member.dto.MemberRecord;
import com.ssafy.trip.domain.member.model.Member;
import com.ssafy.trip.domain.member.dto.MemberSearchCondition;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberDao {

	int insert(Member member);
	MemberRecord selectById(Long id);

	MemberRecord selectByEmail(String email);
	
	int update(Member member);

	int updatePassword(Member member);
	
	int delete(Long memberId);

	List<MemberRecord> selectAll(MemberSearchCondition condition);

	int countAll(MemberSearchCondition condition);

	int block(Long memberId);

	int unblock(Long memberId);

}
