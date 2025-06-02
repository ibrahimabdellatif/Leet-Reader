package com.leetreader.leetReader.repository;

import com.leetreader.leetReader.dto.article.ArticleResponseDTO;
import com.leetreader.leetReader.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    //    List<Article> findByAuthorId(Long authorId);
    List<Article> findArticlesByAuthor_Username(String username);

    //    we need to make the article found by title and the author
    Optional<Article> findArticleByTitleAndAuthor_Username(String title, String username);

    Optional<Article> findArticleByIdAndAuthor_Id(Long articleId, Long authorId);

    void deleteArticleByTitleAndAuthor_Username(String title, String username);
}
