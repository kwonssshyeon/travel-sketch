package com.ssafy.trip.service.attraction;

import java.util.List;

import com.ssafy.trip.common.dto.PageResponse;
import com.ssafy.trip.common.dto.PageRequest;
import com.ssafy.trip.common.wordsearch.SearchEngine;
import com.ssafy.trip.domain.attraction.dto.AttractionRecord;
import com.ssafy.trip.util.GeoUtils;
import org.springframework.stereotype.Service;

import com.ssafy.trip.common.exception.BaseException;
import com.ssafy.trip.common.exception.ErrorCode;
import com.ssafy.trip.controller.attraction.AttractionRequest;
import com.ssafy.trip.controller.attraction.AttractionResponse;
import com.ssafy.trip.domain.attraction.repository.AttractionDao;
import com.ssafy.trip.domain.attraction.dto.AttractionSearchCondition;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AttractionService {

	private final AttractionDao aDao;

	public PageResponse<AttractionResponse.Info> getListWithPaging(AttractionRequest.PagingCondition request, PageRequest pageRequest) {
		AttractionSearchCondition condition = request.toEntity(pageRequest);
		List<AttractionRecord> attractions = aDao.selectWithPaging(condition);
		int totalCount = aDao.getTotalCount(condition);
		return PageResponse.from(pageRequest, totalCount, attractions, AttractionResponse.Info::from);
	}
	
	public List<AttractionResponse.Info> getList(AttractionRequest.SearchCondition request) {
		AttractionSearchCondition condition = request.toEntity();
		List<AttractionRecord> attractions = aDao.selectList(condition);
		return attractions.stream().map(AttractionResponse.Info::from).toList();
	}
	
	public AttractionResponse.Detail getById(Long attractionId, Long loginUserId) {
		AttractionRecord attraction = aDao.selectDetail(attractionId, loginUserId);
		if(attraction == null) {
			throw new BaseException(ErrorCode.ATTRACTION_NOT_FOUND);
		}
		return AttractionResponse.Detail.from(attraction);
	}

	public List<AttractionResponse.Name> searchByKeyword(String prefix) {
		return SearchEngine.search(prefix).stream()
				.map(AttractionResponse.Name::from)
				.toList();
	}

	public List<AttractionResponse.Info> getListNearby(AttractionRequest.Nearby request) {
		GeoUtils.BoxArea boxArea = GeoUtils.calculateBoxRange(request.getLat(), request.getLon(), request.getDistance());
		AttractionSearchCondition condition = AttractionSearchCondition.from(boxArea, request.getCategoryId());
		List<AttractionRecord> attractions = aDao.selectListNearby(condition);
		return attractions.stream().map(AttractionResponse.Info::from).toList();
	}
}
