package com.pgs.zuul.client;


import com.pgs.zuul.payload.UserInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("auth")
public interface AuthClient {
    @GetMapping("api/auth/currentUser")
    UserInfo getCurrentUserInfo();

    @GetMapping("api/auth/isAuthenticated")
    boolean isAuthenticated();
}
