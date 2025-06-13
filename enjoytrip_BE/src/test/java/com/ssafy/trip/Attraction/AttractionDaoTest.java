package com.ssafy.trip.Attraction;

import java.util.List;

import com.ssafy.trip.domain.attraction.dto.AttractionRecord;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ssafy.trip.domain.attraction.repository.AttractionDao;
import com.ssafy.trip.domain.attraction.dto.AttractionSearchCondition;

@SpringBootTest
public class AttractionDaoTest {

	@Autowired AttractionDao aDao;
	
	@Test
	public void search() {
		AttractionSearchCondition condition = new AttractionSearchCondition(1, 1, 12);
		List<AttractionRecord> list = aDao.selectList(condition);
		Assertions.assertEquals(46, list.size());
	}
	
	@Test
	public void select() {
//		Attraction a = aDao.select(56644L);
//		System.out.println(a);
	}
	
}
