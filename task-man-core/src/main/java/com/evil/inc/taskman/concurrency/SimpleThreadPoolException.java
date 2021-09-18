package com.evil.inc.taskman.concurrency;

public class SimpleThreadPoolException extends RuntimeException {
    public SimpleThreadPoolException(Throwable cause) {
        super(cause);
    }

    public SimpleThreadPoolException(final String s) {
        super(s);
    }
}