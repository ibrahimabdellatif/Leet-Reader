package com.leetreader.leetReader.controller;


import com.leetreader.leetReader.dto.CreateArticleRequest;
import com.leetreader.leetReader.model.Article;
import com.leetreader.leetReader.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
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
        var article = articleService.addArticle(username, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(article);

    }

    @PatchMapping("/{articleId}")
    public ResponseEntity<?> updateArticle(@PathVariable Long articleId, @RequestBody CreateArticleRequest articleRequest) {
        Article article = articleService.updateArticle(articleId, articleRequest);
        return ResponseEntity.ok(article);
    }

    @DeleteMapping("/{articleId}")
    public ResponseEntity<?> deleteArticle(@PathVariable Long articleId) {
        articleService.deleteArticle(articleId);
        return ResponseEntity.ok("Article deleted successfully");
    }
}
