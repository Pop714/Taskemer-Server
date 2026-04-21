package net.pop.taskemer.app.advice;

import jakarta.persistence.EntityNotFoundException;
import net.pop.taskemer.utils.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.io.IOException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Handles ALL EntityNotFoundExceptions
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleEntityNotFound(EntityNotFoundException ex) {
        // Now both the HTTP status and the custom Response code match (404)
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ApiResponse<>("Error", HttpStatus.NOT_FOUND.value(), ex.getMessage())
        );
    }

    // Handles any other unhandled generic Exceptions (The 500 errors)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleGlobalException(Exception ex) {
        // Note: In production, you might not want to expose ex.getMessage() for security reasons,
        // but for development it is fine.
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ApiResponse<>("Error", HttpStatus.INTERNAL_SERVER_ERROR.value(), "An unexpected error occurred: " + ex.getMessage())
        );
    }

    // Handles ALL IOExceptions
    @ExceptionHandler(IOException.class)
    public ResponseEntity<ApiResponse<?>> handleIoException(IOException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ApiResponse<>("Error", HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage())
        );
    }

    // Handles ALL MaxUploadSizeExceededException
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ApiResponse<?>> handleMaxSizeException(MaxUploadSizeExceededException ex) {
        return ResponseEntity.status(HttpStatus.CONTENT_TOO_LARGE).body(
                new ApiResponse<>("Error", HttpStatus.CONTENT_TOO_LARGE.value(), "File is too large! Please upload a smaller file.")
        );
    }
}
