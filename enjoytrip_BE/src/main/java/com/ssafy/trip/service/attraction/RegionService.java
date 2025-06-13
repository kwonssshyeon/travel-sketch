package com.ssafy.trip.service.attraction;

import com.ssafy.trip.domain.attraction.model.Gugun;
import com.ssafy.trip.domain.attraction.repository.GugunDao;
import com.ssafy.trip.domain.attraction.model.Sido;
import com.ssafy.trip.domain.attraction.repository.SidoDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RegionService {
    private final SidoDao sidoDao;
    private final GugunDao gugunDao;

    public List<Sido> getAllSido() {
        return sidoDao.selectAll();
    }

    public List<Gugun> getAllGugunBySidoId(int sidoId) {
        return gugunDao.selectAllBySidoId(sidoId);
    }

    public List<Gugun> getAllGugunBySidoCode(Long sidoCode) {
        return gugunDao.selectAllBySidoCode(sidoCode);
    }
}
