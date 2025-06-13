package com.ssafy.trip.domain.attraction.repository;

import com.ssafy.trip.domain.attraction.model.Sido;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SidoDao {

	Sido select(Long id);

	List<Sido> selectAll();

}
