package com.leetreader.leetReader.service;

import com.leetreader.leetReader.model.Article;
import com.leetreader.leetReader.model.User;
import com.leetreader.leetReader.repository.ArticleRepository;
import com.leetreader.leetReader.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;

    public ArticleService(ArticleRepository articleRepository, UserRepository userRepository) {
        this.articleRepository = articleRepository;
        this.userRepository = userRepository;
    }

    public List<Article> getAllArticles() {
        return articleRepository.findAll();
    }

    public List<Article> getArticlesByUsername(Long userId) {
        return articleRepository.findByAuthorId(userId);
    }
//    you need to see if the user is exitst or not
//    public void addArticle(String username,Article article) {
////        User user = userRepository.findByUsername(username);
//        if (user == null){
//            throw new EntityNotFoundException("User not found");
//        }
//        article.setAuthor(user);
//        articleRepository.save(article);
//    }
    public Article updateArticle(Article article) {
       return articleRepository.save(article);
    }
    public void deleteArticle(Long articleId) {
        articleRepository.deleteById(articleId);
    }


}
