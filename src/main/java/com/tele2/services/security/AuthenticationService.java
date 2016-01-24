package com.tele2.services.security;

import com.tele2.models.dao.security.PermissionsDAO;
import com.tele2.models.dao.security.ResourcesDAO;
import com.tele2.models.dao.security.UsersDAO;
import com.tele2.models.dto.security.Resource;
import com.tele2.models.dto.security.Role;
import com.tele2.models.dto.security.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Dell on 16.01.2016.
 */

@Service
@Transactional
public class AuthenticationService {

    public void init() {
        Resource resource1 = new Resource("Defects Tool", "localhost/defects");
        Resource resource2 = new Resource("Data Pool", "localhost/datapool");
        Resource resource3 = new Resource("Sanity Tool", "localhost/sanity");

        resourcesDAO.save(resource1);
        resourcesDAO.save(resource2);
        resourcesDAO.save(resource3);

        Role role1 = new Role(true, resource1);
        Role role2 = new Role(false, resource1);
        Role role3 = new Role(true, resource2);
        Role role4 = new Role(false, resource2);
        Role role5 = new Role(true, resource3);
        Role role6 = new Role(false, resource3);

        Set<Role> permissions1 = new HashSet<>();
        permissions1.add(role1);
        permissions1.add(role2);
        permissions1.add(role3);
        permissions1.add(role4);
        permissions1.add(role5);
        permissions1.add(role6);
        Set<Role> permissions2 = new HashSet<>();
        permissions2.add(role1);
        permissions2.add(role3);
        permissionsDAO.save(permissions1);
        permissionsDAO.save(permissions2);


        User user1 = new User("test1", "testuser", "email@box.me", permissions1);
        User user2 = new User("test2", "testuser2", "email@box.me", permissions2);

        usersDAO.save(user1);
        usersDAO.save(user2);


    }

    @Autowired
    PermissionsDAO permissionsDAO;

    @Autowired
    ResourcesDAO resourcesDAO;

    @Autowired
    UsersDAO usersDAO;

    public void grantAccess(User user, Role role) {
        if (role.isAdmin()) {
            user.getRoles().add(getReaderFromResource(role.getResource()));
        }
        user.getRoles().add(role);
        usersDAO.save(user);
    }

    public void ungrantAccess(User user, Role role) {
        user.getRoles().remove(role);
        usersDAO.save(user);
    }

    //User
    public User createUser(String username, String password, String email) {
        User user = new User(username, password, email, new HashSet<>());
        usersDAO.save(user);
        return user;
    }

    public void deleteUser(User user) {
        usersDAO.delete(user);
    }

    public void deleteUser(String username) {
        usersDAO.delete(usersDAO.findByUsername(username));
    }

    public boolean isUsernameExists(String username) {
        return usersDAO.findByUsername(username) == null ? false : true;
    }

    public User authenticateUser(String usernameOrEmail, String password) {
        User user = null;
        if (usernameOrEmail.matches("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$")) {
            user = usersDAO.findByEmail(usernameOrEmail);
            if (user.getPassword().equals(password)) {
                return user;
            } else {
                return null;
            }
        } else {
            user = usersDAO.findByUsername(usernameOrEmail);
            if (user != null && user.getPassword().equals(password)) {
                return user;
            }
            return null;
        }

    }

    //Resource
    public Resource createResource(String name, String url) {
        Resource resource = new Resource(name, url);
        resourcesDAO.save(resource);
        Role user = new Role(false, resource);
        Role admin = new Role(true, resource);

        permissionsDAO.save(user);
        permissionsDAO.save(admin);

        return resource;
    }

    private void deleteResource(Resource resource) {
        permissionsDAO.delete(resource.getRoles());
        resourcesDAO.delete(resource);
    }

    public void deleteResource(String name) {
        deleteResource(resourcesDAO.findByName(name));
    }

    public Resource getResource(String name) {
        return resourcesDAO.findByName(name);
    }

    public boolean isResourceExists(String name) {
        return resourcesDAO.findByName(name) == null ? false : true;
    }

    //Role
    private Role getAdminFromResource(Resource resource) {
        if (resource == null || resource.getRoles() == null) return null;
        for (Role role : resource.getRoles()) {
            if (role.isAdmin()) {
                return role;
            }
        }
        return null;
    }

    public Role getAdminFromResource(String name) {
        return getAdminFromResource(resourcesDAO.findByName(name));
    }

    private Role getReaderFromResource(Resource resource) {
        if (resource == null || resource.getRoles() == null) return null;
        for (Role role : resource.getRoles()) {
            if (!role.isAdmin()) {
                return role;
            }
        }
        return null;
    }

    public Role getReaderFromResource(String name) {
        return getReaderFromResource(resourcesDAO.findByName(name));
    }

    public Role getMostPowerfulRole(User user, Resource resource) {
        Role returnRole = null;
        for (Role role : user.getRoles()) {
            if (resource.getName().equals(role.getResource().getName())) {
                returnRole = role;
                if (role.isAdmin()) {
                    break;
                }
            }
        }
        return returnRole;
    }

}
