package com.zzxx.travel.dao;

import com.zzxx.travel.domain.Favorite;

public interface FavoriteDao {
    int findCountByRid(int rid);

    Favorite findByUidAndRid(int rid, int uid);

    void insertFavoriteByUidAndRid(int rid, int uid);
}
