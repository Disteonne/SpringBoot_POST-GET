package com.netcracker.dao;

import com.netcracker.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDAO {

    private final List<User> users = new ArrayList<>();
    private int seqId = 0;

    public User add(User user) {
        user.setId(++seqId);
        users.add(user);
        return user;
    }

    public List<User> getUsers() {
        return users;
    }

    public List<User> getUsers(String surname, String name) {
        List<User> newListUsers = new ArrayList<>();
        for (User user : users) {
            if (user.getSurname().equals(surname) && user.getName().equals(name)) {
                newListUsers.add(user);
            }
        }
        return newListUsers;
    }
}
