package com.leetreader.leetReader.service;

import com.leetreader.leetReader.dto.article.CreateArticleRequest;
import com.leetreader.leetReader.exception.article.ArticleIsNotExist;
import com.leetreader.leetReader.exception.article.DuplicateTitleException;
import com.leetreader.leetReader.exception.article.InvalidEmptyInputException;
import com.leetreader.leetReader.model.Article;
import com.leetreader.leetReader.model.User;
import com.leetreader.leetReader.repository.ArticleRepository;
import com.leetreader.leetReader.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleService {

    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository, UserRepository userRepository) {
        this.articleRepository = articleRepository;
        this.userRepository = userRepository;
    }

    public List<Article> getAllArticles() {
        return articleRepository.findAll();
    }

    public List<Article> getArticlesByUsername(String username) {
        return articleRepository.findArticlesByAuthor_Username(username);
    }

    public Optional<Article> getArticleByTitle(String title, String username) {
        Optional<Article> article = articleRepository.findArticleByTitleAndAuthor_Username(title, username);

        if (article.isPresent()) {
            System.out.println("The Article is found 😊 the title:" + title + " The username: " + username);
        } else System.out.println("The Article is not found 😒the title:" + title + " The username: " + username);
        return article;
    }

    @Transactional
    public Article addArticle(String username, CreateArticleRequest article) throws UsernameNotFoundException, DuplicateTitleException {
        User author = userRepository.findUserByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("username is not found"));

        Article newArticle = new Article();

        isArticleTitleExistsForUser(article.title(), username);

        newArticle.setAuthor(author);
        newArticle.setTitle(article.title());
        newArticle.setContent(article.content());
        newArticle.setCreatedAt(LocalDateTime.now());
        return articleRepository.save(newArticle);
    }

    public Article updateArticle(Long articleId, CreateArticleRequest article) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        isArticleTitleExistsForUser(article.title(), username);

        Article existedArticle = authorizeUserAndExistingOfArticle(articleId, "update");

//        StringUtils is a class add by spring to check for null and empty string
        if (StringUtils.hasText(article.title())) existedArticle.setTitle(article.title());
        if (StringUtils.hasText(article.content())) existedArticle.setContent(article.content());

        if (!StringUtils.hasText(article.title()) && !StringUtils.hasText(article.content())) {
            throw new InvalidEmptyInputException("you must update at least one field.");
        }
        existedArticle.setUpdatedAt(LocalDateTime.now());
        return articleRepository.save(existedArticle);
    }

    @Transactional
    public void deleteArticle(Long articleId) {

        authorizeUserAndExistingOfArticle(articleId, "delete");
        articleRepository.deleteById(articleId);
    }

    private Article authorizeUserAndExistingOfArticle(Long articleId, String action) {
        String authenticatedUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        User authenitcatedUser = userRepository.findUserByUsername(authenticatedUsername)
                .orElseThrow(() -> new UsernameNotFoundException("This user is not found"));

        Article existedArticle = articleRepository.findById(articleId)
                .orElseThrow(() -> new ArticleIsNotExist("the article you try to " + action + " is not exist."));

        if (!authenitcatedUser.getId().equals(existedArticle.getAuthor().getId())) {
            throw new AccessDeniedException("You don't have access to " + action + " this resource⛔");
        }
        return existedArticle;
    }

    private void isArticleTitleExistsForUser(String articleTitle, String username) {
        Optional<Article> articleByTitleAndAuthorUsername = articleRepository.findArticleByTitleAndAuthor_Username(articleTitle, username);
        if (articleByTitleAndAuthorUsername.isPresent()) {
            throw new DuplicateTitleException("⚠ You've used this title before. Please try another one!");
        }
    }
}
