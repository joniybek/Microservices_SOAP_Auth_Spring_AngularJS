package com.tele2.models.dto.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {

    @Id
    @Column(name = "permission_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    long permissionId;

    @NotNull
    boolean isAdmin;

    @JsonIgnore
    @ManyToMany(mappedBy = "roles")
    Set<User> users;


    @JsonIgnore
    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "resource_id", nullable = false)
    Resource resource;

    public Role(boolean isAdmin, Resource resource) {
        this.isAdmin = isAdmin;
        this.resource = resource;
    }

    public long getId() {

        return permissionId;
    }


    public Role() {
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public long getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(long permissionId) {
        this.permissionId = permissionId;
    }

    public void setId(long id) {
        this.permissionId = id;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    @Override
    public String getAuthority() {
        return resource.getName().toUpperCase().replaceAll("[ .-]", "_") + "_" + (isAdmin ? "ADMIN" : "USER");
    }
}