package com.leetreader.leetReader.service;

import com.leetreader.leetReader.dto.CreateArticleRequest;
import com.leetreader.leetReader.exception.ArticleIsNotExist;
import com.leetreader.leetReader.exception.DuplicateTitleException;
import com.leetreader.leetReader.exception.InvalidEmptyInputException;
import com.leetreader.leetReader.model.Article;
import com.leetreader.leetReader.model.User;
import com.leetreader.leetReader.repository.ArticleRepository;
import com.leetreader.leetReader.repository.UserRepository;
import jakarta.transaction.Transactional;
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

        Optional<Article> articleByTitleAndAuthorUsername = articleRepository.findArticleByTitleAndAuthor_Username(article.title(), username);
        if (articleByTitleAndAuthorUsername.isPresent()) {
            throw new DuplicateTitleException("⚠ You've used this title before. Please try another one!");
        }

        newArticle.setAuthor(author);
        newArticle.setTitle(article.title());
        newArticle.setContent(article.content());
        newArticle.setCreatedAt(LocalDateTime.now());
        return articleRepository.save(newArticle);
    }

    public Optional<Article> findArticleByTitleAndUsername(String title, String username) {
        return articleRepository.findArticleByTitleAndAuthor_Username(title, username);
    }

    public Article updateArticle(String title, String username, CreateArticleRequest article) {
        Article articleExist = findArticleByTitleAndUsername(title, username)
                .orElseThrow(() -> new ArticleIsNotExist("The article you are try to edit is not exist"));

//        StringUtils is a class add by spring to check for null and empty string
        if (StringUtils.hasText(article.title())) articleExist.setTitle(article.title());
        if (StringUtils.hasText(article.content())) articleExist.setContent(article.content());

        if (!StringUtils.hasText(article.title()) && !StringUtils.hasText(article.content())) {
            throw new InvalidEmptyInputException("you must update at least one field.");
        }
        articleExist.setUpdatedAt(LocalDateTime.now());
        return articleRepository.save(articleExist);
    }

    public void deleteArticle(Long articleId) {
        articleRepository.deleteById(articleId);
    }


}
