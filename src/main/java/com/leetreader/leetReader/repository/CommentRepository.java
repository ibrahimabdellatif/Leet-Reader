package com.leetreader.leetReader.repository;

import com.leetreader.leetReader.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByArticleId(Long articleId);
    List<Comment>  findByArticleIdAndUserId(Long articleId, Long userId);
}
