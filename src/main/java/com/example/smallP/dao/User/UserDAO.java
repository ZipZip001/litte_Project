package com.example.smallP.dao.User;

import com.example.smallP.entity.User;

import java.util.List;

public interface UserDAO {
    List<User> findAll();

    User findById (int theId);

    User save (User theUser);

    void deleteById(int theId);
}

