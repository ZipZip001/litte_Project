//package com.example.smallP.controller.History;
//
//import com.example.smallP.entity.User;
//import com.example.smallP.security.JwtService;
//import com.example.smallP.security.UserRepository;
//import com.example.smallP.service.Order.OrderRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestHeader;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api")
//public class PurchaseHistoryController {
//
//    @Autowired
//    private OrderRepository orderRepository;
//
//    @Autowired
//    private JwtService jwtService;
//    @Autowired
//    private UserRepository userRepository;
//    @Autowired
//    private PurchaseHistoryController purchaseHistoryController;
//
//    @GetMapping("/user")
//    public ResponseEntity<List<PurchaseHistory>> getUserPurchaseHistory(@RequestHeader("Authorization") String authorizationHeader) {
//        // Kiểm tra xem header "Authorization" có chứa "Bearer " không
//        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
//            // Lấy access_token từ header
//            String accessToken = authorizationHeader.substring(7); // Loại bỏ "Bearer " từ header
//
//            // Giải mã access_token để lấy thông tin người dùng
//            User user = jwtService.decodeAccessToken(accessToken);
//
//            if (user != null) {
//                // Nếu giải mã thành công, bạn có thể sử dụng thông tin người dùng để truy vấn lịch sử mua hàng
//                List<PurchaseHistory> purchaseHistory = purchaseHistoryService.getUserPurchaseHistory(user);
//
//                return new ResponseEntity<>(purchaseHistory, HttpStatus.OK);
//            }
//        }
//
//        // Nếu access_token không hợp lệ hoặc không có trong header "Authorization", trả về lỗi UNAUTHORIZED
//        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//    }
//}
