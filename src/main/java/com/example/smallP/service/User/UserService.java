package com.example.smallP.service.User;

import com.example.smallP.entity.User;

import java.util.List;

public interface UserService {
    List<User> findAll();

    User findById (int theId);

    User save (User theUser);

    void deleteById(int theId);
}
