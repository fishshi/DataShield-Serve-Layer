package com.datashield.interceptor;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.datashield.component.RedisComponent;
import com.datashield.entity.UserInfo;
import com.datashield.mapper.UserInfoMapper;
import com.datashield.util.JwtUtil;
import com.datashield.util.ResultUtil;
import com.datashield.util.UserContextUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 登录拦截器类
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    private UserInfoMapper userMapper;
    @Autowired
    private RedisComponent redisComponent;

    /**
     * 预处理方法, 在请求处理之前进行调用
     *
     * @param request  请求对象
     * @param response 响应对象
     * @param handler  处理器对象
     *
     * @return true: 继续流程; false: 中断流程
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String token = request.getHeader("Authorization");
        String id = JwtUtil.verifyToken(token);
        if (id != null) {
            if (redisComponent.hasKey(id) && redisComponent.get(id).equals(token)) {
                UserInfo user = userMapper.selectById(id);
                if (user != null) {
                    UserContextUtil.setUser(user);
                    redisComponent.expire(id, 1, TimeUnit.DAYS);
                    return true;
                }
            }
        }
        writeUnauthorizedResponse(response);
        return false;

    }

    /**
     * 后处理方法, 在整个请求处理完毕之后调用
     * 
     * @param request  请求对象
     * @param response 响应对象
     * @param handler  处理器对象
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
            @Nullable Exception ex) throws Exception {
        UserContextUtil.clear();
    }

    /**
     * 将用户未授权写入响应
     *
     * @param response 响应对象
     */
    private void writeUnauthorizedResponse(HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(new ObjectMapper().writeValueAsString(ResultUtil.unauthorized()));
        response.getWriter().flush();
    }
}
