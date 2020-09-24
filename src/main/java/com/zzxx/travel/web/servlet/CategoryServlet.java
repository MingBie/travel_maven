package com.zzxx.travel.web.servlet;

import com.zzxx.travel.domain.Category;
import com.zzxx.travel.service.CategoryService;
import com.zzxx.travel.service.impl.CategoryServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/category/*")
public class CategoryServlet extends BaseServlet {
    private CategoryService cs = new CategoryServiceImpl();

    // 查询导航栏信息
    public void findAll(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Category> list = cs.findAll();
        writeVaule(response, list);
    }
}
