package com.coldenergia.expensetracker.builder;

import com.coldenergia.expensetracker.domain.Unit;

/**
 * User: coldenergia
 * Date: 5/3/14
 * Time: 9:33 PM
 */
public class UnitBuilder {

    private Unit unit;

    public UnitBuilder() {
        unit = new Unit();
        unit.setName("items");
    }

    public Unit build() {
        return unit;
    }

    public UnitBuilder withName(String name) {
        unit.setName(name);
        return this;
    }

}
