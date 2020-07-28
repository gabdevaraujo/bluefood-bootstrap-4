package com.bs.bluefood.bs_bluefood.application.services;

public class PagamentoException extends Exception{

    public PagamentoException() {
    }

    public PagamentoException(String message) {
        super(message);
    }

    public PagamentoException(Throwable cause) {
        super(cause);
    }

    public PagamentoException(String message, Throwable cause) {
        super(message, cause);
    }
    
    
}