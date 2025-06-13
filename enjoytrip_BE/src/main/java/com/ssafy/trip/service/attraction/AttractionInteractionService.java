package com.ssafy.trip.service.attraction;

import com.ssafy.trip.domain.attraction.dto.AttractionRecord;
import com.ssafy.trip.domain.attraction.model.Attraction;
import com.ssafy.trip.domain.attraction.repository.AttractionDao;
import com.ssafy.trip.domain.attraction.model.AttractionLike;
import com.ssafy.trip.domain.attraction.repository.AttractionLikeDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AttractionInteractionService {
    private final AttractionLikeDao attractionLikeDao;
    private final AttractionDao attractionDao;


    public void like(long attractionId, long memberId) {
        AttractionLike attractionLike = new AttractionLike(attractionId, memberId);

        if (isAlreadyLiked(attractionLike)) attractionLikeDao.reInsert(attractionLike);
        else attractionLikeDao.insert(attractionLike);

        AttractionRecord attractionRecord = attractionDao.selectById(attractionId);
        Attraction attraction = attractionRecord.toModel();

        attraction.addLikeCount();
        attractionDao.updateLikeCount(attraction);
    }

    private boolean isAlreadyLiked(AttractionLike attractionLike) {
        return attractionLikeDao.exists(attractionLike) > 0;
    }

    public void unlike(long attractionId, long memberId) {
        AttractionLike attractionLike = new AttractionLike(attractionId, memberId);
        attractionLikeDao.delete(attractionLike);

        AttractionRecord attractionRecord = attractionDao.selectById(attractionId);
        Attraction attraction = attractionRecord.toModel();

        attraction.subLikeCount();
        attractionDao.updateLikeCount(attraction);
    }

}
