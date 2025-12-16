package org.openapitools.interceptor;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.openapitools.configuration.AuthContext;
import org.openapitools.configuration.SpringSecurityConfig;
import org.openapitools.model.InlineResponse;
import org.openapitools.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

/**
 * 对于需要登录的接口，校验 header 中的 token 是否存在且有效。
 */

@Component
@PropertySource("classpath:/application.properties")
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    SpringSecurityConfig springSecurityConfig;

    @Autowired
    UserService userService;

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
                             @NonNull Object handler) throws Exception {
        Boolean pass = false;
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String jwt = authHeader.substring(7);
            Claims claims = null;

            try {
                claims = Jwts.parser().setSigningKey(springSecurityConfig.getJwtSecret()).parseClaimsJws(jwt).getBody();
            } catch (Exception e) {
                pass = false;
            }

            if (claims != null && claims.getExpiration().after(new Date())) {
                String subject = claims.getSubject();
                Object version = claims.get("version");

                if (subject != null && version != null) {
                    Long userId = Long.parseLong(subject);
                    Integer userVersion = Integer.parseInt(version.toString());

                    // 从 Redis 或者数据库获取用户的 Version，和 Token 中的 Version 对比，如果不一致则说明 Token 已经过期
                    if (userVersion == userService.getUserById(userId).getUserVersion()) {
                        // 将用户 ID 和 Version 设置到 AuthContext 中
                        AuthContext.setUserId(userId);
                        AuthContext.setUserVersion(userVersion);
                        pass = true;
                    }
                }
            }
        }

        if (!pass) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            InlineResponse inlineResponse = new InlineResponse();
            inlineResponse.setMessage("token 错误，无权限或会话已过期");
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(inlineResponse);

            response.getWriter().write(json);
        }

        return pass;
    }

    @Override
    public void afterCompletion(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
                                @NonNull Object handler, @Nullable Exception ex)
            throws Exception {
        AuthContext.clear();
    }
}