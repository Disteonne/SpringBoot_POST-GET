package com.netcracker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netcracker.model.SearchUser;
import com.netcracker.model.User;
import com.netcracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    private final ObjectMapper mapper = new ObjectMapper();

    @GetMapping("/user")
    public String read(Model model) {
        model.addAttribute("newUser", new User());
        return "new";
    }

    @PostMapping("/user")
    public String write(@ModelAttribute User newUser, Model model) {
        newUser = userService.addUser(newUser);
        File file = new File("infoOfUsers");
        try (FileWriter fileWriter = new FileWriter(file, true)) {
            fileWriter.write(mapper.writeValueAsString(newUser) + "\n");
        } catch (IOException ex) {
            System.out.println("Error: controller_write");
        }
        model.addAttribute("addUser", newUser);
        return "result";
    }

    @GetMapping("user/search")
    public String searchUser(Model model) {
        model.addAttribute("searchUser", new SearchUser());
        return "search";
    }

    @PostMapping("user/search")
    public String searchUserGet(@ModelAttribute SearchUser searchUser, Model model) {
        List<User> users = userService.find(searchUser);
        if (users.isEmpty()) {
            return "userNotFound";
        }
        model.addAttribute("users", users);
        return "searchIsDone";
    }
    /*
    @GetMapping("/hello")
    ResponseEntity<String> hello() {
        return new ResponseEntity<>("<H1> Hello World! </H1>", HttpStatus.BAD_REQUEST);
    }

     */

}
