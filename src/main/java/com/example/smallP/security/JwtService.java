package com.example.smallP.security;

import com.example.smallP.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Date;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {


    private final byte[] SECRET_KEY_BYTES = Keys.secretKeyFor(SignatureAlgorithm.HS256).getEncoded();
    private final String SECRET_KEY = new String(SECRET_KEY_BYTES);
    public String generateAccessToken(User user) {
        Date expirationDate = new Date(System.currentTimeMillis() + 86400000); // Token hết hạn sau 24 giờ
        return Jwts.builder()
                .setSubject(String.valueOf(user.getId()))
                .claim("email", user.getEmail())
                .claim("fullName", user.getFullName())
                .claim("role", user.getRole())
                .setIssuedAt(new Date())
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY_BYTES)
                .compact();
    }
    public User decodeAccessToken(String accessToken) {
        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY_BYTES) // Sử dụng khóa để giải mã
                    .parseClaimsJws(accessToken);

            // Lấy thông tin người dùng từ claims
            int userId = Integer.parseInt(claims.getBody().getSubject());
            String email = (String) claims.getBody().get("email");
            String fullName = (String) claims.getBody().get("fullName");
            String role = (String) claims.getBody().get("role");

            // Tạo đối tượng User
            User user = new User();
            user.setId(userId);
            user.setEmail(email);
            user.setFullName(fullName);
            user.setRole(role);

            return user;
        } catch (Exception e) {
            // Xử lý lỗi khi giải mã thất bại
            return null;
        }
    }

    public Jws<Claims> parseToken(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
    }
}
