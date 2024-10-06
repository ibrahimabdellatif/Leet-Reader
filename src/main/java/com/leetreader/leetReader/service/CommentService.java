package com.leetreader.leetReader.service;

import com.leetreader.leetReader.model.Comment;
import com.leetreader.leetReader.repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
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

    public void addComment(Comment comment){
        commentRepository.save(comment);
    }
    public Comment updateComment(Comment comment){
        return commentRepository.save(comment);
    }

    public void deleteCommentById(Long commentId){
        commentRepository.deleteById(commentId);
    }
}
