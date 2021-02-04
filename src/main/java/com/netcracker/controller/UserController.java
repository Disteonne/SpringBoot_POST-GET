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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import java.io.*;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    private final ObjectMapper mapper = new ObjectMapper();

    @GetMapping("/user")
    public String read(Model model) {
            model.addAttribute("newUser", new User());
            return "newUser";
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
    public String fileIsEmpty(){
        return "fileIsEmpty";
    }
    @PostMapping("/user/upload")
    public RedirectView uploadFile(@RequestParam("file") MultipartFile multipartFile){
        if(!multipartFile.isEmpty()){
            try(BufferedOutputStream bufferedOutputStream=new BufferedOutputStream(
                    new FileOutputStream(new File("File_upload.txt")))) {
                byte[] file=multipartFile.getBytes();
                bufferedOutputStream.write(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return  new RedirectView("/user");
        }else {
            return new RedirectView("/fileIsEmpty");
        }

    }

}
