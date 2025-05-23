package com.leetreader.leetReader.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.validation.constraints.Email;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
//when stating to add spring security you need to implement userDetails interface.

@Entity
@Table(name = "users") // To avoid using reserved keyword 'user'
@Setter
@Getter
//@JsonIgnoreProperties({"hibernateLazyInitializer"})
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(nullable = false, unique = true)
    private String username;
    @Email(message = "Please enter a valid email")
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    private String bio;
    @Column(name = "profile_picture_url")
    private String profilePictureUrl;
    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @JsonManagedReference
    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
    private List<Article> articles;

    @OneToMany(mappedBy = "user" , fetch = FetchType.LAZY)
    private List<Comment> comments;

    //    Following and followed
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_follower",
            joinColumns = @JoinColumn(name = "follower_id"),
            inverseJoinColumns = @JoinColumn(name = "followee_id")
    )
    Set<User> following = new HashSet<>();

    @ManyToMany(mappedBy = "following" ,fetch = FetchType.LAZY)
    Set<User> followers = new HashSet<>();


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_authorities",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id")
    )
    private Set<Authority> authorities;

}
