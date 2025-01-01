package com.star.altineller_kuyumcu.handler;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.star.altineller_kuyumcu.exception.BaseException;
import com.star.altineller_kuyumcu.exception.MessageType;
import com.star.altineller_kuyumcu.exception.ResourceNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ApiError<?>> handleBaseException(BaseException ex, WebRequest request) {
        ApiError<?> apiError = createApiError(ex.getMessage(), request);
        apiError.setStatus(HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError<?>> handleResourceNotFoundException(ResourceNotFoundException ex,
            WebRequest request) {
        ApiError<?> apiError = createApiError(ex.getMessage(), request);
        apiError.setStatus(HttpStatus.NOT_FOUND.value());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiError<?>> handleAccessDeniedException(AccessDeniedException ex, WebRequest request) {
        ApiError<?> apiError = createApiError("Access denied", request);
        apiError.setStatus(HttpStatus.FORBIDDEN.value());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(apiError);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError<Map<String, List<String>>>> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex, WebRequest request) {
        Map<String, List<String>> map = new HashMap<>();
        for (ObjectError objError : ex.getBindingResult().getAllErrors()) {
            String fieldName = ((FieldError) objError).getField();
            map.computeIfAbsent(fieldName, k -> new ArrayList<>())
                    .add(objError.getDefaultMessage());
        }
        ApiError<Map<String, List<String>>> apiError = createApiError(map, request);
        apiError.setStatus(HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.badRequest().body(apiError);
    }

    @ExceptionHandler({ java.lang.Exception.class })
    public ResponseEntity<ApiError<?>> handleGenericException(java.lang.Exception ex, WebRequest request) {
        ApiError<?> apiError = createApiError(MessageType.GENERAL_EXCEPTION.getMessage(), request);
        apiError.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiError);
    }

    private String getHostName() {
        try {
            return Inet4Address.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            return "unknown-host";
        }
    }

    public <E> ApiError<E> createApiError(E message, WebRequest request) {
        ApiError<E> apiError = new ApiError<>();
        apiError.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());

        com.star.altineller_kuyumcu.handler.Exception<E> exception = new com.star.altineller_kuyumcu.handler.Exception<>();
        exception.setPath(request.getDescription(false).substring(4));
        exception.setCreateTime(new Date());
        exception.setMessage(message);
        exception.setHostName(getHostName());

        apiError.setException(exception);

        return apiError;
    }
}
