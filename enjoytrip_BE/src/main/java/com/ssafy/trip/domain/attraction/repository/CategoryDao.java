package com.ssafy.trip.domain.attraction.repository;

import com.ssafy.trip.domain.attraction.model.Category;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CategoryDao {
	
	Category select(Long id);

}
