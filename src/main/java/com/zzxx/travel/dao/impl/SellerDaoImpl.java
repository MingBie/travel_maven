package com.zzxx.travel.dao.impl;

import com.zzxx.travel.dao.SellerDao;
import com.zzxx.travel.domain.Seller;
import com.zzxx.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class SellerDaoImpl implements SellerDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    // 根据sid 查询seller
    public Seller findSellerBySid(int sid) {
        String sql = "select * from tab_seller where sid = ?";
        List<Seller> list = template.query(sql, new BeanPropertyRowMapper<>(Seller.class), sid);
        return list.get(0);
    }
}
