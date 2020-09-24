package com.zzxx.travel.dao.impl;

import com.zzxx.travel.dao.UserDao;
import com.zzxx.travel.domain.User;
import com.zzxx.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class UserDaoImpl implements UserDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public User findUserByUsername(String username) {
        String sql = "select * from tab_user where username = ?";
        List<User> list = template.query(sql, new BeanPropertyRowMapper<>(User.class), username);
        return list.size() == 0 ? null : list.get(0);
    }

    @Override
    public void insertUser(User user) {
        String sql = "insert into tab_user values(null,?,?,?,?,?,?,?,?,?)";
        template.update(sql, user.getUsername(), user.getPassword(), user.getName(), user.getBirthday(), user.getSex(), user.getTelephone(), user.getEmail(), user.getStatus(), user.getCode());
    }

    @Override
    public int updateStatusByCode(String code) {
        String sql = "update tab_user set status = 'Y' where code = ?";
        int count = template.update(sql, code);
        return count;
    }

    @Override
    public User selectUserByUsernameAndPassword(String username, String password) {
        String sql = "select * from tab_user where username = ? and password = ?";
        // 判断 是否找到用户
        try {
            // 存在返回 用户
            User user = template.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), username, password);
            return user;
        } catch (Exception e) {
            // 不存在 返回null
            return null;
        }
    }
}
