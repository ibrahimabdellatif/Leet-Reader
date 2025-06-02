package com.leetreader.leetReader.mapper;

import com.leetreader.leetReader.dto.article.ArticleResponseDTO;
import com.leetreader.leetReader.model.Article;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class ArticleResponseDTOMapper implements Function<Article, ArticleResponseDTO> {
//    private final UserDTOMapper userDTOMapper;

    @Override
    public ArticleResponseDTO apply(Article article) {

        return new ArticleResponseDTO(
                article.getId(),
                article.getAuthor().getId(),
                article.getAuthor().getUsername(),
                article.getTitle(),
                article.getContent(),
                article.getCreatedAt(),
                article.getUpdatedAt(),
                article.getComments()
        );
    }
}
