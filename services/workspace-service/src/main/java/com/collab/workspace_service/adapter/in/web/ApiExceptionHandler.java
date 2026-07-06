package com.collab.workspace_service.adapter.in.web;

import com.collab.workspace_service.application.exception.WorkspaceNotFoundException;
import com.collab.workspace_service.common.error.WorkspaceErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(WorkspaceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ProblemDetail handleWorkspaceNotFound(WorkspaceNotFoundException exception) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.NOT_FOUND,
                exception.getMessage()
        );
        problemDetail.setTitle("Workspace not found");
        problemDetail.setType(URI.create("https://collab-cloud-modernization/errors/workspace-not-found"));

        return problemDetail;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ProblemDetail handleIllegalArgument(IllegalArgumentException exception) {
        WorkspaceErrorCode errorCode = resolveBadRequestErrorCode(exception);

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                exception.getMessage()
        );
        problemDetail.setTitle(errorCode.defaultMessage());
        problemDetail.setType(URI.create("https://collab-cloud-modernization/errors/" + errorCode.code()));
        problemDetail.setProperty("errorCode", errorCode.code());

        return problemDetail;
    }

    private WorkspaceErrorCode resolveBadRequestErrorCode(IllegalArgumentException exception) {
        String message = exception.getMessage();

        if (message != null && message.toLowerCase().contains("page")) {
            return WorkspaceErrorCode.INVALID_PAGINATION;
        }

        return WorkspaceErrorCode.INVALID_WORKSPACE_ID;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ProblemDetail handleValidationError(MethodArgumentNotValidException exception) {
        String detail = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .findFirst()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .orElse("Request validation failed");

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                detail
        );
        problemDetail.setTitle("Validation error");
        problemDetail.setType(URI.create("https://collab-cloud-modernization/errors/validation-error"));

        return problemDetail;
    }
}