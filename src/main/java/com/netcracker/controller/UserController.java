package com.netcracker.controller;

import com.netcracker.model.UserAgent;
import com.netcracker.model.SearchUser;
import com.netcracker.model.User;
import com.netcracker.service.EmailServer;
import com.netcracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private EmailServer emailServer;

    private User user = new User();
    private UserAgent userAgent=new UserAgent();
    private List<User> users = new ArrayList<>();


    @GetMapping("/user")
    public String read(Model model) {
        model.addAttribute("newUser", user);
        return "newUser";
    }

    @PostMapping("/user")
    public String write(@ModelAttribute User newUser, Model model) {
        newUser = userService.addUser(newUser);
        model.addAttribute("addUser", newUser);
        userService.clear(user);
        return "resultAddNewUser";
    }


    @GetMapping("/user/search")
    public String searchUser(Model model) {
        model.addAttribute("searchUser", new SearchUser());
        return "search";
    }

    @GetMapping("/user/searchIsDone")
    public String searchUserIsDone(Model model) {
        model.addAttribute("users", users);
        model.addAttribute("userAgent",userAgent);
        return "searchIsDone";
    }
    @GetMapping("/user/notFound")
    public String searchUserNotFound() {
        return "userNotFound";
    }


    @PostMapping("/user/search")
    public RedirectView searchUserGet(@ModelAttribute SearchUser searchUser, HttpServletRequest servletRequest) {
        users.clear();
        users = userService.find(searchUser);
        userAgent.setBrowserInfo(servletRequest.getHeader("User-Agent"));
        userAgent.setDate(new Date().toString());
        if (users.isEmpty()) {
            return new RedirectView("/user/notFound");
        }
        return new RedirectView("/user/searchIsDone");
    }

    @GetMapping("/fileIsEmpty")
    public String fileIsEmpty() {
        return "fileIsEmpty";
    }

    @PostMapping("/user/upload")
    public RedirectView uploadFile(@RequestParam("file") MultipartFile multipartFile) {
        boolean saved = userService.uploadFile(multipartFile, user);
        return saved ? new RedirectView("/user"):new RedirectView("/fileIsEmpty");
    }

    @PostMapping("/user/search/sendMail")
    public RedirectView sendMail(@RequestParam("to") String to,
            @RequestParam("subject") String subject,@RequestParam("text") String text,Model model){
            emailServer.sendMessage(to,subject,text);
            return new RedirectView("/user/search");
    }
}
