package com.leetreader.leetReader.dto;

import com.leetreader.leetReader.model.Comment;

import java.time.LocalDateTime;
import java.util.List;

public record ArticleDTO(
        UserDTO userDTO,
        String title,
        String content,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        List<Comment> comments
) {
}
