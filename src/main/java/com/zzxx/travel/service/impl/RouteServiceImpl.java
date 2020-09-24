package com.zzxx.travel.service.impl;

import com.zzxx.travel.dao.RouteDao;
import com.zzxx.travel.dao.RouteImgDao;
import com.zzxx.travel.dao.SellerDao;
import com.zzxx.travel.dao.impl.RouteDaoImpl;
import com.zzxx.travel.dao.impl.RouteImgDaoImpl;
import com.zzxx.travel.dao.impl.SellerDaoImpl;
import com.zzxx.travel.domain.PageBean;
import com.zzxx.travel.domain.Route;
import com.zzxx.travel.domain.RouteImg;
import com.zzxx.travel.domain.Seller;
import com.zzxx.travel.service.RouteService;

import java.util.List;

public class RouteServiceImpl implements RouteService {
    private RouteDao rd = new RouteDaoImpl();
    private SellerDao sd = new SellerDaoImpl();
    private RouteImgDao routeImgDao = new RouteImgDaoImpl();

    // 分页查询 旅游路线信息
    @Override
    public PageBean findPage(String _cid, String _currentPage, String _pageSize, String rname) {
        // 新建一个分页类 PageBean
        PageBean<Route> pageBean = new PageBean<>();
        // cid不存在时设置为-1
        int cid = -1;
        // 判断 cid是否存在
        if (_cid != null && _cid != "") {
            cid = Integer.parseInt(_cid);
        }
        int currentPage = Integer.parseInt(_currentPage);
        int pageSize = Integer.parseInt(_pageSize);
        // 获得 totalCount
        int totalCount = rd.findTotalCountByCid(cid, rname);
        // 获得 list
        int start = (currentPage - 1) * pageSize; // 页面起始位置
        List<Route> list = rd.findPageByCid(cid, start, pageSize, rname);
        // 计算 totalPage
        int totalPage = (totalCount + pageSize - 1) / pageSize;
        // 封装 PageBean
        pageBean.setList(list);
        pageBean.setCurrentPage(currentPage);
        pageBean.setPageSize(pageSize);
        pageBean.setTotalCount(totalCount);
        pageBean.setTotalPage(totalPage);
        return pageBean;
    }

    // 查询 旅游路线详细信息
    @Override
    public Route findRouteDeatilByRid(int rid) {
        // 1. 根据rid 调用RouteDao 获得 route
        Route route = rd.findRouteByRid(rid);
        // 2. 根据route中的sid 调用SellerDao 获得seller
        // 获得 route中sid
        int sid = route.getSid();
        Seller seller = sd.findSellerBySid(sid);
        // 3. 根据rid 调用RouteImgDao 获得routeImgList
        List<RouteImg> routeImgList = routeImgDao.findRouteImgListByRid(rid);
        // 4. 将 seller和routeImgList 注入 route中
        route.setSeller(seller);
        route.setRouteImgList(routeImgList);
        return route;
    }
}
