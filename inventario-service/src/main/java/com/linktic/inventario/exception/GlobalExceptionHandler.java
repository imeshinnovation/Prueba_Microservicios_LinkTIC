package com.linktic.inventario.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InventarioNotFoundException.class)
    public ResponseEntity<ErrorWrapper> handleInventarioNotFound(InventarioNotFoundException ex) {
        ErrorResponse error = new ErrorResponse("404", "Not Found", ex.getMessage());
        return new ResponseEntity<>(new ErrorWrapper(List.of(error)), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProductoServiceException.class)
    public ResponseEntity<ErrorWrapper> handleProductoServiceException(ProductoServiceException ex) {
        ErrorResponse error = new ErrorResponse("503", "Service Unavailable", ex.getMessage());
        return new ResponseEntity<>(new ErrorWrapper(List.of(error)), HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorWrapper> handleIllegalArgument(IllegalArgumentException ex) {
        ErrorResponse error = new ErrorResponse("400", "Bad Request", ex.getMessage());
        return new ResponseEntity<>(new ErrorWrapper(List.of(error)), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorWrapper> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<ErrorResponse> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(this::mapFieldError)
                .collect(Collectors.toList());
        return new ResponseEntity<>(new ErrorWrapper(errors), HttpStatus.BAD_REQUEST);
    }

    private ErrorResponse mapFieldError(FieldError fieldError) {
        return new ErrorResponse("400", "Validation Error", fieldError.getDefaultMessage());
    }

    public static class ErrorWrapper {
        @JsonProperty("errors")
        private List<ErrorResponse> errors;

        public ErrorWrapper(List<ErrorResponse> errors) {
            this.errors = errors;
        }

        public List<ErrorResponse> getErrors() { return errors; }
        public void setErrors(List<ErrorResponse> errors) { this.errors = errors; }
    }

    public static class ErrorResponse {
        private String status;
        private String title;
        private String detail;

        public ErrorResponse(String status, String title, String detail) {
            this.status = status;
            this.title = title;
            this.detail = detail;
        }

        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }
        public String getDetail() { return detail; }
        public void setDetail(String detail) { this.detail = detail; }
    }
}