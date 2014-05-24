package com.coldenergia.expensetracker.service;

/**
 * Indicates that the domain name is already taken.
 * User: coldenergia
 * Date: 5/24/14
 * Time: 3:39 PM
 */
public class DomainNameIsTakenException extends ServiceException {

    public DomainNameIsTakenException() {}

    public DomainNameIsTakenException(String message) {
        super(message);
    }

    public DomainNameIsTakenException(String message, Throwable cause) {
        super(message, cause);
    }

    public DomainNameIsTakenException(Throwable cause) {
        super(cause);
    }

    public DomainNameIsTakenException(
            String message,
            Throwable cause,
            boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
