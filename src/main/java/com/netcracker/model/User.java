package com.netcracker.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {
    private int id;
    private String surname;
    private String name;
    private String patronymic;
    private int age;
    private double salary;
    private String email;
    private String workAddress;

    @Override
    public String toString() {
        return id +"="+surname + "="+ name + "="+ patronymic +
                "=" +age +"="+ salary +"="+ email + "="+workAddress;
    }
}
