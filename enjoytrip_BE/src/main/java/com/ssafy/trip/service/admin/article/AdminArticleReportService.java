package com.ssafy.trip.service.admin.article;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.trip.common.dto.PageResponse;
import com.ssafy.trip.controller.admin.article.AdminArticleRequest;
import com.ssafy.trip.controller.admin.article.AdminArticleResponse;
import com.ssafy.trip.domain.article.model.report.CommentReport;
import com.ssafy.trip.domain.article.repository.CommentReportDao;
import com.ssafy.trip.domain.article.model.enums.ReportStatus;
import com.ssafy.trip.domain.article.repository.ArticleInteractionDao;
import com.ssafy.trip.domain.article.model.report.ArticleReport;
import com.ssafy.trip.domain.article.dto.ReportSearchCondition;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminArticleReportService {
	
	private final ArticleInteractionDao articleInteractionDao;
	private final CommentReportDao commentReportDao;

	public PageResponse<AdminArticleResponse.ReportInfo> reportArticleList(AdminArticleRequest.ReportSearchCondition request) {
		ReportSearchCondition condition = request.toEntity();
		List<ArticleReport> list = articleInteractionDao.selectAll(condition);
		Long totalCount = articleInteractionDao.countAll(condition);
		return PageResponse.from(condition.getPage(), totalCount, condition.getSize(), list, AdminArticleResponse.ReportInfo::from);
	}
	
	public PageResponse<AdminArticleResponse.ReportInfo> reportCommentList(AdminArticleRequest.ReportSearchCondition request) {
		ReportSearchCondition condition = request.toEntity();
		List<CommentReport> list = commentReportDao.selectAll(condition);
		Long totalCount = commentReportDao.countAll(condition);
		return PageResponse.from(condition.getPage(), totalCount, condition.getSize(), list, AdminArticleResponse.ReportInfo::from);
	}
	
	@Transactional
	public void processArticleReport(Long reportId, AdminArticleRequest.ReportRequest request) {
		ArticleReport report = articleInteractionDao.selectReport(reportId);
		report.update(request.toStatus());
		if(request.toStatus() == ReportStatus.APPROVED) {
			articleInteractionDao.block(reportId);
		}
		articleInteractionDao.updateReport(report);
	}
	
	public void processCommentReport(Long reportId, AdminArticleRequest.ReportRequest request) {
		CommentReport report = commentReportDao.selectReport(reportId);
		report.update(request.toStatus());
		if(request.toStatus() == ReportStatus.APPROVED) {
			commentReportDao.block(reportId);
		}
		commentReportDao.updateReport(report);
	}
	
}
