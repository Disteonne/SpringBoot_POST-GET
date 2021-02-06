package com.netcracker.dao;

import com.netcracker.model.User;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDAO {

    private final List<User> allUsers = getAllUsers();
    private  int seqId=getLastId();

    private File dbFile(){
        return new File("infoOfUsers.txt");
    }

    public User add(User user) {
        user.setId(++seqId);
        //allUsers.add(user);
        return user;
    }
    private List<User> getAllUsers(){
        List<User> allUser=new ArrayList<>();
        try(BufferedReader bufferedReader=new BufferedReader(new FileReader(dbFile()))) {
            String userInfo;
            while ((userInfo=bufferedReader.readLine())!=null && userInfo!=""){
                User readUser=new User();
                String[] userField=userInfo.split("=");
                readUser.setId(Integer.parseInt(userField[0]));
                readUser.setSurname(userField[1]);
                readUser.setName(userField[2]);
                readUser.setPatronymic(userField[3]);
                readUser.setAge(Integer.parseInt(userField[4]));
                readUser.setSalary(Double.parseDouble(userField[5]));
                readUser.setEmail(userField[6]);
                readUser.setWorkAddress(userField[7]);
                allUser.add(readUser);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return allUser;
    }

    public void writeFile(User user){
        try (FileWriter fileWriter = new FileWriter(dbFile(), true)) {
            fileWriter.write(user.toString()+ "\n");
        } catch (IOException ex) {
            System.out.println("Error: controller_write");
        }
    }

    public List<User> getUsers(String surname, String name) {
        List<User> newListUsers = new ArrayList<>();
        for (User user: allUsers
             ) {
            if (user.getName().equals(name) && user.getSurname().equals(surname)) {
                newListUsers.add(user);
            }
        }
        return newListUsers;
    }

    private int getLastId(){
        return allUsers.size()==0 ? 0 : allUsers.get(allUsers.size()-1).getId();
    }
}
