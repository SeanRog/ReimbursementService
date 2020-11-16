package com.vertigo.exceptions;

public class InvalidRequestException extends RuntimeException{

    public InvalidRequestException() {

    }

    public InvalidRequestException(String msg) {
        super(msg);
    }

}
