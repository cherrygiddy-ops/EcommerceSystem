package com.morrisco.net.eCommerceSystem.exceptions;

public class OrderNotFoundException extends RuntimeException {
    /**
     * Constructs a new throwable with {@code null} as its detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * <p>The {@link #fillInStackTrace()} method is called to initialize
     * the stack trace data in the newly created throwable.
     */
    public OrderNotFoundException() {
        super("Order Not found");
    }
}
