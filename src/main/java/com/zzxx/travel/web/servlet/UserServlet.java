package com.zzxx.travel.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zzxx.travel.domain.ResultInfo;
import com.zzxx.travel.domain.User;
import com.zzxx.travel.service.UserService;
import com.zzxx.travel.service.impl.UserServiceImpl;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

@WebServlet("/user/*")
public class UserServlet extends BaseServlet {
    // 检查用户名是否存在
    public void checkUsername(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 获得 username
        String username = request.getParameter("username");
        // 调用 service
        UserService us = new UserServiceImpl();
        boolean flag = us.findUserByUsername(username);
        // 封装 返回结果信息对象
        ResultInfo info = new ResultInfo();
        info.setFlag(!flag);
        writeVaule(response, info);
    }

    // 注册用户
    public void register(HttpServletRequest request, HttpServletResponse response) throws IOException {
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
        writeVaule(response, info);
    }

    // 账号激活
    public void active(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 获得 状态码
        String code = request.getParameter("code");
        // 调用service 返回是否激活成功
        UserService us = new UserServiceImpl();
        boolean flag = us.active(code);
        // 判断 是否成功
        response.setContentType("text/html;charset=utf-8");
        if (flag) {
            // response.sendRedirect(request.getContextPath() + "/login.html");
            response.getWriter().println("激活成功！<a href='/travel/login.html'>登录</a>");
        } else {
            response.getWriter().println("激活失败！请联系管理员");
        }
    }

    // 登录
    public void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 获得 登录请求信息
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String check = request.getParameter("check");
        // 获得 自动登录状态
        String autoLogin = request.getParameter("autoLogin");
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
                // 判断是否勾选自动登录
                if (autoLogin != null && autoLogin.equals("autoLogin")) {
                    // 将账号密码保存在Cookie中
                    Cookie usernameCookie = new Cookie("username", username);
                    Cookie passwordCookie = new Cookie("password", password);
                    // 设置最大存储时间
                    usernameCookie.setMaxAge(Integer.MAX_VALUE);
                    passwordCookie.setMaxAge(Integer.MAX_VALUE);
                    // 设置路径
                    usernameCookie.setPath(request.getContextPath());
                    passwordCookie.setPath(request.getContextPath());
                    // 保存
                    response.addCookie(usernameCookie);
                    response.addCookie(passwordCookie);
                }
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
        writeVaule(response, info);
    }

    // 显示登录用户
    public void findLoginUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 获得Session中的登录用户
        User loginUser = (User) request.getSession().getAttribute("loginUser");
        writeVaule(response, loginUser);
    }

    // 退出登录
    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 删除 登录用户信息
        request.getSession().removeAttribute("loginUser");
        // 替换 Cookie中保存的登录信息
        Cookie usernameCookie = new Cookie("username", "");
        Cookie passwordCookie = new Cookie("password", "");
        usernameCookie.setPath("/travel");
        passwordCookie.setPath("/travel");
        response.addCookie(usernameCookie);
        response.addCookie(passwordCookie);
        // 重定向 登录界面
        response.sendRedirect(request.getContextPath() + "/login.html");
    }
}
