package com.inventivum.resourceserver.security;

import com.inventivum.resourceserver.service.RoleService;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;

public class CustomMethodSecurityExpressionRoot extends SecurityExpressionRoot implements MethodSecurityExpressionOperations {

    private Object filterObject;
    private Object returnObject;
    private final RoleService roleService;

    @Lazy
    public CustomMethodSecurityExpressionRoot(Authentication authentication, RoleService roleService) {
        super(authentication);
        this.roleService = roleService;
    }

    public boolean hasCustomPermission(String permissionName, String pageName, String supplierId) {
        return roleService.hasPermission(permissionName, pageName, supplierId);
    }

    @Override
    public Object getFilterObject() {
        return this.filterObject;
    }

    @Override
    public Object getReturnObject() {
        return this.returnObject;
    }

    @Override
    public Object getThis() {
        return this;
    }

    @Override
    public void setFilterObject(Object filterObject) {
        this.filterObject = filterObject;
    }

    @Override
    public void setReturnObject(Object returnObject) {
        this.returnObject = returnObject;
    }
}
