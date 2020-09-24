package com.zzxx.travel.web.servlet.code_servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zzxx.travel.domain.ResultInfo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// 校验验证码
@WebServlet("/CheckCheckServlet")
public class CheckCheckServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获得 客户端输入的验证码
        String check = request.getParameter("check");
        // 获得 Session中的验证码
        String code = (String) request.getSession().getAttribute("CHECKCODE_SERVER");
        ResultInfo info = new ResultInfo();
        // 校验验证码 封装返回结果对象
        if (check != null && code.equalsIgnoreCase(check)) {
            info.setFlag(true);
        } else {
            info.setFlag(false);
        }
        response.setContentType("application/json;charset=UTF-8");
        // Java对象 -> JSON对象
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), info);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
