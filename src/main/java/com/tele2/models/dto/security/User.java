package com.tele2.models.dto.security;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Dell on 16.01.2016.
 */
@Entity
@Table(name = "USERS")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long userId;

    @NotNull
    @NotEmpty
    @Size(min = 4, max = 50)
    @Column(name = "username", length = 50)
    String username;

    @NotNull
    @NotEmpty
    @Size(min = 8 ,max = 50)
    @Column(name = "password", length = 50)
    private String password;

    @NotNull
    @NotEmpty
    @Size(max = 50)
    @Column(name = "email", length = 50)
    private String email;

    @Size(max = 50)
    @Column(name = "skype_name", length = 50)
    private String skypeName;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "app_user_permission",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "permission_id")})
    private Set<Role> roles = new HashSet<Role>();


    public User() {
    }

    public User(String username, String password, String email, Set<Role> roles) {

        this.username = username;
        this.password = password;
        this.email = email;
        this.roles = roles;
    }

    public String getUsername() {

        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSkypeName() {
        return skypeName;
    }

    public void setSkypeName(String skypeName) {
        this.skypeName = skypeName;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}