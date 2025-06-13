package com.ssafy.trip.service.article;

import com.ssafy.trip.common.exception.BaseException;
import com.ssafy.trip.common.exception.ErrorCode;
import com.ssafy.trip.controller.article.comment.CommentRequest;
import com.ssafy.trip.controller.article.comment.CommentResponse;
import com.ssafy.trip.domain.article.model.comment.Comment;
import com.ssafy.trip.domain.article.repository.CommentDao;
import com.ssafy.trip.domain.member.dto.MemberRecord;
import com.ssafy.trip.domain.member.repository.MemberDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentDao commentDao;
    private final MemberDao memberDao;

    public Long create(Long articleId, CommentRequest.Create request, String email) {
        MemberRecord memberRecord = memberDao.selectByEmail(email);
        Comment comment = request.toEntity(memberRecord.getId(), articleId);
        commentDao.insert(comment);
        return comment.getId();
    }

    public void update(Long commentId, CommentRequest.Update request) {
        Comment comment = commentDao.selectById(commentId);
        if (comment == null) {
            throw new BaseException(ErrorCode.COMMENT_NOT_FOUND);
        }
        comment.updateContent(request.getContent());
        commentDao.update(comment);
    }

    public void delete(Long commentId) {
        Comment comment = commentDao.selectById(commentId);
        if (comment == null) {
            throw new BaseException(ErrorCode.COMMENT_NOT_FOUND);
        }
        commentDao.delete(commentId);
    }

    public List<CommentResponse.Info> get(Long articleId) {
        List<Comment> comments = commentDao.selectByArticleId(articleId);
        return comments.stream().map(CommentResponse.Info::from).toList();
    }

}
