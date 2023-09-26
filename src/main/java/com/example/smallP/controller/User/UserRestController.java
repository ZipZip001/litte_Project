package com.example.smallP.controller.User;

import com.example.smallP.controller.User.UserResponse;
import com.example.smallP.entity.User;
import com.example.smallP.service.User.UserService;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserRestController {
    private UserService userService;


    public UserRestController(UserService theUserService){
        userService = theUserService;
    }

    @GetMapping("/user")
    public ResponseEntity<ObjectNode> findAll(
            @RequestParam(required = false) Integer current,
            @RequestParam(required = false) Integer pageSize
    ){
        ObjectMapper objectMapper = new ObjectMapper();
        List<User> users = userService.findAll();

        int totalUsers = users.size();

        // Kiểm tra nếu current và pageSize bị null, thì trả về toàn bộ dữ liệu
        if (current == null || pageSize == null) {
            current = 1; // Giá trị mặc định nếu current bị null
            pageSize = totalUsers; // Trả về tất cả nếu pageSize bị null
        }


        //current and pageSize
        int totalPages = (int) Math.ceil((double) totalUsers / pageSize);

        int startIndex = (current - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, totalUsers);

        List<User> paginatedUsers = users.subList(startIndex, endIndex);

        UserResponse<List<User>> response = new UserResponse<>();
        UserResponse.Meta meta = new UserResponse.Meta(current, pageSize, totalPages, totalUsers);
        response.setMeta(meta);
        response.setResult(paginatedUsers);


        // Đặt "data" bên trong một đối tượng JSON bổ sung
        ObjectNode dataNode = JsonNodeFactory.instance.objectNode();
        dataNode.set("data", objectMapper.valueToTree(response));

        return new ResponseEntity<>(dataNode, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ObjectNode> getUser(@PathVariable int userId){
        User theUser = userService.findById(userId);

        ObjectMapper objectMapper = new ObjectMapper();
        // Đặt "data" bên trong một đối tượng JSON bổ sung
        ObjectNode dataNode = JsonNodeFactory.instance.objectNode();
        dataNode.set("data", objectMapper.valueToTree(theUser));

        if (dataNode == null){
            throw new RuntimeException("User id not found -" +dataNode);
        }
        else {
            return new ResponseEntity<>(dataNode, HttpStatus.OK);
        }
    }

    @PostMapping("/user")
    public User addUser(@RequestBody User theUser){

        theUser.setId(0);

        User dbUser = userService.save(theUser);

        return dbUser;
    }

    @PutMapping("/user")
    public User updateUser(@RequestBody User theUser){

        User dbUser = userService.save(theUser);
        return  dbUser;
    }

    @DeleteMapping("/user/{userId}")
    public String delete(@PathVariable int userId){

        User tempUser = userService.findById(userId);

        if (tempUser == null){
            throw new RuntimeException("User id not found -" +userId);
        }

        userService.deleteById(userId);

        return "Delete User with id- " +userId;
    }

}
