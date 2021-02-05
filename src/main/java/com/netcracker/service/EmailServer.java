package com.netcracker.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailServer {

    @Autowired
    private JavaMailSender emailSender;

    public void sendMessage(String to,String subject,String text){
        SimpleMailMessage mailMessage=new SimpleMailMessage();
        mailMessage.setFrom("springtestforhomework@gmail.com");
        mailMessage.setTo(to);
        mailMessage.setSubject(subject);
        mailMessage.setText(text);
        emailSender.send(mailMessage);

    }
}
