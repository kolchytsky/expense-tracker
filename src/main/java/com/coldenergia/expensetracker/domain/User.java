package com.coldenergia.expensetracker.domain;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * User: coldenergia
 * Date: 5/2/14
 * Time: 4:02 PM
 */
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 40)
    private String name;

    /*
    * The password column length is set to 50, since the password is bcrypt-encoded,
    * and the bcrypt specification says:
    * "The key argument is a secret encryption key, which can be a user-chosen
    * password of up to 56 bytes (including a terminating zero byte
    * when the key is an ASCII string)."
    * So one could infer a maximum input password length of 55 characters
    * (not counting the terminating zero). And that is true only for ASCII characters,
    * Unicode characters can take up to 4 bytes. So the safest option is 50 here.
    * */
    @Column(length = 50)
    private String password;

    @Temporal(TemporalType.DATE)
    @Column(name = "created_date")
    private Date created;

    // User entity is the owning side of this many-to-many relationship
    @ManyToMany(cascade = { CascadeType.PERSIST })
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public List<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<Authority> authorities) {
        this.authorities = authorities;
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
