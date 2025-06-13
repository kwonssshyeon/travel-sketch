package com.ssafy.trip.service.tripplan;

import com.ssafy.trip.domain.tripplan.dto.TripPlanStyleRecord;
import com.ssafy.trip.domain.tripplan.model.enums.TripStyle;
import com.ssafy.trip.domain.tripplan.repository.TripPlanDao;
import com.ssafy.trip.external.ai.AiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TripStyleService {
    private final TripPlanDao tripPlanDao;
    private final AiClient aiClient;

    public void generateAndSaveStyles(Long tripPlanId, String tripContent) {
//        List<TripStyle> tripStyles = aiClient.tripStyleGeneration(tripContent);
        List<TripStyle> tripStyles = List.of(TripStyle.FOODIE, TripStyle.ADVENTURE);
        if (tripStyles == null || tripStyles.isEmpty()) {
            return;
        }

        List<TripPlanStyleRecord> tripPlanStyleRecords = tripStyles.stream()
                .map(tripStyle -> new TripPlanStyleRecord(tripPlanId, tripStyle.name()))
                .toList();
        tripPlanDao.insertTripStyles(tripPlanStyleRecords);
    }
}
