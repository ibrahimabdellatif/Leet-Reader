package com.leetreader.leetReader.dto.article;

import com.leetreader.leetReader.dto.user.UserCreationDTO;
import com.leetreader.leetReader.model.Comment;

import java.time.LocalDateTime;
import java.util.List;

public record ArticleDTO(
        UserCreationDTO userDTO,
        String title,
        String content,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        List<Comment> comments
) {
}
