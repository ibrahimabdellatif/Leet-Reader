package com.leetreader.leetReader.controller;

import com.leetreader.leetReader.config.SecurityUser;
import com.leetreader.leetReader.dto.comment.CreateCommentDTO;
import com.leetreader.leetReader.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/articles/{id}/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity<?> addComment(@AuthenticationPrincipal SecurityUser securityUser, @PathVariable Long articleId, @Valid @RequestBody CreateCommentDTO commentDTO) {
        Long authenticatedUserId = securityUser.getUserId();
        commentService.addComment(authenticatedUserId, articleId, commentDTO);
        return ResponseEntity.ok().body("Comment posted successfully.😊");
    }

    @DeleteMapping("{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Long commentId){
        commentService.deleteCommentById(commentId);
        return new ResponseEntity<>("comment deleted successfully",HttpStatus.OK);
    }
}
