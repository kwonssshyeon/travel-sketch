package com.ssafy.trip.service.memberplace;

import com.ssafy.trip.domain.memberplace.dto.MemberPlaceRecord;
import com.ssafy.trip.domain.memberplace.model.MemberPlace;
import com.ssafy.trip.domain.memberplace.repository.MemberPlaceDao;
import com.ssafy.trip.domain.memberplace.model.MemberPlaceLike;
import com.ssafy.trip.domain.memberplace.repository.MemberPlaceLikeDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberPlaceInteractionService {
    private final MemberPlaceLikeDao memberPlaceLikeDao;
    private final MemberPlaceDao memberPlaceDao;

    @Transactional
    public void like(long memberPlaceId, long memberId) {
        MemberPlaceLike memberPlaceLike = new MemberPlaceLike(memberPlaceId, memberId);
        int result = memberPlaceLikeDao.exists(memberPlaceLike);
        if (result > 0) memberPlaceLikeDao.reInsert(memberPlaceLike);
        else memberPlaceLikeDao.insert(memberPlaceLike);

        MemberPlaceRecord record = memberPlaceDao.select(memberPlaceId);
        MemberPlace memberPlace = record.toModel();

        memberPlace.addLikeCount();
        memberPlaceDao.updateLike(memberPlace);
    }

    @Transactional
    public void unlike(long memberPlaceId, long memberId) {
        MemberPlaceLike memberPlaceLike = new MemberPlaceLike(memberPlaceId, memberId);
        memberPlaceLikeDao.delete(memberPlaceLike);
        MemberPlaceRecord record = memberPlaceDao.select(memberPlaceId);
        MemberPlace memberPlace = record.toModel();
        memberPlace.subLikeCount();
        memberPlaceDao.updateLike(memberPlace);
    }
}
