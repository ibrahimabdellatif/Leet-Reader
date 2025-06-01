package com.leetreader.leetReader.dto.user;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserPasswordDTO(@NotBlank(message = "you must enter your old password") String oldPassword,
                              @NotNull(message = "the new password must be not null") @NotBlank(message = "the new password must be not empty") @Size(min = 8, max = 20, message = "The password length must be between 8 and 20") String newPassword,
                              String confirmPassword) {
}
