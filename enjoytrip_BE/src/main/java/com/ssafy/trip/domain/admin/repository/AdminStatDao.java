package com.ssafy.trip.domain.admin.repository;

import com.ssafy.trip.controller.admin.stat.AdminStatResponse;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AdminStatDao {

    AdminStatResponse.Count getStatCount();

    AdminStatResponse.MemberCount getMemberCount();

    List<AdminStatResponse.MonthlyRow> getMonthlyRows();

    AdminStatResponse.CategoryCount getCategoryCount();

}
