package com.leetreader.leetReader.controller;


import com.leetreader.leetReader.model.Article;
import com.leetreader.leetReader.service.ArticleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
