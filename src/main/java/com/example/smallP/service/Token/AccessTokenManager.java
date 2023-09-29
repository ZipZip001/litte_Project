package com.example.smallP.service.Token;

import java.util.HashSet;
import java.util.Set;
import org.springframework.stereotype.Component;

@Component
public class AccessTokenManager {

    private Set<String> activeTokens = new HashSet<>();

    // Thêm access token vào danh sách đang hoạt động
    public void addAccessToken(String accessToken) {
        activeTokens.add(accessToken);
    }

    // Kiểm tra xem access token có tồn tại trong danh sách đang hoạt động không
    public boolean isAccessTokenValid(String accessToken) {
        return activeTokens.contains(accessToken);
    }

    // Xóa access token khỏi danh sách đang hoạt động
    public void deleteAccessToken(String accessToken) {
        activeTokens.remove(accessToken);
    }
}
