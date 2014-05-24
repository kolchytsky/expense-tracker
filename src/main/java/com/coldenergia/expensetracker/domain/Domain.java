package com.coldenergia.expensetracker.domain;

import javax.persistence.*;
import java.util.List;

/**
 * User: coldenergia
 * Date: 5/2/14
 * Time: 4:33 PM
 */
@Entity
@Table(name = "domains")
public class Domain {

    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 40, unique = true)
    private String name;

    /*
    * From Hibernate 4 documentation:
    * Changes made only to the inverse end of the association are not persisted.
    * This means that Hibernate has two representations in memory for every bidirectional association:
    * one link from A to B and another link from B to A. This is easier to understand if you think
    * about the Java object model and how a many-to-many relationship in Java is created:
    *
    * category.getItems().add(item);           // The category now "knows" about the relationship
    * item.getCategories().add(category);      // The item now "knows" about the relationship
    *
    * session.persist(item);                   // The relationship won't be saved!
    * session.persist(category);               // The relationship will be saved
    *
    * The non-inverse side is used to save the in-memory representation to the database.
    * */
    @ManyToMany
    @JoinTable(
            name = "user_domains",
            joinColumns = @JoinColumn(name = "domain_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> users;

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

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Domain{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }

}
