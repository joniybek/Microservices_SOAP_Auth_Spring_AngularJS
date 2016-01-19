package com.tele2.models.dto.security;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * Created by Dell on 16.01.2016.
 */

@Entity
@Table(name = "resources")
public class Resource {
    public Resource() {
    }

    @Id
    @Column(name = "resource_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "resource")
    Set<Role> roles;

    @NotNull
    @Column(name = "name",unique=true)
    String name;

    @NotNull
    String url;

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Resource(String name, String url) {

        this.name = name;
        this.url = url;
    }

}
