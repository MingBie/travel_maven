package com.zzxx.travel.web.servlet;

import com.zzxx.travel.domain.ResultInfo;
import com.zzxx.travel.service.FavoriteService;
import com.zzxx.travel.service.impl.FavoriteServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/favorite/*")
public class FavoriteServlet extends BaseServlet {
    private FavoriteService favoriteService = new FavoriteServiceImpl();

    // 查找路线收藏次数
    public void favoriteCount(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int rid = Integer.parseInt(request.getParameter("rid"));
        Integer favoriteCount = favoriteService.findFavoriteCountByRid(rid);
        writeVaule(response, favoriteCount);
    }

    // 判断是否收藏
    public void ifFavorite(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int rid = Integer.parseInt(request.getParameter("rid"));
        int uid = Integer.parseInt(request.getParameter("uid"));
        boolean flag = favoriteService.findFavoriteByRidAndUid(rid, uid);
        ResultInfo info = new ResultInfo();
        info.setFlag(flag);
        writeVaule(response, info);
    }

    // 收藏路线
    public void addFavorite(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int rid = Integer.parseInt(request.getParameter("rid"));
        int uid = Integer.parseInt(request.getParameter("uid"));
        favoriteService.addFavoriteByRidAndUid(rid, uid);
    }
}
