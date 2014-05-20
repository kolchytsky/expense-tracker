package com.coldenergia.expensetracker.web.view.model;

/**
 * User: coldenergia
 * Date: 5/18/14
 * Time: 6:26 PM
 */
public class UserForm {

    private String name;

    private String password;

    // TODO: Refactor as enum later
    private String authority;

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

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

}
