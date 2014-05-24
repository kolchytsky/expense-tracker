package com.coldenergia.expensetracker.service;

/**
 * Indicates that the user name has already been taken.
 * User: coldenergia
 * Date: 5/22/14
 * Time: 9:59 PM
 */
public class UserNameIsTakenException extends ServiceException {

    public UserNameIsTakenException() {}

    public UserNameIsTakenException(String message) {
        super(message);
    }

    public UserNameIsTakenException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserNameIsTakenException(Throwable cause) {
        super(cause);
    }

    public UserNameIsTakenException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
