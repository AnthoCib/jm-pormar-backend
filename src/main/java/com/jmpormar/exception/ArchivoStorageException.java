package com.jmpormar.exception;

public class ArchivoStorageException
        extends RuntimeException {

    public ArchivoStorageException(
            String message
    ) {
        super(message);
    }

    public ArchivoStorageException(
            String message,
            Throwable cause
    ) {
        super(message, cause);
    }
}