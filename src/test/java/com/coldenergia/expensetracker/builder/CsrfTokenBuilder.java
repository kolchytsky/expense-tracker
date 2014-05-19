package com.coldenergia.expensetracker.builder;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.DefaultCsrfToken;

import static com.coldenergia.expensetracker.web.CsrfConstants.*;

/**
 * @author Petri Kainulainen
 * @author coldenergia
 * User: coldenergia
 * Date: 5/19/14
 * Time: 9:58 PM
 */
public class CsrfTokenBuilder {

    private String headerName = DEFAULT_CSRF_HEADER_NAME;

    private String requestParameterName = DEFAULT_CSRF_PARAMETER_NAME;

    private String tokenValue = CSRF_TOKEN_VALUE_FOR_TEST;

    public CsrfToken build() {
        return new DefaultCsrfToken(headerName, requestParameterName, tokenValue);
    }

    public CsrfTokenBuilder withHeaderName(String headerName) {
        this.headerName = headerName;
        return this;
    }

    public CsrfTokenBuilder withRequestParameterName(String requestParameterName) {
        this.requestParameterName = requestParameterName;
        return this;
    }

    public CsrfTokenBuilder withTokenValue(String tokenValue) {
        this.tokenValue = tokenValue;
        return this;
    }

}
