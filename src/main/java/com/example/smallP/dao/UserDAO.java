package com.example.smallP.dao;

import com.example.smallP.entity.User;

import java.util.List;

public interface UserDAO {
    List<User> findAll();

    User findById (int theId);

    User save (User theUser);

    void deleteById(int theId);
}
