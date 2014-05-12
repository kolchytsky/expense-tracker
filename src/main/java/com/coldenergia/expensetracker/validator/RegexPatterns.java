package com.coldenergia.expensetracker.validator;

/**
 * User: coldenergia
 * Date: 5/12/14
 * Time: 10:13 PM
 */
public final class RegexPatterns {

    public static final String ALPHANUMERIC_AND_UNDERSCORES_ONLY = "^\\w*$";

    public static final String ASCII_CHARS_ONLY = "^\\p{ASCII}*$";

    private RegexPatterns() {
        // Restrict instantiation
    }

}
