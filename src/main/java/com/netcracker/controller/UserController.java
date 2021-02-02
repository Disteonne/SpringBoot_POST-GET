package com.netcracker.controller;

import com.netcracker.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


@Controller
public class UserController {
    File file;
    ObjectMapper mapper;


    @GetMapping("/user")
    public String read(Model model){
        model.addAttribute("newUser",new User());
        return "new";
    }


    @PostMapping("/user")
    public String write(@ModelAttribute User newUs,Model model){
        file=new File("infoOfUsers");
        mapper=new ObjectMapper();
          try (FileWriter fileWriter=new FileWriter(file,true)) {
             fileWriter.write(mapper.writeValueAsString(newUs));
          }catch (IOException ex){
                System.out.println("Error: controller_write");
          }
          model.addAttribute("addUser",newUs);
        return "result";
    }

}
