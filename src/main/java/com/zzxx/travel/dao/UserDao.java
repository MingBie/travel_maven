package com.zzxx.travel.dao;

import com.zzxx.travel.domain.User;

public interface UserDao {
    User findUserByUsername(String username);
    void insertUser(User user);

    int updateStatusByCode(String code);

    User selectUserByUsernameAndPassword(String username, String password);
}
