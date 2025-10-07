package com.morrisco.net.eCommerceSystem.carts;

public class CartEmptyException extends RuntimeException {
    /**
     * Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     */
    public CartEmptyException() {
        super("cart is Empty ");
    }
}
