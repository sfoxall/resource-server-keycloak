package com.inventivum.resourceserver.controller;

import com.inventivum.resourceserver.exception.ErrorParameter;
import com.inventivum.resourceserver.exception.ErrorResponse;
import com.inventivum.resourceserver.exception.ResourceNotFoundException;
import com.inventivum.resourceserver.exception.UniqueConstraintException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
@ControllerAdvice
public class ExceptionHandlerController {

    public static final String RESOURCE_NOT_FOUND = "Resource Not Found";
    public static final String BAD_REQUEST_CONSTRAINT_VIOLATION = "Bad Request - Constraint Violation";
    public static final String BAD_REQUEST_METHOD_ARGUMENT_NOT_VALID = "Bad Request - Method Argument Not Valid";
    public static final String BAD_REQUEST_MISSING_REQUEST_PARAMETER = "Bad Request - Missing Request Parameter";
    public static final String INTERNAL_SERVER_ERROR = "Internal Server Error - ";
    public static final String BAD_REQUEST_HTTP_NOT_READABLE = "Bad Request - HTTP Not Readable";
    public static final String UNAUTHORIZED = "Unauthorised";
    public static final String FORBIDDEN = "Forbidden - Access Denied";

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    @ExceptionHandler({ResourceNotFoundException.class})
    protected ErrorResponse handleNotFoundException(ResourceNotFoundException exception){
        log.warn(RESOURCE_NOT_FOUND, exception);
        return new ErrorResponse(RESOURCE_NOT_FOUND, exception.getMessage(), exception.getErrors());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler({UniqueConstraintException.class})
    protected ErrorResponse handleUniqueConstraintException(UniqueConstraintException exception){
        log.warn(BAD_REQUEST_CONSTRAINT_VIOLATION, exception);
        return new ErrorResponse(BAD_REQUEST_CONSTRAINT_VIOLATION, exception.getMessage(), exception.getErrors());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(ConstraintViolationException.class)
    protected ErrorResponse handleConstraintViolationException(ConstraintViolationException exception){
        log.warn(BAD_REQUEST_CONSTRAINT_VIOLATION, exception);
        return new ErrorResponse(BAD_REQUEST_CONSTRAINT_VIOLATION, exception.getLocalizedMessage(),
                getConstraintViolationErrors(exception.getConstraintViolations()));
    }

    private List<ErrorParameter> getConstraintViolationErrors(Set<ConstraintViolation<?>> violations){
        return violations
                .stream()
                .map(v -> new ErrorParameter(getConstraintViolationParameter(v), v.getMessage()))
                .collect(Collectors.toList());
    }

    private String getConstraintViolationParameter(ConstraintViolation<?> violation){
        return Objects.requireNonNull(StreamSupport.stream(
                                violation.getPropertyPath().spliterator(), false).
                        reduce((first, second) -> second).
                        orElse(null)).
                toString();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler({MethodArgumentNotValidException.class})
    protected ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException exception){
        log.warn(BAD_REQUEST_METHOD_ARGUMENT_NOT_VALID, exception);

        var errors = exception.getBindingResult()
                .getAllErrors()
                .stream()
                .map(r -> new ErrorParameter(((FieldError) r).getField(), r.getDefaultMessage()))
                .toList();

        return new ErrorResponse(BAD_REQUEST_METHOD_ARGUMENT_NOT_VALID, exception.getMessage(), errors);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler({HttpMessageNotReadableException.class})
    protected ErrorResponse handleHttpMessageNotReadableException(HttpMessageNotReadableException exception){
        log.warn(BAD_REQUEST_HTTP_NOT_READABLE, exception);
        return new ErrorResponse(BAD_REQUEST_HTTP_NOT_READABLE, exception.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler({MissingServletRequestParameterException.class})
    protected ErrorResponse handleMissingServletRequestParameterException(MissingServletRequestParameterException exception){
        log.warn(BAD_REQUEST_MISSING_REQUEST_PARAMETER, exception);

        var message = "Request parameter " + exception.getParameterName() + " is missing";
        return new ErrorResponse(BAD_REQUEST_MISSING_REQUEST_PARAMETER, message,
                List.of(new ErrorParameter(exception.getParameterName(), message)));
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    @ExceptionHandler(AuthenticationException.class)
    public ErrorResponse handleAuthenticationException(AuthenticationException exception) {
        return new ErrorResponse(UNAUTHORIZED, exception.getMessage());
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    @ExceptionHandler(AccessDeniedException.class)
    public ErrorResponse handleAccessDeniedException(AccessDeniedException exception) {
        return new ErrorResponse(FORBIDDEN, exception.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    @ExceptionHandler({Exception.class})
    protected ErrorResponse handleMethodArgumentNotValidException(Exception exception){

        var exceptionClassName = exception.getClass().getName();

        var errorName = exceptionClassName.equals("com.inventivum.reperioapi.service.exception.KeycloakServerException")
                ? "Keycloak Server Error"
                : INTERNAL_SERVER_ERROR + exceptionClassName;

        log.error(errorName, exception);
        return new ErrorResponse(errorName, exception.getMessage());
    }
}
