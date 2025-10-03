package com.morrisco.net.eCommerceSystem.exceptions;

public class CartNotFoundException extends RuntimeException{
    /**
     * Constructs a new runtime exception with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     */
    public CartNotFoundException() {
        super("cart Not Found Exception");
    }
}
