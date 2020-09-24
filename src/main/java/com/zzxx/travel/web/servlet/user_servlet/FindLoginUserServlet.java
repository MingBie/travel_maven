package com.zzxx.travel.web.servlet.user_servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zzxx.travel.domain.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// 显示登录信息
@WebServlet("/FindLoginUserServlet")
public class FindLoginUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获得Session中的登录用户
        User loginUser = (User) request.getSession().getAttribute("loginUser");
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=UTF-8");
        mapper.writeValue(response.getOutputStream(), loginUser);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
