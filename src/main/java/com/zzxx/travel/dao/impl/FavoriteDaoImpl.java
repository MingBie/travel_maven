package com.zzxx.travel.dao.impl;

import com.zzxx.travel.dao.FavoriteDao;
import com.zzxx.travel.domain.Favorite;
import com.zzxx.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Date;
import java.util.List;

public class FavoriteDaoImpl implements FavoriteDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    // 通过rid查找 旅游线路被收藏的次数
    public int findCountByRid(int rid) {
        String sql = "select count(*) from tab_favorite where rid = ?";
        return template.queryForObject(sql, Integer.class, rid);
    }

    @Override
    // 用过uid和rid查找 用户是否收藏此旅游路线
    public Favorite findByUidAndRid(int rid, int uid) {
        String sql = "select * from tab_favorite where rid = ? and uid = ?";
        List<Favorite> list = template.query(sql, new BeanPropertyRowMapper<>(Favorite.class), rid, uid);
        return list.size() == 0 ? null : list.get(0);
    }

    @Override
    // 用过uid和rid插入 收藏 此旅游路线
    public void insertFavoriteByUidAndRid(int rid, int uid) {
        String sql = "insert into tab_favorite values(?,?,?)";
        template.update(sql, rid, new Date(), uid);
    }
}
