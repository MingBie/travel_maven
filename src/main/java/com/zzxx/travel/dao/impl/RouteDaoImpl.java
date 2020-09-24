package com.zzxx.travel.dao.impl;

import com.zzxx.travel.dao.RouteDao;
import com.zzxx.travel.domain.Route;
import com.zzxx.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

public class RouteDaoImpl implements RouteDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    // 根据cid查询 总信息数量
    @Override
    public int findTotalCountByCid(int cid, String rname) {
        StringBuilder _sql = new StringBuilder("select count(*) from tab_route where 1=1");
        // 用来存储 查询条件
        List<Object> params = new ArrayList<>();
        // 判断是否存在
        // 若存在
        // 1. 拼接 SQL语句
        // 2. 把条件存入 查询条件集合中
        if (cid != -1) {
            _sql.append(" and cid = ? ");
            params.add(cid);
        }
        if (rname != null && rname != "" && !rname.equals("null")) {
            _sql.append(" and rname like ? ");
            params.add("%"+rname+"%");
        }
        Integer count = template.queryForObject(_sql.toString(), Integer.class, params.toArray());
        return count;
    }

    // 根据cid分页查询 旅游路线信息
    @Override
    public List<Route> findPageByCid(int cid, int start, int pageSize, String rname) {
        StringBuilder _sql = new StringBuilder("select * from tab_route where 1=1");
        // 用来存储 查询条件
        List<Object> params = new ArrayList<>();
        // 判断 cid和rname 是否存在
        // 若存在
        // 1. 拼接 sql语句
        // 2. 把条件存入 查询条件集合中
        if (cid != -1) {
            _sql.append(" and cid = ? ");
            params.add(cid);
        }
        if (rname != null && rname != "" && !rname.equals("null")) {
            _sql.append(" and rname like ? ");
            params.add("%"+rname+"%");
        }
        // 拼接 分页sql语句
        // 把条件加入集合
        _sql.append(" limit ?,? ");
        params.add(start);
        params.add(pageSize);
        List<Route> list = template.query(_sql.toString(), new BeanPropertyRowMapper<>(Route.class), params.toArray());
        return list;
    }

    @Override
    // 根据rid 查询route
    public Route findRouteByRid(int rid) {
        String sql = "select * from tab_route where rid = ?";
        List<Route> list = template.query(sql, new BeanPropertyRowMapper<>(Route.class), rid);
        return list.get(0);
    }
}
