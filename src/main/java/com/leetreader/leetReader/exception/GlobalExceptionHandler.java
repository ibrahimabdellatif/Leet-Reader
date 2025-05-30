package com.leetreader.leetReader.exception;

import com.leetreader.leetReader.exception.article.ArticleIsNotExist;
import com.leetreader.leetReader.exception.article.DuplicateTitleException;
import com.leetreader.leetReader.exception.article.InvalidEmptyInputException;
import com.leetreader.leetReader.exception.user.UserEmailIsExist;
import com.leetreader.leetReader.exception.user.UsernameIsExist;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ArticleIsNotExist.class)
    public ResponseEntity<?> handleArticleNotExistException(ArticleIsNotExist exception, HttpServletRequest request) {
        ErrorResponse response = getErrorResponse(HttpStatus.NOT_FOUND, exception.getMessage(), request);

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateTitleException.class)
    public ResponseEntity<?> handleDuplicateTitleException(DuplicateTitleException exception, HttpServletRequest request) {
        ErrorResponse response = getErrorResponse(HttpStatus.CONFLICT, exception.getMessage(), request);

        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidEmptyInputException.class)
    public ResponseEntity<?> handleInvalidEmptyInputException(InvalidEmptyInputException exception, HttpServletRequest request) {
        ErrorResponse response = getErrorResponse(HttpStatus.BAD_REQUEST, exception.getMessage(), request);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserEmailIsExist.class)
    public ResponseEntity<?> handleUserEmailIsExist(UserEmailIsExist exception, HttpServletRequest request) {
        ErrorResponse response = getErrorResponse(HttpStatus.CONFLICT, exception.getMessage(), request);

        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UsernameIsExist.class)
    public ResponseEntity<?> handleUsernameIsExist(UsernameIsExist exception, HttpServletRequest request) {
        ErrorResponse response = getErrorResponse(HttpStatus.CONFLICT, exception.getMessage(), request);

        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleEntityNotFoundException(EntityNotFoundException exception, HttpServletRequest request) {
        ErrorResponse response = getErrorResponse(HttpStatus.NOT_FOUND, exception.getMessage(), request);

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleAccessDeniedException(AccessDeniedException exception, HttpServletRequest request) {
        ErrorResponse response = getErrorResponse(HttpStatus.FORBIDDEN, exception.getMessage(), request);

        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException exception, HttpServletRequest request) {
        ErrorResponse response = getErrorResponse(HttpStatus.BAD_REQUEST, exception.getMessage(), request);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private ErrorResponse getErrorResponse(HttpStatus conflict, String exception, HttpServletRequest request) {
        return ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .statusCode(conflict.value())
                .error(conflict.getReasonPhrase())
                .message(exception)
                .path(request.getRequestURI())
                .build();
    }

}







