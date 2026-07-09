package com.collab.workspace_service.adapter.in.web;

import com.collab.workspace_service.application.exception.WorkspaceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;

@RestControllerAdvice
public class ApiExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApiExceptionHandler.class);

    @ExceptionHandler(WorkspaceNotFoundException.class)
    public ProblemDetail handleWorkspaceNotFound(WorkspaceNotFoundException exception) {
        LOGGER.info("Workspace not found exception handled: message={}", exception.getMessage());

        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problemDetail.setTitle("Workspace not found");
        problemDetail.setDetail(exception.getMessage());
        problemDetail.setType(URI.create("https://collab-cloud-modernization/errors/workspace-not-found"));

        return problemDetail;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidationException(MethodArgumentNotValidException exception) {
        LOGGER.info("Request validation failed: errorsCount={}", exception.getErrorCount());

        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle("Validation failed");
        problemDetail.setDetail("Request payload contains invalid fields");
        problemDetail.setType(URI.create("https://collab-cloud-modernization/errors/validation-failed"));

        return problemDetail;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ProblemDetail handleIllegalArgumentException(IllegalArgumentException exception) {
        LOGGER.info("Invalid request argument: message={}", exception.getMessage());

        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle("Invalid request");
        problemDetail.setDetail(exception.getMessage());
        problemDetail.setType(URI.create("https://collab-cloud-modernization/errors/invalid-request"));

        return problemDetail;
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleUnexpectedException(Exception exception) {
        LOGGER.error("Unexpected error occurred", exception);

        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        problemDetail.setTitle("Internal server error");
        problemDetail.setDetail("An unexpected error occurred");
        problemDetail.setType(URI.create("https://collab-cloud-modernization/errors/internal-server-error"));

        return problemDetail;
    }
}