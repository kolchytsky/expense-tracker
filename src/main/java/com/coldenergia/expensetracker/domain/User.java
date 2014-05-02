package com.coldenergia.expensetracker.domain;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * User: coldenergia
 * Date: 5/2/14
 * Time: 4:02 PM
 */
// TODO: Add uniqueness attributes test-first in all domain classes
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 40)
    private String name;

    @Temporal(TemporalType.DATE)
    @Column(name = "created_date")
    private Date created;

    // User entity is the owning side of this many-to-many relationship
    @ManyToMany
    @JoinTable(
            name = "user_authorities",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id")
    )
    private List<Authority> authorities;

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

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", created=").append(created);
        sb.append('}');
        return sb.toString();
    }

}
