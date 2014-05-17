package com.coldenergia.expensetracker.domain;

import javax.persistence.*;

/**
 * To be used in conjunction with Spring Security.<br>
 * User: coldenergia
 * Date: 5/2/14
 * Time: 6:10 PM
 */
@Entity
@Table(name = "authorities")
public class Authority {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(length = 60, unique = true)
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
        final StringBuilder sb = new StringBuilder("Authority{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }

}
