package com.pgs.chatservice.client;


import com.pgs.chatservice.payload.UserInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("auth")
public interface AuthClient {

    @GetMapping("api/auth/currentUser")
    UserInfo getCurrentUserInfo();
}
