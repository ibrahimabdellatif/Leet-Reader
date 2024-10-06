package com.leetreader.leetReader.service;

import com.leetreader.leetReader.dto.UserDTO;
import com.leetreader.leetReader.dto.UserPasswordDTO;
import com.leetreader.leetReader.mapper.UserDTOMapper;
import com.leetreader.leetReader.model.User;
import com.leetreader.leetReader.repository.UserRepository;
import com.leetreader.leetReader.security.SecurityUser;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {


    private final UserRepository userRepository;
    private final UserDTOMapper userDTOMapper;

    @Override
    public UserDetails loadUserByUsername(String username) {
        var user = userRepository.findUserByUsername(username);

        return user.map(SecurityUser::new)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userDTOMapper)
                .collect(Collectors.toList());
    }

    public UserDTO getUserById(Long id) {
        return userRepository.findById(id)
                .map(userDTOMapper)
                .orElseThrow(EntityNotFoundException::new);
    }

    public Optional<UserDTO> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

//    public User findUserByUsername(String username) {
//        return userRepository.findUserByUsername(username);
//    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public UserDTO updateUser(String username, User user) {
        if (user == null || user.getId() == null) {
            throw new IllegalArgumentException("User or user id is null");
        }
        if (!userRepository.existsById(user.getId())) {
            throw new EntityNotFoundException("User with id " + user.getId() + " not found");
        }

//        UserDTO userDTO = getUserByUsername(username);

        User updatedUser = userRepository.save(user);
        return userDTOMapper.apply(updatedUser);
    }

    //You never need to pass the full User entity in get request or as a returned type of update or post
    @Transactional
    public String updateUserPassword(String username, UserPasswordDTO userPasswordDTO) {
        String successfulMessage = "The user password is update successfully";

//        TODO: check if the username was existed or not
        Optional<UserDTO> userDTO = userRepository.findByUsername(username);
        if (userDTO.isEmpty()) {
            throw new EntityNotFoundException("User with username " + username + " not found");
        }
//        TODO: check if the old password is correct
        if (!Objects.equals(userPasswordDTO.oldPassword(), userRepository.getUserPassword(username))) {
            throw new EntityNotFoundException("Old password doesn't match");
        }
//        TODO: check if the new password and confirmed one are equals
        if (!Objects.equals(userPasswordDTO.newPassword(), userPasswordDTO.confirmPassword())) {
            throw new EntityNotFoundException("The confirm password is incorrect");
        }
//        TODO: check if the old password is equal to the new one
        if (Objects.equals(userPasswordDTO.oldPassword(), userPasswordDTO.newPassword())) {
            throw new EntityNotFoundException("your new password is the same as the old password");
        }

//        String hashedPassword =
        userRepository.updateUserPassword(username, userPasswordDTO.newPassword());
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
