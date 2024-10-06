package com.leetreader.leetReader.dto;


public record UserDTO(
        String firstName,
        String lastName,
        String email,
        String username,
        String profilePictureUrl,
        String bio
) {
}
