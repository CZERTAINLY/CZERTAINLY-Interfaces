package com.otilm.api.exception;

public class SchedulerException extends Exception implements PlatformException {

    public SchedulerException(String message) {
        super(message);
    }

    public SchedulerException(String message, Throwable cause) {
        super(message, cause);
    }
}
