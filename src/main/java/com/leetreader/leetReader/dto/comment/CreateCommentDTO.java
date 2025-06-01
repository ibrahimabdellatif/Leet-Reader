package com.leetreader.leetReader.dto.comment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateCommentDTO(
        @NotNull(message = "the comment can't be null")
        @NotBlank(message = "The comment can't be empty")
        @Size(max = 1000, message = "Text must be from 1-1000 chars") String text
) {
}
