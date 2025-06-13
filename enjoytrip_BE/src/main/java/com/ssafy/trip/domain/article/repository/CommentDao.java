package com.ssafy.trip.domain.article.repository;

import com.ssafy.trip.domain.article.model.comment.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentDao {

    int insert(Comment comment);

    int update(Comment comment);

    int delete(Long id);

    Comment selectById(Long id);

    List<Comment> selectByArticleId(Long articleId);

}
