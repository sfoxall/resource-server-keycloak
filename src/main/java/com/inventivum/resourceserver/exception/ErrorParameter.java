package com.inventivum.resourceserver.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ErrorParameter {

    @Schema(description = "Error parameter name", example = "organisationId")
    @JsonProperty("parameterName")
    private String parameterName;

    @Schema(description = "Error message", example = "Organisation id cannot be found")
    @JsonProperty("errorMessage")
    private String errorMessage;

}
