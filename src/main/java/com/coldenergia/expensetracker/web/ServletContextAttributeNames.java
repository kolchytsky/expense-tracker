package com.coldenergia.expensetracker.web;

/**
 * User: coldenergia
 * Date: 5/10/14
 * Time: 11:24 AM
 */
/**
 * Inspired by <a href="http://en.wikipedia.org/wiki/Constant_interface">Constant interface antipattern</a>.
 * */
public final class ServletContextAttributeNames {

    public static final String CONTEXT_PATH = "contextPath";

    /**
     * To be used in JSPs with Spring Security taglib. Not that I really
     * like it that way - perhaps when I integrate a template engine I
     * will have separate templates for admin and spender and won't use the taglib.
     * */
    public static final String ADMIN_AUTHORITY_NAME = "adminAuthorityName";

    /**
     * To be used in JSPs with Spring Security taglib. Not that I really
     * like it that way - perhaps when I integrate a template engine I
     * will have separate templates for admin and spender and won't use the taglib.
     * */
    public static final String SPENDER_AUTHORITY_NAME = "spenderAuthorityName";

    private ServletContextAttributeNames() {
        // Restrict instantiation
    }

}
