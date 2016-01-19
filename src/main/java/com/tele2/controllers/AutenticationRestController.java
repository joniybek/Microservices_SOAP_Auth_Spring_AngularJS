package com.tele2.controllers;

import com.tele2.models.dao.security.UsersDAO;
import com.tele2.models.dto.security.Role;
import com.tele2.models.dto.security.User;
import com.tele2.services.security.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dell on 17.01.2016.
 */
@RestController
@RequestMapping("/api/auth")
public class AutenticationRestController {

    @Autowired
    UsersDAO users;

    @Autowired
    AuthenticationService authenticationService;

    @PreAuthorize("hasRole('DATA_POOL_ADMIN')")
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public List<User> getUsers() {
        List<User> result = new ArrayList<>();
        users.findAll().forEach(result::add);
        return result;
    }

    @RequestMapping(value = "/users/promote", method = RequestMethod.GET)
    public ResponseEntity<String> promoteUsers(@RequestParam(name = "role") String role,
                                               @RequestParam(name = "resource") String resource,
                                               @RequestParam(name = "user") String user) {
        if ("".equals(role)) {
            for (Role role1 : authenticationService.getResource(resource).getRoles()) {
                authenticationService.ungrantAccess(users.findByUsername(user), role1);
            }
        } else if ("reader".equals(role)) {
            authenticationService.grantAccess(users.findByUsername(user), authenticationService.getReaderFromResource(resource));
        } else {
            authenticationService.grantAccess(users.findByUsername(user), authenticationService.getAdminFromResource(resource));
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
