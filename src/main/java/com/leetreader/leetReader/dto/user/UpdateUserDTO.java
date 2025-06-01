package com.leetreader.leetReader.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

@JsonIgnoreProperties(ignoreUnknown = false)
public record UpdateUserDTO(
        String firstName,
        String lastName,
        @Email(message = "You must add a valid email")
        String email,
        @Size(max = 500 , message = "you have just up to 500 chars for bio")
        String bio
) {
}
