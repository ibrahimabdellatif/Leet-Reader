package com.leetreader.leetReader.dto.user;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


public record UserCreationDTO(
        @NotBlank(message = "first name can not be empty.")
        @Size(min = 1, max = 25, message = "first name length 1-25 chars")
        String firstName,
        @NotBlank(message = "last name can not be empty.")
        @Size(min = 1, max = 25, message = "last name length 1-25 chars")
        String lastName,
        @NotBlank(message = "email can not be empty.")
        @Size(max = 100, message = "email size can not be 100 chars")
        @Email(message = "enter a valid email")
        String email,
        @NotBlank(message = "username can not be empty.")
        @Size(min = 3, max = 30, message = "username length 3-30 chars")
        String username,
        @NotBlank(message = "password can not be empty.")
        @Size(min = 4, max = 100, message = "username length 4-100 chars")
        String password,
        @Size(min = 1, max = 500, message = "bio length up to 500 chars")
        String bio
) {
}
