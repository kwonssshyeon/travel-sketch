package com.ssafy.trip.domain.article.repository;

import java.util.List;

import com.ssafy.trip.domain.article.model.report.CommentReport;
import org.apache.ibatis.annotations.Mapper;

import com.ssafy.trip.domain.article.dto.ReportSearchCondition;

@Mapper
public interface CommentReportDao {
    int insertReport(CommentReport report);
    
    CommentReport selectReport(Long id);
    
    int updateReport(CommentReport report);
    
    List<CommentReport> selectAll(ReportSearchCondition condition); 
    
    Long countAll(ReportSearchCondition condition);

    int block(Long id);

}
