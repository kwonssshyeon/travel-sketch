package com.ssafy.trip.domain.article.model.qna;

import com.ssafy.trip.domain.article.model.Article;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ArticleAnswer {
	
    private Article article;
    private QnaAnswer answer;
    
}
