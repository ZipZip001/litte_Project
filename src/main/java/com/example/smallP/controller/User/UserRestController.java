package com.example.smallP.controller.User;

import com.example.smallP.controller.User.UserResponse;
import com.example.smallP.dao.User.UserDAO;
import com.example.smallP.entity.User;
import com.example.smallP.security.JwtService;
import com.example.smallP.security.LogoutRequest;
import com.example.smallP.security.UserPassword;
import com.example.smallP.security.UserRepository;
import com.example.smallP.service.Token.AccessTokenManager;
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


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

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
            @RequestParam(required = false) Integer pageSize,
            @RequestParam(required = false) String fullName,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String phone

    ){
        ObjectMapper objectMapper = new ObjectMapper();
        List<User> users = userService.findAll();

        List<User> filteredUsers = new ArrayList<>(users);


        if (fullName != null && !fullName.isEmpty()) {
            String searchText = fullName.toLowerCase(); // Chuyển đổi sang chữ thường để tìm kiếm không phân biệt hoa thường
            filteredUsers = filteredUsers.stream()
                    .filter(user -> user.getFullName().toLowerCase().contains(searchText))
                    .collect(Collectors.toList());
        }
        if (email != null && !email.isEmpty()) {
            String searchText = email.toLowerCase(); // Chuyển đổi sang chữ thường để tìm kiếm không phân biệt hoa thường
            filteredUsers = filteredUsers.stream()
                    .filter(user -> user.getEmail().toLowerCase().contains(searchText))
                    .collect(Collectors.toList());
        }
        if (phone != null && !phone.isEmpty()) {
            String searchText = phone.toLowerCase(); // Chuyển đổi sang chữ thường để tìm kiếm không phân biệt hoa thường
            filteredUsers = filteredUsers.stream()
                    .filter(user -> user.getPhone().contains(searchText))
                    .collect(Collectors.toList());
        }

        int totalUsers = filteredUsers.size();

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

    @Autowired
    private UserRepository userRepository;


    // Endpoint cho việc cập nhật thông tin người dùng dựa trên id
    @PutMapping("/user/{id}")
    public User updateUser(@PathVariable int id, @RequestBody User newData) {
        return userService.updateUser(id, newData);
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
        newUser.setRole("USER");
        newUser.setActive(true);
        newUser.setAvatar("https://png.pngtree.com/png-vector/20191113/ourlarge/pngtree-avatar-human-man-people-person-profile-user-abstract-circl-png-image_1983926.jpg");

        // Kiểm tra xem email đã tồn tại hay chưa
        if (userPassword.isEmailExists(newUser.getEmail())) {
            User existingUser = new User(); // Tạo đối tượng User tạm thời không trả về mess được
            return new ResponseEntity<>(existingUser, HttpStatus.BAD_REQUEST);
        }
        // Lấy thời gian hiện tại
        Date currentTime = new Date();

        // Định dạng thời gian thành "yyyy-MM-dd"
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = dateFormat.format(currentTime);

        try {
            // Chuyển đổi chuỗi thành kiểu Date
            Date date = dateFormat.parse(formattedDate);

            newUser.setCreatedAt(date);
            newUser.setUpdatedAt(date);

            // Lưu tài khoản vào cơ sở dữ liệu
            User savedUser = userPassword.registerUser(newUser);

            return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
        } catch (ParseException e) {
            // Xử lý lỗi nếu có lỗi xảy ra trong quá trình chuyển đổi
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
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

        // Truy vấn cơ sở dữ liệu để lấy thông tin chi tiết của người dùng
        Optional<User> userOptional = userRepository.findById((long) user.getId());

        if (userOptional.isPresent()) {
            // Tạo đối tượng UserData và đặt thông tin người dùng từ cơ sở dữ liệu
            User dbUser = userOptional.get();
            UserData userData = new UserData();
            userData.setEmail(dbUser.getEmail());
            userData.setPhone(dbUser.getPhone());
            userData.setFullName(dbUser.getFullName());
            userData.setRole(dbUser.getRole());
            userData.setAvatar(dbUser.getAvatar());
            userData.setId(dbUser.getId());

            // Đặt thông tin access_token và user vào đối tượng ApiResponse
            AuthResponse authResponse = new AuthResponse();
            authResponse.setUser(userData);

            UserMakeAPI userMakeAPI = new UserMakeAPI();
            userMakeAPI.setData(authResponse);

            return new ResponseEntity<>(userMakeAPI, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @Autowired
    private AccessTokenManager accessTokenManager;

    @PostMapping("/user/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String authorizationHeader) {
        // Kiểm tra xem có header Authorization không
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            // Trích xuất access token từ header
            String accessToken = authorizationHeader.replace("Bearer ", "");

            // Kiểm tra xem access token có hợp lệ không
            if (jwtService.validateAccessToken(accessToken)) {
                // Nếu access token hợp lệ, hãy thực hiện xóa access token khỏi danh sách đang hoạt động
                accessTokenManager.deleteAccessToken(accessToken);

                // Trả về thông báo thành công
                return ResponseEntity.ok("Logout successful");
            }
        }

        // Nếu access token không hợp lệ hoặc không tồn tại, trả về lỗi không xác thực
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }




}
