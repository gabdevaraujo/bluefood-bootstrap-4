package com.bs.bluefood.bs_bluefood.application.services;

public class ApplicationServiceException extends RuntimeException{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public ApplicationServiceException(String message) {
        super(message);
    }

    public ApplicationServiceException(Throwable cause) {
        super(cause);
    }

    public ApplicationServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    

}
