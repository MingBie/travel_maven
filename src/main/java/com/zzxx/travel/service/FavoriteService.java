package com.zzxx.travel.service;

public interface FavoriteService {
    int findFavoriteCountByRid(int rid);

    boolean findFavoriteByRidAndUid(int rid, int uid);

    void addFavoriteByRidAndUid(int rid, int uid);
}
