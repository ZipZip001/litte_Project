package com.example.smallP.controller;

import com.example.smallP.entity.User;
import com.example.smallP.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api")
public class UserRestController {
    private UserService userService;


    public UserRestController(UserService theUserService){
        userService = theUserService;
    }

    @GetMapping("/users")
    public List<User> findAll(){
        return userService.findAll();
    }

    @GetMapping("/hello")
    public String sayHello(){
        return "This is hello Pages";
    }
    @GetMapping("/users/{userId}")
    public User getUser(@PathVariable int userId){
        User theUser = userService.findById(userId);

        if (theUser == null){
            throw new RuntimeException("User id not found -" +theUser);
        }
        else {
            return theUser;
        }
    }

    @PostMapping("/users")
    public User addUser(@RequestBody User theUser){

        theUser.setId(0);

        User dbUser = userService.save(theUser);

        return dbUser;
    }

    @PutMapping("/users")
    public User updateUser(@RequestBody User theUser){

        User dbUser = userService.save(theUser);
        return  dbUser;
    }

    @DeleteMapping("/users/{userId}")
    public String delete(@PathVariable int userId){

        User tempUser = userService.findById(userId);

        if (tempUser == null){
            throw new RuntimeException("User id not found -" +userId);
        }

        userService.deleteById(userId);

        return "Delete User with id- " +userId;
    }

}
