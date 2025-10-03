package com.morrisco.net.eCommerceSystem.exceptions;

public class ProductNotFoundException extends RuntimeException{
    /**
     * Constructs a new runtime exception with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     */
    public ProductNotFoundException() {
        super("Can not Find the Specified Product");
    }
}
