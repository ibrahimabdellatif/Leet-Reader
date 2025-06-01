package com.leetreader.leetReader.service;

import com.leetreader.leetReader.config.SecurityUser;
import com.leetreader.leetReader.dto.user.UpdateUserDTO;
import com.leetreader.leetReader.dto.user.UserCreationDTO;
import com.leetreader.leetReader.dto.user.UserPasswordDTO;
import com.leetreader.leetReader.dto.user.UserResponseDTO;
import com.leetreader.leetReader.exception.user.*;
import com.leetreader.leetReader.mapper.UserDTOMapper;
import com.leetreader.leetReader.model.User;
import com.leetreader.leetReader.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {


    private final UserRepository userRepository;
    private final UserDTOMapper userDTOMapper;
    private final PasswordEncoder passwordEncoder;

    //    it use for spring security authentication
    @Override
    public UserDetails loadUserByUsername(String username) {
        var user = userRepository.findUserByUsername(username);

        return user.map(SecurityUser::new)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }

    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userDTOMapper)
                .collect(Collectors.toList());
    }

    public UserResponseDTO getUserById(Long id) {
        return userRepository.findById(id)
                .map(userDTOMapper)
                .orElseThrow(EntityNotFoundException::new);
    }

    public Optional<UserResponseDTO> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

//    public User findUserByUsername(String username) {
//        return userRepository.findUserByUsername(username).orElseThrow();
//    }

    public void updateUserImageProfile(String username, String imageUrl) {
        userRepository.updateUserImageProfile(username, imageUrl);
    }

    public UserResponseDTO createUser(UserCreationDTO user) {
        boolean userEmailIsExist = userRepository.findUserByEmail(user.email()).isPresent();
        boolean usernameIsExist = userRepository.findUserByUsername(user.username()).isPresent();

        if (usernameIsExist) throw new UsernameIsExist("This username is exist before try another one");
        if (userEmailIsExist) throw new UserEmailIsExist("This email is exist before try another one");
        User savedUser = new User();
        savedUser.setBio(user.bio());
        savedUser.setEmail(user.email());
        savedUser.setFirstName(user.firstName());
        savedUser.setLastName(user.lastName());
        savedUser.setPassword(passwordEncoder.encode(user.password()));
        savedUser.setUsername(user.username());
        userRepository.save(savedUser);

        UserDTOMapper mapper = new UserDTOMapper();

        return mapper.apply(savedUser);
    }

    public UserResponseDTO updateUser(String username, UpdateUserDTO updateUserDTO) {
        String authenticatedUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!authenticatedUsername.equals(username))
            throw new ForbiddenException("You don't have permission to update this user: " + username);
        User updatedUser = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("The user with username: " + username + " you try to update is not exist"));

        if (StringUtils.hasText(updateUserDTO.firstName()))
            updatedUser.setFirstName(updateUserDTO.firstName());
        if (StringUtils.hasText(updateUserDTO.lastName()))
            updatedUser.setLastName(updateUserDTO.lastName());
        if (StringUtils.hasText(updateUserDTO.bio()))
            updatedUser.setBio(updateUserDTO.bio());
        if (StringUtils.hasText(updateUserDTO.email())) {
            if (userRepository.findUserByEmail(updateUserDTO.email()).isPresent()) {
                throw new UserEmailIsExist("This email is not available!" + updateUserDTO.email());
            }
            updatedUser.setEmail(updateUserDTO.email());
        }
        userRepository.save(updatedUser);
        return userDTOMapper.apply(updatedUser);
    }

    //You never need to pass the full User entity in get request or as a returned type of update or post
    @Transactional
    public String updateUserPassword(String username, UserPasswordDTO userPasswordDTO) {
        String successfulMessage = "The user password is update successfully";

        Optional<User> user = userRepository.findUserByUsername(username);
        if (user.isEmpty()) {
            throw new EntityNotFoundException("User with username " + username + " not found");
        }
        if (!passwordEncoder.matches(userPasswordDTO.oldPassword(), userRepository.getUserPassword(username))) {
            throw new EntityNotFoundException("Old password doesn't match");
        }
        if (!Objects.equals(userPasswordDTO.newPassword(), userPasswordDTO.confirmPassword())) {
            throw new PasswordMissMatchException("The confirm password is incorrect");
        }
        if (Objects.equals(userPasswordDTO.oldPassword(), userPasswordDTO.newPassword())) {
            throw new PasswordReuseException("your new password is the same as the old password");
        }

//        String hashedPassword =
        userRepository.updateUserPassword(username, passwordEncoder.encode(userPasswordDTO.newPassword()));
        return successfulMessage;
    }
/*
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException("User with id " + id + " not found");
        }
        userRepository.deleteById(id);
    }

 */
}
