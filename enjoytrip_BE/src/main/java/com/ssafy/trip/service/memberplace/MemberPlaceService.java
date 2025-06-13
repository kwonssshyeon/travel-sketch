package com.ssafy.trip.service.memberplace;

import java.util.List;

import com.ssafy.trip.domain.member.dto.MemberRecord;
import com.ssafy.trip.domain.memberplace.dto.MemberPlaceRecord;
import org.springframework.stereotype.Service;

import com.ssafy.trip.common.dto.PageResponse;
import com.ssafy.trip.common.exception.BaseException;
import com.ssafy.trip.common.exception.ErrorCode;
import com.ssafy.trip.controller.memberplace.MemberPlaceRequest;
import com.ssafy.trip.controller.memberplace.MemberPlaceResponse;
import com.ssafy.trip.controller.memberplace.MemberPlaceResponse.Info;
import com.ssafy.trip.domain.attraction.model.Category;
import com.ssafy.trip.domain.attraction.repository.CategoryDao;
import com.ssafy.trip.domain.attraction.model.Gugun;
import com.ssafy.trip.domain.attraction.repository.GugunDao;
import com.ssafy.trip.domain.attraction.model.Sido;
import com.ssafy.trip.domain.attraction.repository.SidoDao;
import com.ssafy.trip.domain.member.model.Member;
import com.ssafy.trip.domain.member.repository.MemberDao;
import com.ssafy.trip.domain.memberplace.model.MemberPlace;
import com.ssafy.trip.domain.memberplace.repository.MemberPlaceDao;
import com.ssafy.trip.domain.memberplace.dto.MemberPlaceSearchCondition;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberPlaceService {
	
	private final MemberPlaceDao mpDao;
	private final MemberDao memberDao;
	private final SidoDao sidoDao;
	private final GugunDao gugunDao;
	private final CategoryDao categoryDao;
	
	public PageResponse<Info> selectAll(MemberPlaceRequest.SearchCondition request, String email) {
		MemberPlaceSearchCondition condition = request.toEntity();
		Member member = findMemberId(email);
		if(member != null) condition.setLoginMemberId(member.getId());
		List<MemberPlaceRecord> memberPlaces = mpDao.selectAll(condition);
		Long totalCount = mpDao.countAll(condition);
		return PageResponse.from(condition.getPage(), totalCount, condition.getSize(), memberPlaces, MemberPlaceResponse.Info::from);
	}
	
	
	public MemberPlaceResponse.Info select(Long id) {
		MemberPlaceRecord mp = findMemberPlace(id);
		return MemberPlaceResponse.Info.from(mp);
	}
	
	public void insert(MemberPlaceRequest.Data request, String email) {
		MemberPlace mp = request.toEntity(findSido(request.getSidoId()), findGugun(request.getGugunId()), findCategory(request.getCategoryId()));
		mp.setMember(findMemberId(email));
		mpDao.insert(mp);
	}
	
	public void update(MemberPlaceRequest.Data request, Long id, String email) {
		Member member = findMemberId(email);
		MemberPlaceRecord record = findMemberPlace(id);
		isWriter(record, member);
		MemberPlace mp = record.toModel();
		mp.update(request.getName(), request.getLatitude(), request.getLongitude(), request.getVisitedAt(), request.getDescription(), request.getAddress(), request.getImage(), findSido(request.getSidoId()), findGugun(request.getGugunId()), findCategory(request.getCategoryId()));
		mpDao.update(mp);
	}
	
	public void delete(Long id, String email) {
		Member member = findMemberId(email);
		MemberPlaceRecord mp = findMemberPlace(id);
		isWriter(mp, member);
		mpDao.delete(mp.getId());
	}
	
	
	// 핫플, 멤버 확인 
	private void isWriter(MemberPlaceRecord mp, Member member) {
		if (!mp.getMember().getId().equals(member.getId())) {
			throw new BaseException(ErrorCode.ARTICLE_AUTHENTICATION_FAILED);
		}
	}
	
	private MemberPlaceRecord findMemberPlace(Long id) {
		MemberPlaceRecord mp = mpDao.select(id);
		if(mp == null) {
			throw new BaseException(ErrorCode.MEMBERPLACE_NOT_FOUND);
		}
		return mp;
	}
	
	private Member findMemberId(String email) {
		if(email == null) return null;
		
		MemberRecord member = memberDao.selectByEmail(email);
		if(member == null) {
			throw new BaseException(ErrorCode.MEMBER_NOT_FOUND);
		}
		return member.toModel();
	}
	

	private Sido findSido(Long id) {
		if(id == null) return null;
		
		Sido sido = sidoDao.select(id);
		if(sido == null) {
			throw new BaseException(ErrorCode.SIDO_NOT_FOUND);
		}
		return sido;
	}
	
	private Gugun findGugun(Long id) {
		if(id == null) return null;
		
		Gugun gugun = gugunDao.select(id);
		if(gugun == null) {
			throw new BaseException(ErrorCode.GUGUN_NOT_FOUND);
		}
		return gugun;
	}
	
	private Category findCategory(Long id) {
		if(id == null) return null;
		
		Category category = categoryDao.select(id);
		if(category == null) {
			throw new BaseException(ErrorCode.CATEGORY_NOT_FOUND);
		}
		return category;	
	}

}
