package com.paypal.pos.exception;

/**
 * Created by pderoxas on 2/24/14.
 */
public class DalException extends Exception {
    public DalException(String message) {
        super(message);
    }

    public DalException(String message, Throwable cause) {
        super(message, cause);
    }
}