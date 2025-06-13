package com.ssafy.trip.service.admin.article;

import java.util.List;

import com.ssafy.trip.domain.member.dto.MemberRecord;
import org.springframework.stereotype.Service;

import com.ssafy.trip.common.dto.PageResponse;
import com.ssafy.trip.common.exception.BaseException;
import com.ssafy.trip.common.exception.ErrorCode;
import com.ssafy.trip.controller.admin.article.AdminArticleRequest;
import com.ssafy.trip.controller.admin.article.AdminArticleResponse;
import com.ssafy.trip.domain.article.model.Article;
import com.ssafy.trip.domain.article.repository.ArticleDao;
import com.ssafy.trip.domain.article.dto.ArticleSearchCondition;
import com.ssafy.trip.domain.article.model.qna.ArticleAnswer;
import com.ssafy.trip.domain.article.model.qna.QnaAnswer;
import com.ssafy.trip.domain.article.repository.QnaAnswerDao;
import com.ssafy.trip.domain.article.dto.QnaArticleSearchCondition;
import com.ssafy.trip.domain.auth.model.enums.Role;
import com.ssafy.trip.domain.member.model.Member;
import com.ssafy.trip.domain.member.repository.MemberDao;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminArticleService {
	
	private final ArticleDao articleDao;
	private final MemberDao memberDao;
	private final QnaAnswerDao answerDao;

	
	public PageResponse<AdminArticleResponse.Info> selectAllByAdmin(AdminArticleRequest.SearchCondition request) {
		ArticleSearchCondition condition = request.toEntity();
		condition.showDeleted();
		List<Article> articles = articleDao.selectAll(condition);
		Long totalCount = articleDao.countAll(condition);
		return PageResponse.from(condition.getPage(), totalCount, condition.getSize(), articles, AdminArticleResponse.Info::from);
	}
		
	public AdminArticleResponse.Info selectByAdmin(Long id) {
		Article article =findArticle(id);
		return AdminArticleResponse.Info.from(article);
	}
		
	public void insertQnaAnswer(AdminArticleRequest.QnaAnswerCreate request, String email) {
		 QnaAnswer answer = answerDao.selectById(request.getAnswerId());
		 answer.update(request.getContent(), findMember(email).getId());
		 answerDao.update(answer);
	}
	
	public PageResponse<AdminArticleResponse.QnaInfo> selectAllQna(AdminArticleRequest.QnaSearchCondition request) {
		QnaArticleSearchCondition condition = request.toEntity();
		
		List<ArticleAnswer> articles = articleDao.selectQnaList(condition);
		Long totalCount = articleDao.countQna(condition);
		return PageResponse.from(condition.getPage(), totalCount, condition.getSize(), articles, AdminArticleResponse.QnaInfo::from);
	}
	
	public Long insert(AdminArticleRequest.Data request, String email) {
		Article article = request.toEntity();
		article.setMember(findMember(email));
		articleDao.insert(article);
		return article.getId();
	}
	
	public void update(AdminArticleRequest.Data request, Long id, String email) {
		Article article = findArticle(id);
		article.update(request.getTitle(), request.getContent());
		article.setMember(findMember(email));
		articleDao.update(article);
	}
	
	public void delete(Long id) {
		Article article = findArticle(id);
		articleDao.delete(article.getId());
	}
	
	private Article findArticle(Long id) {
		Article article = articleDao.select(id, true, null);
		if(article == null) {
			throw new BaseException(ErrorCode.ARTICLE_NOT_FOUND);
		}
		return article;
	}
	
	private Member findMember(String email) {
		if(email == null) {
			throw new BaseException(ErrorCode.ARTICLE_AUTHENTICATION_FAILED);
		}
		
		MemberRecord member = memberDao.selectByEmail(email);
		if(member == null || member.getRole() != Role.ADMIN) {
			throw new BaseException(ErrorCode.ARTICLE_AUTHENTICATION_FAILED);
		}
		return member.toModel();
	}

	
}
