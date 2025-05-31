package com.leetreader.leetReader.dto.comment;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public record CreateCommentDTO(
        @NotNull(message = "the comment can't be null")
        @NotBlank(message = "The comment can't be empty")
        @Size(max = 1000 , message = "Text must be from 1-1000 chars") String text
) {
}
