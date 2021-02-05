package com.netcracker.service;

import com.netcracker.dao.UserDAO;
import com.netcracker.model.SearchUser;
import com.netcracker.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import java.io.*;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserDAO userDAO;

    public User addUser(User user) {
         userDAO.writeFile(user);
         return userDAO.add(user);
    }

    public List<User> find(SearchUser searchUser) {
        return userDAO.getUsers(searchUser.getSurname(), searchUser.getName());
    }

    public void clear(User user){
        user.setSurname(null);
        user.setName(null);
        user.setPatronymic(null);
        user.setAge(0);
        user.setSalary(0.0);
        user.setEmail(null);
        user.setWorkAddress(null);
    }

    public boolean uploadFile(MultipartFile multipartFile,User user){

        if (!multipartFile.isEmpty()) {
            File file = new File(multipartFile.getName() + "_upload");
            try (BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(
                    new FileOutputStream(file))) {
                byte[] getFile = multipartFile.getBytes();
                bufferedOutputStream.write(getFile);
            } catch (IOException e) {
                e.printStackTrace();
            }

            try (BufferedReader reader = new BufferedReader(new FileReader(file));
            ) {
                String info = reader.readLine();
                returnNewUserWithInfo(info,user);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        } else {
            return false;
        }
    }

    private void returnNewUserWithInfo(String info,User user) {
        String[] information = info.split("=");//считаем,что инфа в файле задана целой строкой и данные разделены =
        user.setSurname(information[0]);
        user.setName(information[1]);
        user.setPatronymic(information[2]);
        user.setAge(Integer.parseInt(information[3]));
        user.setSalary(Double.parseDouble(information[4]));
        user.setEmail(information[5]);
        user.setWorkAddress(information[6]);
    }
}
