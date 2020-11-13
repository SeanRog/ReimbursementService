package com.vertigo.exceptions;

public class UsernameAlreadyExistsException extends RuntimeException {

    public UsernameAlreadyExistsException() {

    }

    public UsernameAlreadyExistsException(String msg) {
        super(msg);
    }

}
