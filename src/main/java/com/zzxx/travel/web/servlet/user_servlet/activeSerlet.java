package com.zzxx.travel.web.servlet.user_servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zzxx.travel.service.UserService;
import com.zzxx.travel.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// 判断 账户是否激活
@WebServlet("/activeSerlet")
public class activeSerlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获得 状态码
        String code = request.getParameter("code");
        // 调用service 返回是否激活成功
        UserService us = new UserServiceImpl();
        boolean flag = us.active(code);
        // 判断 是否成功
        if (flag) {
            // response.sendRedirect(request.getContextPath() + "/login.html");
            response.getWriter().println("激活成功！<a href='/travel/login.html'>登录</a>");
        } else {
            response.getWriter().println("激活失败");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
