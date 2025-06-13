package com.ssafy.trip.service.member;

import com.ssafy.trip.controller.member.dto.MemberResponse;
import com.ssafy.trip.domain.member.dto.MemberRecord;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ssafy.trip.common.exception.BaseException;
import com.ssafy.trip.common.exception.ErrorCode;
import com.ssafy.trip.controller.member.dto.MemberRequest;
import com.ssafy.trip.domain.member.model.Member;
import com.ssafy.trip.domain.member.repository.MemberDao;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {
	public final MemberDao memberDao;
	public final PasswordEncoder passwordEncoder;

	/**
	 * 단일 회원 정보 조회
	 */
	public MemberResponse.Info get(Long memberId, Long loginUserId) {
		validateSelfRequest(memberId, loginUserId);

		MemberRecord memberRecord = memberDao.selectById(memberId);
		return MemberResponse.Info.from(memberRecord);
	}

	/**
	 * 회원 정보 수정 (이름, 생년월일, 프로필 이미지)
	 */
	public void update(Long memberId, MemberRequest.Update request, Long loginUserId) {
		validateSelfRequest(memberId, loginUserId);

		MemberRecord memberRecord = memberDao.selectById(memberId);
		Member member = memberRecord.toModel();

		member.update(request.getName(), request.getProfileImage(), request.getBirthDate());
		memberDao.update(member);
	}

	/**
	 * 비밀번호 변경
	 */
	public void updatePassword(Long memberId, MemberRequest.UpdatePassword request, Long loginUserId) {
		validateSelfRequest(memberId, loginUserId);

		MemberRecord memberRecord = memberDao.selectById(memberId);
		Member member = memberRecord.toModel();

		// 기존 비밀번호 확인
		if (!passwordEncoder.matches(request.getCurrentPassword(), member.getPassword())) {
            throw new BaseException(ErrorCode.UNMATCHED_PASSWORD);
		}

		member.encodePassword(passwordEncoder.encode(request.getNewPassword()));
		memberDao.updatePassword(member);
	}

	/**
	 * 회원 탈퇴
	 */
	public void delete(Long memberId, Long loginUserId){
		validateSelfRequest(memberId, loginUserId);

		MemberRecord memberRecord = memberDao.selectById(memberId);

		memberDao.delete(memberRecord.getId());
		SecurityContextHolder.getContext().setAuthentication(null);
	}

	private void validateSelfRequest(Long memberId, Long loginUserId) {
		if (!memberId.equals(loginUserId)) {
			throw new AuthorizationDeniedException("본인의 정보에만 접근할 수 있습니다.");
		}
	}
}
