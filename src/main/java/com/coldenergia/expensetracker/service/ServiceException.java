package com.coldenergia.expensetracker.service;

/**
 * User: coldenergia
 * Date: 5/11/14
 * Time: 9:30 AM
 */
/**
 * Now I finally can wrap my head around using checked
 * and unchecked exceptions, thanks to:
 * <a href="http://stackoverflow.com/questions/6115896/java-checked-vs-unchecked-exception-explanation">link one</a>,
 * <a href="http://forum.spring.io/forum/other-spring-related/architecture/59382-proper-exception-handling">link two</a>,
 * <a href="http://forum.spring.io/forum/other-spring-related/architecture/60223-service-layer-exceptions">link three</a>,
 * <a href="http://stackoverflow.com/questions/27578/when-to-choose-checked-and-unchecked-exceptions">link four</a>.
 * */
public class ServiceException extends RuntimeException {

    public ServiceException() {
        super();
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }

}
