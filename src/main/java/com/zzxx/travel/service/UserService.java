package com.zzxx.travel.service;

import com.zzxx.travel.domain.User;

public interface UserService {
    boolean findUserByUsername(String username);

    boolean registerUser(User user);

    boolean active(String code);

    User login(String username, String password) throws Exception;
}
