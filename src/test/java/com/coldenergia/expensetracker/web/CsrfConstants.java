package com.coldenergia.expensetracker.web;

import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

/**
 * Mirrors the constants defined in {@link HttpSessionCsrfTokenRepository} to
 * be used in tests.<br>
 * User: coldenergia
 * Date: 5/19/14
 * Time: 10:12 PM
 */
public final class CsrfConstants {

    public static final String DEFAULT_CSRF_PARAMETER_NAME = "_csrf";

    public static final String DEFAULT_CSRF_HEADER_NAME = "X-CSRF-TOKEN";

    public static final String DEFAULT_CSRF_TOKEN_ATTR_NAME = HttpSessionCsrfTokenRepository.class.getName().concat(".CSRF_TOKEN");

    /**
     * This is a value for testing.
     * */
    public static final String CSRF_TOKEN_VALUE_FOR_TEST = "6e8b5979-89e8-4082-842c-69498b40e538";

    private CsrfConstants() {
        // Restrict instantiation
    }

}
