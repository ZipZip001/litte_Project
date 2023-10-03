package com.example.smallP.controller.Order;

import com.example.smallP.entity.Order;
import com.example.smallP.entity.OrderDetail;
import com.example.smallP.entity.User;
import com.example.smallP.security.JwtService;
import com.example.smallP.service.Order.Interface.OrderDetailRepository;
import com.example.smallP.service.Order.OrderApiResponse;
import com.example.smallP.service.Order.OrderRepository;
import com.example.smallP.service.Order.OrderService;
import com.example.smallP.service.User.DesginAPI.AuthResponse;
import com.example.smallP.service.User.DesginAPI.UserData;
import com.example.smallP.service.User.DesginAPI.UserMakeAPI;
import com.example.smallP.service.User.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api")
public class OrderRestController {

    public OrderService orderService;

    @Autowired
    private JwtService jwtService;
    @Autowired
    public OrderRestController(OrderService orderService) {
        this.orderService = orderService;
    }

    private UserService userService;

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @GetMapping("/order")
    public ResponseEntity<ObjectNode> getOrders(
            @RequestParam(required = false) Integer current,
            @RequestParam(required = false) Integer pageSize) {

        ObjectMapper objectMapper = new ObjectMapper();
//        List<Order> orders = orderService.findAll();

        List<Order> orders = orderRepository.findAllByOrderByCreatedAtDesc();

        int totalUsers = orders.size();

        // Kiểm tra nếu current và pageSize bị null, thì trả về toàn bộ dữ liệu
        if (current == null || pageSize == null) {
            current = 1; // Giá trị mặc định nếu current bị null
            pageSize = totalUsers; // Trả về tất cả nếu pageSize bị null
        }



        //current and pageSize
        int totalPages = (int) Math.ceil((double) totalUsers / pageSize);

        int startIndex = (current - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, totalUsers);

        List<Order> paginatedUsers = orders.subList(startIndex, endIndex);

        OrderResponse<List<Order>> response = new OrderResponse<>();
        OrderResponse.Meta meta = new OrderResponse.Meta(current, pageSize, totalPages, totalUsers);
        response.setMeta(meta);
        response.setResult(paginatedUsers);


        // Đặt "data" bên trong một đối tượng JSON bổ sung
        ObjectNode dataNode = JsonNodeFactory.instance.objectNode();
        dataNode.set("data", objectMapper.valueToTree(response));

        return new ResponseEntity<>(dataNode, HttpStatus.OK);
    }

    @PostMapping("/order")
    public ResponseEntity<ObjectNode> createOrder(@RequestBody Order orderData) {
        // Tạo đơn hàng từ dữ liệu được gửi trong orderData
        Order newOrder = new Order(
                orderData.getName(),
                orderData.getAddress(),
                orderData.getPhone(),
                orderData.getUserId(),
                null,
                orderData.getTotalPrice(),
                null,
                null
        );

        // Lưu đơn hàng vào cơ sở dữ liệu
        Order savedOrder = orderRepository.save(newOrder);

        // Thêm OrderDetail và liên kết với đơn hàng đã lưu
        for (OrderDetail detail : orderData.getDetail()) {
            detail.setOrder(savedOrder); // Liên kết với đơn hàng đã lưu
            // Lưu đối tượng OrderDetail
            orderDetailRepository.save(detail);
        }


        // Trả về phản hồi thành công
        ObjectNode response = JsonNodeFactory.instance.objectNode();
        response.put("message", "Đơn hàng đã được tạo và lưu thành công");
        response.put("orderId", savedOrder.getId()); // Trả về ID của đơn hàng đã được tạo

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/history")
    public ResponseEntity<List<Order>> getUserOrderHistory(@RequestHeader("Authorization") String authorizationHeader) {
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
        List<Order> userOrders = orderService.findByUserName(user.getFullName());

        if (userOrders.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        // Trả về danh sách đơn hàng của người dùng
        return new ResponseEntity<>(userOrders, HttpStatus.OK);
    }
}



