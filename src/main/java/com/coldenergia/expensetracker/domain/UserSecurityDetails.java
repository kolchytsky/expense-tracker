package com.coldenergia.expensetracker.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * This class was created for a custom {@link org.springframework.security.core.userdetails.UserDetailsService}
 * implementation - {@link com.coldenergia.expensetracker.service.UserDetailsServiceImpl}.<br>
 * User: coldenergia
 * Date: 5/6/14
 * Time: 9:15 PM
 */
public class UserSecurityDetails implements UserDetails {

    private String username;

    private String password;

    private List<SimpleAuthority> authorities = new ArrayList<SimpleAuthority>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAuthorities(List<String> authorityNames) {
        for (String authorityName : authorityNames) {
            this.authorities.add(new SimpleAuthority(authorityName));
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UserSecurityDetails{");
        sb.append("username='").append(username).append('\'');
        sb.append(", authorities=").append(authorities);
        sb.append('}');
        return sb.toString();
    }

    public static class SimpleAuthority implements GrantedAuthority {

        private String authorityName;

        public SimpleAuthority(String authorityName) {
            this.authorityName = authorityName;
        }

        @Override
        public String getAuthority() {
            return authorityName;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("SimpleAuthority{");
            sb.append("authorityName='").append(authorityName).append('\'');
            sb.append('}');
            return sb.toString();
        }

    }

}
