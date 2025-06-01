package com.leetreader.leetReader.service;

import com.leetreader.leetReader.dto.comment.CreateCommentDTO;
import com.leetreader.leetReader.exception.article.ArticleIsNotExist;
import com.leetreader.leetReader.exception.user.ForbiddenException;
import com.leetreader.leetReader.model.Article;
import com.leetreader.leetReader.model.Comment;
import com.leetreader.leetReader.model.User;
import com.leetreader.leetReader.repository.ArticleRepository;
import com.leetreader.leetReader.repository.CommentRepository;
import com.leetreader.leetReader.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;

    public CommentService(CommentRepository commentRepository, UserRepository userRepository, ArticleRepository articleRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.articleRepository = articleRepository;
    }

    public List<Comment> getComments() {
        return commentRepository.findAll();
    }

    public List<Comment> getCommentByArticle(Long articleId) {
        return commentRepository.findByArticleId(articleId);
    }

    public List<Comment> getCommentByUserAndArticle(Long articleId, Long userId) {
        return commentRepository.findByArticleIdAndUserId(articleId, userId);
    }

    public void addComment(Long userId, Long articleId, CreateCommentDTO commentDTO) {
        Comment comment = new Comment();
        comment.setUser(findUserById(userId));
        comment.setArticle(findArticleById(articleId));
        comment.setText(commentDTO.text());
        comment.setCreatedAt(LocalDateTime.now());
        commentRepository.save(comment);
    }

//    public Comment updateComment(Comment comment) {
//        return commentRepository.save(comment);
//    }

    public void deleteCommentById(Long commentId) {

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("The comment is not found"));
        String commentUsername = comment.getUser().getUsername();
        String authenticatedUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!authenticatedUsername.equals(commentUsername))
            throw new ForbiddenException("You Don't have access to delete this comment!");
        commentRepository.deleteById(commentId);
    }

    private User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("The user with id: " + userId + " is not exist"));
    }

    private Article findArticleById(Long articleId) {
        return articleRepository.findById(articleId)
                .orElseThrow(() -> new ArticleIsNotExist("The article you comment on it is not exist"));
    }
}
