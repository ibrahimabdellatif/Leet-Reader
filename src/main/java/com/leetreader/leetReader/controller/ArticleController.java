package com.leetreader.leetReader.controller;


import com.leetreader.leetReader.dto.CreateArticleRequest;
import com.leetreader.leetReader.model.Article;
import com.leetreader.leetReader.service.ArticleService;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
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
    public List<Article> getArticles() {
        return articleService.getAllArticles();
    }
//    @PostMapping("/@{username}")
//    public ResponseEntity<Article> addArticle(@PathVariable String username ,@RequestBody Article article) {
//        articleService.addArticle(username,article);
//        return ResponseEntity.status(HttpStatus.CREATED).body(article);
//    }

    @PostMapping
    public ResponseEntity<Article> addArticle(@RequestBody CreateArticleRequest request){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        var article = articleService.addArticle(username,request);
        return new ResponseEntity<>(article , HttpStatus.CREATED);
    }

}
