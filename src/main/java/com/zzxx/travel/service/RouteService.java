package com.zzxx.travel.service;

import com.zzxx.travel.domain.PageBean;
import com.zzxx.travel.domain.Route;

public interface RouteService {
    PageBean findPage(String cid, String currentPage, String pageSize, String rname);

    Route findRouteDeatilByRid(int rid);
}
