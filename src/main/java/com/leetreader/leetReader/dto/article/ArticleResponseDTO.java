package com.leetreader.leetReader.dto.article;

import com.leetreader.leetReader.dto.user.UserResponseDTO;
import com.leetreader.leetReader.model.Comment;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record ArticleResponseDTO(
        Long id,
        Long authorId,
        String authorUsername,
        String title,
        String content,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        List<Comment> comments
) {
}
