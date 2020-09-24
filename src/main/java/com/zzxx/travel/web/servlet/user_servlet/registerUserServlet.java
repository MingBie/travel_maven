package com.zzxx.travel.web.servlet.user_servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zzxx.travel.domain.ResultInfo;
import com.zzxx.travel.domain.User;
import com.zzxx.travel.service.UserService;
import com.zzxx.travel.service.impl.UserServiceImpl;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

// 注册账户
@WebServlet("/registerUserServlet")
public class registerUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获得请求数据并封装
        User user = new User();
        try {
            BeanUtils.populate(user, request.getParameterMap());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        UserService us = new UserServiceImpl();
        boolean flag = us.registerUser(user);
        // 封装JSON对象
        ResultInfo info = new ResultInfo();
        info.setFlag(flag);
        // 如果错误 设置 错误信息
        if (!flag) {
            info.setErrorMsg("注册失败");
        }
        response.setContentType("application/json;charset=UTF-8");
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), info);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
