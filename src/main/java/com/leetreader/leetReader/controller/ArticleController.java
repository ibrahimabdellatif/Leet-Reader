package com.leetreader.leetReader.controller;


import com.leetreader.leetReader.dto.CreateArticleRequest;
import com.leetreader.leetReader.exception.article.ArticleIsNotExist;
import com.leetreader.leetReader.exception.article.DuplicateTitleException;
import com.leetreader.leetReader.exception.article.InvalidEmptyInputException;
import com.leetreader.leetReader.model.Article;
import com.leetreader.leetReader.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/v1/articles")
public class ArticleController {
    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping
    public List<Article> getArticles() {
        return articleService.getAllArticles();
    }

    @GetMapping("/@{username}")
    public List<Article> getArticlesByUsername(@PathVariable String username) {
        return articleService.getArticlesByUsername(username);
    }

    @GetMapping("/@{username}/{title}")
    public Optional<Article> getArticleByTitle(@PathVariable String username, @PathVariable String title) {
        return articleService.getArticleByTitle(title, username);
    }

    @PostMapping
    public ResponseEntity<?> addArticle(@RequestBody CreateArticleRequest request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        try {

            var article = articleService.addArticle(username, request);
            return ResponseEntity.status(HttpStatus.CREATED).body(article);

        } catch (DuplicateTitleException e) {
            log.error("Error posting article: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server went wrong");
        }

    }

    @PatchMapping("/@{username}/{title}")
    public ResponseEntity<?> updateArticle(@PathVariable String username, @PathVariable String title, @RequestBody CreateArticleRequest articleRequest) {

        try {
            Article article = articleService.updateArticle(title, username, articleRequest);
            return ResponseEntity.status(HttpStatus.OK).body(article);
        } catch (ArticleIsNotExist exist) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exist.getMessage());
        }catch (InvalidEmptyInputException emptyInputException){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(emptyInputException.getMessage());
        }
    }

}
