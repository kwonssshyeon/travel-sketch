package com.ssafy.trip.common.wordsearch;

import com.ssafy.trip.domain.attraction.dto.AttractionRecord;
import com.ssafy.trip.domain.attraction.repository.AttractionDao;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TrieInitializer {
    private final AttractionDao attractionDao;

    @PostConstruct
    public void init() {
        execute();
    }

    public void execute() {
        List<AttractionRecord> attractions = attractionDao.selectAllName();
        for (AttractionRecord attraction : attractions) {
            String name = attraction.getTitle();
            Long id = attraction.getId();
            SearchEngine.insert(name, id);
        }
    }
}
