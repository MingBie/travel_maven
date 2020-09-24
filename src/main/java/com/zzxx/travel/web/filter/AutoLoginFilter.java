package com.zzxx.travel.web.filter;

import com.zzxx.travel.domain.User;
import com.zzxx.travel.service.UserService;
import com.zzxx.travel.service.impl.UserServiceImpl;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/*")
// 自动登录过滤器
public class AutoLoginFilter implements Filter {
    private UserService us = new UserServiceImpl();
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
            // 获得请求中的Cookie
            Cookie[] cookies = request.getCookies();
            String username = null;
            String password = null;
            if (cookies != null) {
                // 判断是否存在 username和password
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("username")) {
                        username = cookie.getValue();
                    }
                    if (cookie.getName().equals("password")) {
                        password = cookie.getValue();
                    }
                }
            }
            // 判断 账号密码是否正确
            if (username != null && password != null) {
                try {
                    // 正确 把账号密码存在Session中
                    User loginUser = us.login(username, password);
                    request.getSession().setAttribute("loginUser", loginUser);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
