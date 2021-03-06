package com.pgs.chatservice.client.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class AuthorizationRequestInterceptor implements RequestInterceptor {

    private static final String AUTHORIZATION_HEADER = "Authorization";

    @Override
    public void apply(RequestTemplate requestTemplate) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            return;
        }
        String auth = requestAttributes.getRequest().getHeader(AUTHORIZATION_HEADER);
        if (auth == null) {
            return;
        }
        requestTemplate.header(AUTHORIZATION_HEADER, auth);
    }
}
