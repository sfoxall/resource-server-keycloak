package com.inventivum.resourceserver.controller;

import com.inventivum.resourceserver.model.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @GetMapping("/status/check")
    public String status(){
        return "Working...";
    }

//    @Secured("ROLE_developer")
//    @PreAuthorize("hasRole('developer') or #id == #jwt.subject")
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable String id, @AuthenticationPrincipal Jwt jwt){
        return "Deleted user with id " + id + "and JWT subject " + jwt.getSubject();
    }

//    @PostAuthorize("returnObject.userId == #jwt.subject")
    @GetMapping("/{id}")
    public User getUserById(@PathVariable String id, @AuthenticationPrincipal Jwt jwt){
        return new User("Stewart", "Foxall", "f3c13d28-2c24-437f-ad76-");
    }


}
