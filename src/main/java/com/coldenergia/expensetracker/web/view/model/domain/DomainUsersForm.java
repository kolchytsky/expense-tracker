package com.coldenergia.expensetracker.web.view.model.domain;

import java.util.Set;

/**
 * User: coldenergia
 * Date: 5/24/14
 * Time: 6:39 PM
 */
public class DomainUsersForm {

    private Set<Long> userIds;

    public Set<Long> getUserIds() {
        return userIds;
    }

    public void setUserIds(Set<Long> userIds) {
        this.userIds = userIds;
    }

}
