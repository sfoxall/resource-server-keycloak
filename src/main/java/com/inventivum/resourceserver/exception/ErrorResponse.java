package com.inventivum.resourceserver.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ErrorResponse
{
    @Schema(description = "Error name", example = "Resource Not Found")
    @JsonProperty("error")
    private String error;

    @Schema(description = "Error message", example = "SQL Error: 34637")
    @JsonProperty("message")
    private String message;

    private List<ErrorParameter> errors;

    public ErrorResponse(String error, String message) {
        this.error = error;
        this.message = message;
    }

    public ErrorResponse(String error, List<ErrorParameter> errors) {
        this.error = error;
        this.message = message;
        this.errors = errors;
    }
}

