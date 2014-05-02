package com.coldenergia.expensetracker.domain;

import javax.persistence.*;

/**
 * User: coldenergia
 * Date: 5/2/14
 * Time: 7:23 PM
 */
/**
 * {@link Unit} is a unit of measurement.
 * */
@Entity
@Table(name = "units")
public class Unit {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(length = 10)
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Unit{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }

}
