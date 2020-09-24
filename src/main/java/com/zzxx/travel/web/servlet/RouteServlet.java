package com.zzxx.travel.web.servlet;

import com.zzxx.travel.domain.PageBean;
import com.zzxx.travel.domain.Route;
import com.zzxx.travel.service.RouteService;
import com.zzxx.travel.service.impl.RouteServiceImpl;
import com.zzxx.travel.util.DownLoadUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;

@WebServlet("/route/*")
public class RouteServlet extends BaseServlet {
    private RouteService rs = new RouteServiceImpl();

    // 分页查询 旅游路线
    public void findPageRoute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 获得参数 cid currentPage pageSize
        String cid = request.getParameter("cid");
        String currentPage = request.getParameter("currentPage");
        String pageSize = request.getParameter("pageSize");
        // 获得 rname 并解码
        String rname = request.getParameter("rname");

        // 判断 currentPage pageSize 是否有值
        if (currentPage == null || currentPage.length() == 0) {
            currentPage = "1";
        }
        if (pageSize == null || pageSize.length() == 0) {
            pageSize = "8";
        }
        // 调用service分页查询
        // 返回PageBean分页类
        PageBean pageBean = rs.findPage(cid, currentPage, pageSize, rname);
        writeVaule(response, pageBean);
    }

    // 查询旅游路线详情
    public void routeDeatil(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 获得 旅游路线 rid
        int rid = Integer.parseInt(request.getParameter("rid"));
        // 调用RouteService获得 route对象
        Route route = rs.findRouteDeatilByRid(rid);
        writeVaule(response, route);
    }
}
