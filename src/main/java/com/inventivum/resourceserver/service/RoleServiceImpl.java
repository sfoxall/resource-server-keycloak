package com.inventivum.resourceserver.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class RoleServiceImpl implements RoleService {

    /*
     * Dummy service which returns a list of roles. Where the user's email in the JWT matches the email set in
     * application.properties an 'Admin' role is additionally returned, which is used to authenticate access to certain
     * controller methods.
     */

    @Value("${keycloak.email}")
    private String keycloakEmail;

    @Override
    public List<String> getRoles(String email) {
        if(Objects.equals(email, keycloakEmail)){
            return List.of("Admin", "Buyer" , "Supplier");
        }
        else return List.of("Buyer", "Supplier");
    }

    @Override
    public boolean hasPermission(String permissionName, String pageName, String supplierId){
        return true;
    }
}
