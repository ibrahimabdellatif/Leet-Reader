package com.leetreader.leetReader.repository;

import com.leetreader.leetReader.dto.UserDTO;
import com.leetreader.leetReader.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<UserDTO> findByUsername(String username);

    @Query("""
            SELECT u FROM User u WHERE u.username = :username
            """)
        Optional<User> findUserByUsername(@Param("username")String username);
    @Modifying
    @Query(value = "update User u set u.password = :password where u.username= :username")
    void updateUserPassword(@Param("username") String username, @Param("password") String password);

    @Query(value = "select u.password from User u where u.username = :username")
    String getUserPassword(@Param("username") String username);

    @Transactional
    @Modifying
    @Query(value = "UPDATE User u SET u.profilePictureUrl = :image, u.updatedAt = CURRENT_TIMESTAMP WHERE u.username= :username")
    void updateUserImageProfile(@Param("username") String username, @Param("image") String imageUrl);
}
