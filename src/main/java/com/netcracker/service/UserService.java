package com.netcracker.service;

import com.netcracker.dao.UserDAO;
import com.netcracker.model.SearchUser;
import com.netcracker.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserDAO userDAO;

    public User addUser(User user) {
         return userDAO.add(user);
    }

    public List<User> find(SearchUser searchUser) {
        return userDAO.getUsers(searchUser.getSurname(), searchUser.getName());
    }
}
