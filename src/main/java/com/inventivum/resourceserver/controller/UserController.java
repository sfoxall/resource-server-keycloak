package com.inventivum.resourceserver.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "User", description = "Endpoints for managing users")
@RestController
@RequestMapping("/users")
public class UserController {

    @Secured("ROLE_admin")
    @PreAuthorize("hasCustomPermission(\"foo\", \"bar\", #id)")
    @Operation(summary = "Delete user id checks if user has realm role 'ROLE_admin' and account role (from role service) 'Admin'", tags = { "User" })
    @ResponseStatus(HttpStatus.ACCEPTED)
    @DeleteMapping("/{id}")
    public String deleteUser(@Parameter(description = "The id of the user departments to delete")
                                 @PathVariable String id){
        return "Deleted user with id " + id;
    }

}
