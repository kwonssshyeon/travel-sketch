package com.ssafy.trip.domain.article.repository;

import com.ssafy.trip.domain.article.model.qna.QnaAnswer;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface QnaAnswerDao {

    int insert(QnaAnswer answer);

    int update(QnaAnswer answer);

    int delete(Long id);

    QnaAnswer selectById(Long id);

}
