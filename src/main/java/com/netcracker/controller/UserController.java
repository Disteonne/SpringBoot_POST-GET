package com.netcracker.controller;

import com.netcracker.model.MailObject;
import com.netcracker.model.SearchUser;
import com.netcracker.model.User;
import com.netcracker.service.EmailServer;
import com.netcracker.service.UserService;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpProtocolParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private EmailServer emailServer;

    private User user = new User();


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

    @PostMapping("/user/search")
    public String searchUserGet(@ModelAttribute SearchUser searchUser, Model model) {
        List<User> users = userService.find(searchUser);
        if (users.isEmpty()) {
            return "userNotFound";
        }
        model.addAttribute("users", users);
        return "searchIsDone";
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

    @GetMapping("/agent")
    public String userAgent(Model model){
        model.addAttribute("agent", new MailObject());
        return "agent";
    }

    @PostMapping("/agent")
    public String resultAgent(HttpServletRequest request,@ModelAttribute MailObject mailObject, Model model){
        mailObject.setTo(request.getHeader("User-Agent")+"     "+new Date().toString());
        model.addAttribute("kek",mailObject);
        return "agentResult";
        }
}
