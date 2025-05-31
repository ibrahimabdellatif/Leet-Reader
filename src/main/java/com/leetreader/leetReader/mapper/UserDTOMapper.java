package com.leetreader.leetReader.mapper;

import com.leetreader.leetReader.dto.user.UserCreationDTO;
import com.leetreader.leetReader.dto.user.UserResponseDTO;
import com.leetreader.leetReader.model.Article;
import com.leetreader.leetReader.model.Comment;
import com.leetreader.leetReader.model.User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.function.Function;

@Service
public class UserDTOMapper implements Function<User, UserResponseDTO> {
    @Override
    public UserResponseDTO apply(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getUsername(),
                user.getEmail(),
                user.getBio(),
                user.getProfilePictureUrl(),
                user.getCreatedAt(),
                user.getUpdatedAt(),
                user.getArticles(),
                user.getComments()
        );
    }

}
