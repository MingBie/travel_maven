package com.zzxx.travel.dao;

import com.zzxx.travel.domain.Route;

import java.util.List;

public interface RouteDao {
    int findTotalCountByCid(int cid, String rname);

    List<Route> findPageByCid(int cid, int start, int pageSize, String rname);

    Route findRouteByRid(int rid);
}
