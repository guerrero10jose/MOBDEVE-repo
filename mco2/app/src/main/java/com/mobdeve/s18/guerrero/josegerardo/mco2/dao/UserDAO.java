package com.mobdeve.s18.guerrero.josegerardo.mco2.dao;

import com.mobdeve.s18.guerrero.josegerardo.mco2.models.User;

import java.util.ArrayList;

public interface UserDAO {
    long addUser(User user);
    ArrayList<User> getUsers();
    User getUser(String username);
    int updateUser(User user);
    int deleteUser(String username);
}
