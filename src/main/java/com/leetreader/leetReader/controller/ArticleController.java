package com.leetreader.leetReader.controller;


import com.leetreader.leetReader.dto.article.ArticleResponseDTO;
import com.leetreader.leetReader.dto.article.CreateArticleRequest;
import com.leetreader.leetReader.service.ArticleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/articles")
public class ArticleController {
    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping
    public List<ArticleResponseDTO> getArticles() {
        return articleService.getAllArticles();
    }

    @GetMapping("/@{username}")
    public List<ArticleResponseDTO> getArticlesByUsername(@PathVariable String username) {
//        here need to reformating the return of articles.
        return articleService.getArticlesByUsername(username);
    }

    @GetMapping("/@{username}/{title}")
    public ArticleResponseDTO getArticleByTitle(@PathVariable String username, @PathVariable String title) {
        return articleService.getArticleByTitle(title, username);
    }

    @PostMapping
    public ResponseEntity<?> addArticle(@RequestBody CreateArticleRequest request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        var article = articleService.addArticle(username, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(article);

    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateArticle(@PathVariable Long id, @RequestBody CreateArticleRequest articleRequest) {
        return ResponseEntity.ok(articleService.updateArticle(id, articleRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteArticle(@PathVariable Long articleId) {
        articleService.deleteArticle(articleId);
        return ResponseEntity.ok("Article deleted successfully");
    }
}
