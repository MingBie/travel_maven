package com.zzxx.travel.web.servlet.user_servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zzxx.travel.domain.ResultInfo;
import com.zzxx.travel.service.UserService;
import com.zzxx.travel.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// 注册 检查username是否存在
@WebServlet("/CheckUsernameServlet")
public class CheckUsernameServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获得 username
        String username = request.getParameter("username");
        // 调用 service
        UserService us = new UserServiceImpl();
        boolean flag = us.findUserByUsername(username);
        // 封装 返回结果信息对象
        ResultInfo info = new ResultInfo();
        info.setFlag(!flag);
        response.setContentType("application/json;charset=UTF-8");
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), info);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
