package com.ssafy.trip.domain.article.repository;

import java.util.List;

import com.ssafy.trip.domain.article.dto.ReportSearchCondition;
import com.ssafy.trip.domain.article.model.ArticleLike;
import com.ssafy.trip.domain.article.model.report.ArticleReport;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ArticleInteractionDao {
    int insert(ArticleLike articleLike);

    int delete(ArticleLike articleLike);

    int exists(ArticleLike articleLike);

    int reInsert(ArticleLike articleLike);

    int insertReport(ArticleReport articleReport);
    
    ArticleReport selectReport(Long id);
    
    int updateReport(ArticleReport articleReport);
    
    List<ArticleReport> selectAll(ReportSearchCondition condition);
    
    Long countAll(ReportSearchCondition condition);
    
    int block(Long id);
}
