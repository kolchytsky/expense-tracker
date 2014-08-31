package com.coldenergia.expensetracker.domain;

import javax.persistence.*;

/**
 * This is the core domain class. It characterizes the expense only using name and
 * category, and does not concern itself with prices, as these can change over time.
 * User: coldenergia
 * Date: 5/2/14
 * Time: 6:49 PM
 */
@Entity
@Table(name = "expenses")
public class Expense {

    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 60)
    private String name;

    @OneToOne
    @JoinColumn(name = "category_id")
    private Category category;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Expense{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }

}
