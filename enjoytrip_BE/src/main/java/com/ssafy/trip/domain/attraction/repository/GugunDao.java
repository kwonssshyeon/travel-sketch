package com.ssafy.trip.domain.attraction.repository;

import com.ssafy.trip.domain.attraction.model.Gugun;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GugunDao {

	Gugun select(Long id);

	List<Gugun> selectAllBySidoId(Integer sidoId);

	List<Gugun> selectAllBySidoCode(Long sidoCode);

}
