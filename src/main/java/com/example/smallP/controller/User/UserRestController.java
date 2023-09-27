package com.example.smallP.controller.User;

import com.example.smallP.controller.User.UserResponse;
import com.example.smallP.dao.User.UserDAO;
import com.example.smallP.entity.User;
import com.example.smallP.security.JwtService;
import com.example.smallP.security.UserPassword;
import com.example.smallP.service.User.DesginAPI.AuthResponse;
import com.example.smallP.service.User.DesginAPI.UserData;
import com.example.smallP.service.User.DesginAPI.UserMakeAPI;
import com.example.smallP.service.User.UserService;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class UserRestController {
    private UserService userService;
    @Autowired
    private JwtService jwtService;


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

    // login
    @PostMapping("/user/login")
    public ResponseEntity<UserMakeAPI> login(@RequestBody Map<String, String> loginData) {
        String email = loginData.get("email");
        String password = loginData.get("password");



        User user = userService.findByEmail(email);

        if (user != null && isPasswordCorrect(user, password)) {
            // Tạo access_token ngẫu nhiên
            String accessToken = jwtService.generateAccessToken(user);

            // Tạo đối tượng UserData và đặt thông tin người dùng
            UserData userData = new UserData();
            userData.setEmail(user.getEmail());
            userData.setPhone(user.getPhone());
            userData.setFullName(user.getFullName());
            userData.setRole(user.getRole());
            userData.setAvatar(user.getAvatar());
            userData.setId(user.getId());

            // Đặt thông tin access_token và user vào đối tượng ApiResponse
            AuthResponse authResponse = new AuthResponse();
            authResponse.setAccess_token(accessToken);
            authResponse.setUser(userData);


            UserMakeAPI userMakeAPI = new UserMakeAPI();
            userMakeAPI.setData(authResponse);

            return new ResponseEntity<>(userMakeAPI, HttpStatus.OK);
        } else {

            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }


    // Kiểm tra mật khẩu
    private boolean isPasswordCorrect(User user, String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPasswordInDatabase = user.getPassword(); // Lấy mật khẩu đã mã hóa trong cơ sở dữ liệu

        return passwordEncoder.matches(password, hashedPasswordInDatabase);
    }



    @Autowired
    private UserPassword userPassword;
    @PostMapping("/user/register")
    public ResponseEntity<User> registerUser(@RequestBody User newUser) {
        User savedUser = userPassword.registerUser(newUser);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @GetMapping("/user/profile")
    public ResponseEntity<UserMakeAPI> getUserProfile(@RequestHeader("Authorization") String authorizationHeader) {
        // Kiểm tra xem có header Authorization không
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        // Lấy access token từ header
        String accessToken = authorizationHeader.replace("Bearer ", "");

        // Giải mã access token để lấy thông tin người dùng
        User user = jwtService.decodeAccessToken(accessToken);

        // Kiểm tra xem access token có hợp lệ không
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        // Tạo đối tượng UserData và đặt thông tin người dùng
        UserData userData = new UserData();
        userData.setEmail(user.getEmail());
        userData.setPhone(user.getPhone());
        userData.setFullName(user.getFullName());
        userData.setRole(user.getRole());
        userData.setAvatar(user.getAvatar());
        userData.setId(user.getId());

        // Đặt thông tin access_token và user vào đối tượng ApiResponse
        AuthResponse authResponse = new AuthResponse();
        authResponse.setUser(userData);

        UserMakeAPI userMakeAPI = new UserMakeAPI();
        userMakeAPI.setData(authResponse);

        return new ResponseEntity<>(userMakeAPI, HttpStatus.OK);
    }

}
