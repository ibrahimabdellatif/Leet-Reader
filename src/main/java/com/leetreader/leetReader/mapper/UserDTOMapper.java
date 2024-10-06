package com.leetreader.leetReader.mapper;

import com.leetreader.leetReader.dto.UserDTO;
import com.leetreader.leetReader.model.User;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class UserDTOMapper implements Function<User, UserDTO> {
    @Override
    public UserDTO apply(User user) {
        return new UserDTO(
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getUsername(),
                user.getProfilePictureUrl(),
                user.getBio()
        );
    }

}
