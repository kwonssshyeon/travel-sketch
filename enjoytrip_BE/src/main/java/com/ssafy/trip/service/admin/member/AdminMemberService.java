package com.ssafy.trip.service.admin.member;

import com.ssafy.trip.common.dto.PageResponse;
import com.ssafy.trip.common.dto.PageRequest;
import com.ssafy.trip.controller.admin.member.AdminMemberResponse;
import com.ssafy.trip.domain.member.dto.MemberRecord;
import com.ssafy.trip.domain.member.repository.MemberDao;
import com.ssafy.trip.domain.member.dto.MemberSearchCondition;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminMemberService {
    private final MemberDao memberDao;

    public PageResponse<AdminMemberResponse.Info> getAllMembers(PageRequest pageRequest, String status, String keyword) {
        MemberSearchCondition condition = new MemberSearchCondition(pageRequest, keyword, status);
        List<MemberRecord> memberList = memberDao.selectAll(condition);
        int count = memberDao.countAll(condition);
        for (MemberRecord member : memberList) {
            System.out.println(member);
        }
        return PageResponse.from(pageRequest, count, memberList, AdminMemberResponse.Info::from);
    }

    public void blockingMember(Long memberId) {
        memberDao.block(memberId);
    }

    public void unblockingMember(Long memberId) {
        memberDao.unblock(memberId);
    }

}
