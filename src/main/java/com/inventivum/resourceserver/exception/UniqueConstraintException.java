package com.inventivum.resourceserver.exception;

import java.util.List;

public class UniqueConstraintException extends RuntimeException {

    private List<ErrorParameter> errors;

    public UniqueConstraintException() {
    }

    public UniqueConstraintException(String message, List<ErrorParameter> errors){
        super(message);
        this.errors = errors;
    }

    public UniqueConstraintException(String message, Throwable cause, List<ErrorParameter> errors){
        super(message, cause);
        this.errors = errors;
    }

    public UniqueConstraintException(Throwable cause, List<ErrorParameter> errors){
        super(cause);
        this.errors = errors;
    }

    public UniqueConstraintException(List<ErrorParameter> errors){
        this.errors = errors;
    }

    public List<ErrorParameter> getErrors() {
        return errors;
    }

    public UniqueConstraintException(String message, Throwable cause, boolean enableSuppression,
                                     boolean writeableStackTrace, List<ErrorParameter> errors){
        super(message, cause, enableSuppression, writeableStackTrace);
        this.errors = errors;
    }
}
