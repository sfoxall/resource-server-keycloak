package com.inventivum.resourceserver.exception;

import java.util.List;

public class ResourceNotFoundException extends RuntimeException {

    private List<ErrorParameter> errors;

    public static final ResourceNotFoundException USER_NOT_FOUND_EXCEPTION = new ResourceNotFoundException("User cannot be found", List.of(new ErrorParameter("userId", "User id cannot be found")));
    public static final ResourceNotFoundException USER_KEYCLOAK_NOT_FOUND_EXCEPTION = new ResourceNotFoundException("Keycloak user cannot be found", List.of(new ErrorParameter("email", "Email cannot be found")));
    public static final ResourceNotFoundException USER_DEPARTMENT_NOT_FOUND_EXCEPTION = new ResourceNotFoundException("User department cannot be found",List.of(new ErrorParameter("userDepartmentId", "User department id cannot be found")));
    public static final ResourceNotFoundException USER_ORGANISATION_NOT_FOUND_EXCEPTION = new ResourceNotFoundException("User organisation cannot be found",List.of(new ErrorParameter("userOrganisationId", "User organisation id cannot be found")));
    public static final ResourceNotFoundException COUNTRY_NOT_FOUND_EXCEPTION = new ResourceNotFoundException("Country cannot be found",List.of(new ErrorParameter("countryId", "Country id cannot be found")));
    public static final ResourceNotFoundException DEPARTMENT_NOT_FOUND_EXCEPTION = new ResourceNotFoundException("Department cannot be found",List.of(new ErrorParameter("departmentId", "Department id cannot be found")));
    public static final ResourceNotFoundException ORGANISATION_NOT_FOUND_EXCEPTION = new ResourceNotFoundException("Organisation cannot be found",List.of(new ErrorParameter("organisationId", "Organisation id cannot be found")));
    public static final ResourceNotFoundException ORGANISATION_MEMBERSHIP_NOT_FOUND_EXCEPTION = new ResourceNotFoundException("Organisation membership cannot be found",List.of(new ErrorParameter("organisationMembershipId", "Organisation membership id cannot be found")));
    public static final ResourceNotFoundException ORGANISATION_CHILD_NOT_FOUND_EXCEPTION = new ResourceNotFoundException("Organisation child cannot be found",List.of(new ErrorParameter("organisationChildId", "Organisation child id cannot be found")));
    public static final ResourceNotFoundException ORGANISATION_TYPE_NOT_FOUND_EXCEPTION = new ResourceNotFoundException("Organisation type cannot be found",List.of(new ErrorParameter("typeId", "Organisation type id cannot be found")));
    public static final ResourceNotFoundException PARENT_ORGANISATION_TYPE_NOT_FOUND_EXCEPTION = new ResourceNotFoundException("Parent organisation type cannot be found",List.of(new ErrorParameter("parentId", "Parent organisation type id cannot be found")));
    public static final ResourceNotFoundException ORGANISATION_STATUS_NOT_FOUND_EXCEPTION = new ResourceNotFoundException("Organisation status cannot be found",List.of(new ErrorParameter("statusId", "Organisation status id cannot be found")));
    public static final ResourceNotFoundException PARENT_DEPARTMENT_NOT_FOUND_EXCEPTION = new ResourceNotFoundException("Parent department cannot be found",List.of(new ErrorParameter("parentId", "Parent department id cannot be found")));

    public ResourceNotFoundException() {
    }

    public ResourceNotFoundException(String message, List<ErrorParameter> errors){
        super(message);
        this.errors = errors;
    }

    public ResourceNotFoundException(String message, Throwable cause, List<ErrorParameter> errors){
        super(message, cause);
        this.errors = errors;
    }

    public ResourceNotFoundException(Throwable cause, List<ErrorParameter> errors){
        super(cause);
        this.errors = errors;
    }

    public ResourceNotFoundException(List<ErrorParameter> errors){
        this.errors = errors;
    }

    public List<ErrorParameter> getErrors() {
        return errors;
    }

    public ResourceNotFoundException(String message, Throwable cause, boolean enableSuppression,
                                     boolean writeableStackTrace, List<ErrorParameter> errors){
        super(message, cause, enableSuppression, writeableStackTrace);
        this.errors = errors;
    }
}
