package com.ssafy.trip.service.article;

import com.ssafy.trip.domain.article.model.report.CommentReport;
import com.ssafy.trip.domain.article.repository.CommentReportDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentInteractionService {
    private final CommentReportDao commentReportDao;

    public void report(Long commentId, Long memberId, String reason) {
        CommentReport report = new CommentReport(commentId, memberId, reason);
        commentReportDao.insertReport(report);
    }
}
