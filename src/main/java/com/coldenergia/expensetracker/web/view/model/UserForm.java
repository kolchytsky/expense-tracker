package com.coldenergia.expensetracker.web.view.model;

import static com.coldenergia.expensetracker.defaultdata.DefaultDataConstants.ADMIN_AUTHORITY_NAME;
import static com.coldenergia.expensetracker.defaultdata.DefaultDataConstants.SPENDER_AUTHORITY_NAME;

/**
 * User: coldenergia
 * Date: 5/18/14
 * Time: 6:26 PM
 */
public class UserForm {

    private String name;

    private String password;

    private AuthorityName authority;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public AuthorityName getAuthority() {
        return authority;
    }

    public void setAuthority(AuthorityName authority) {
        this.authority = authority;
    }

    public static enum AuthorityName {

        ADMIN(ADMIN_AUTHORITY_NAME, "authority.admin"),

        SPENDER(SPENDER_AUTHORITY_NAME, "authority.spender");

        private final String value;

        /**
         * Message code in localization files.
         * */
        private final String messageCode;

        private AuthorityName(String value, String messageCode) {
            this.value = value;
            this.messageCode = messageCode;
        }

        public String getValue() {
            return value;
        }

        public String getMessageCode() {
            return messageCode;
        }

    }

}
