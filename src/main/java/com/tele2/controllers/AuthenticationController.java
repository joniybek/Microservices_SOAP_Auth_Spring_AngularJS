package com.tele2.controllers;

import com.tele2.models.dao.security.ResourcesDAO;
import com.tele2.models.dao.security.UsersDAO;
import com.tele2.models.dto.security.Resource;
import com.tele2.models.dto.security.Role;
import com.tele2.models.dto.security.User;
import com.tele2.services.security.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.*;

@Controller
public class AuthenticationController {
    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    ResourcesDAO resourcesDAO;

    @Autowired
    UsersDAO usersDAO;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model) {
        model.addAttribute("title", "Login");
        return "login";
    }

    @RequestMapping(value = "/mvc/auth/all", method = RequestMethod.GET)
    public String getAll(Model model) {
        List<Resource> resources = new ArrayList<>();
        resourcesDAO.findAll().forEach(resources::add);
        Collections.sort(resources, (resource1, resource2) -> resource1.getName().compareTo(resource2.getName()));
        LinkedHashMap<String, HashMap<String, String>> data = new LinkedHashMap<>();
        for (User user : usersDAO.findAll()) {
            LinkedHashMap<String, String> hashMap = new LinkedHashMap<>();
            for (Resource resource : resources) {
                Role role = authenticationService.getMostPowerfulRole(user, resource);
                if (role == null) {
                    hashMap.put(resource.getName(), "");
                } else if (role.isAdmin()) {
                    hashMap.put(resource.getName(), "admin");
                } else {
                    hashMap.put(resource.getName(), "reader");
                }
            }
            data.put(user.getUsername(), hashMap);
        }

        model.addAttribute("resources", resources);
        model.addAttribute("data", data);
        model.addAttribute("title","User administration panel");
        return "allusers";
    }

}
