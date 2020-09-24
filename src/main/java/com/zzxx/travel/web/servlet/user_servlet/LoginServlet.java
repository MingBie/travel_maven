package com.zzxx.travel.web.servlet.user_servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zzxx.travel.domain.ResultInfo;
import com.zzxx.travel.domain.User;
import com.zzxx.travel.service.UserService;
import com.zzxx.travel.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// 登录
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获得 登录请求信息
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String check = request.getParameter("check");
        ResultInfo info = new ResultInfo();
        // 判断 验证码是否输入正确
        // 获得Session中的验证码
        String checkcode_server = (String) request.getSession().getAttribute("CHECKCODE_SERVER");
        if (check != null && check.equalsIgnoreCase(checkcode_server)) {
            UserService us = new UserServiceImpl();
            // 调用service 封装结果信息
            // 登录成功 设置状态为true
            // 登录失败 设置状态为false 设置错误信息
            try {
                User loginUser = us.login(username, password);
                // 把用户存在Session中
                request.getSession().setAttribute("loginUser", loginUser);
                info.setFlag(true);
            } catch (Exception e) {
                info.setFlag(false);
                info.setErrorMsg(e.getMessage());
            }
        } else {
            info.setFlag(false);
            info.setErrorMsg("验证码输入错误");
        }
        response.setContentType("application/json;charset=UTF-8");
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), info);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
