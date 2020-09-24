package com.zzxx.travel.service.impl;

import com.zzxx.travel.dao.FavoriteDao;
import com.zzxx.travel.dao.impl.FavoriteDaoImpl;
import com.zzxx.travel.domain.Favorite;
import com.zzxx.travel.service.FavoriteService;

public class FavoriteServiceImpl implements FavoriteService {
    private FavoriteDao favoriteDao = new FavoriteDaoImpl();

    @Override
    // 查找 旅游线路被收藏的次数
    public int findFavoriteCountByRid(int rid) {
        return favoriteDao.findCountByRid(rid);
    }

    @Override
    // 判断是否被此用户收藏
    public boolean findFavoriteByRidAndUid(int rid, int uid) {
        Favorite favorite = favoriteDao.findByUidAndRid(rid, uid);
        if (favorite != null) {
            return true;
        }
        return false;
    }

    @Override
    // 添加收藏
    public void addFavoriteByRidAndUid(int rid, int uid) {
        favoriteDao.insertFavoriteByUidAndRid(rid, uid);
    }
}
