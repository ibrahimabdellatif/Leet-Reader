package com.leetreader.leetReader.dto.user;

import com.leetreader.leetReader.model.Article;

import java.time.LocalDateTime;
import java.util.List;

public record UserResponseDTO(
        Long id,
        String firstName,
        String lastName,
        String username,
        String email,
        String bio,
        String profilePictureUrl,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        List<Article> articles) {
}
