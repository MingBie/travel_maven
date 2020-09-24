package com.zzxx.travel.service.impl;

import com.zzxx.travel.dao.UserDao;
import com.zzxx.travel.dao.impl.UserDaoImpl;
import com.zzxx.travel.domain.User;
import com.zzxx.travel.service.UserService;
import com.zzxx.travel.util.MailUtils;
import com.zzxx.travel.util.UuidUtil;
import com.zzxx.travel.util.exception.StatusException;
import com.zzxx.travel.util.exception.UserNotFoundException;

public class UserServiceImpl implements UserService {
    private UserDao ud = new UserDaoImpl();

    // 通过 username进行查找
    @Override
    public boolean findUserByUsername(String username) {
        User user = ud.findUserByUsername(username);
        // 判断 用户名是否已经存在
        if (user != null) {
            return true;
        }
        return false;
    }

    // 注册 user(插入user数据)
    @Override
    public boolean registerUser(User user) {
        // 判断是否注册成功(可能会有异常)
        try {
            // 设置 激活状态
            user.setStatus("N");
            // 设置 状态码
            user.setCode(UuidUtil.getUuid());
            ud.insertUser(user);
            // 发送邮箱
            String text = "<a href=http://localhost:80/travel/user/active?code=" + user.getCode() + ">账号激活</a>";
            MailUtils.sendMail("389395995@qq.com", text, "指针旅游账户激活");
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // 通过code 修改用户的status
    @Override
    public boolean active(String code) {
        int count = ud.updateStatusByCode(code);
        return count != 0;
    }

    // 通过 username和password进行查找
    @Override
    public User login(String username, String password) throws Exception {
        User user = ud.selectUserByUsernameAndPassword(username, password);
        // 判断 用户是否存在
        if (user != null) {
            // 判断 用户是否激活
            if (user.getStatus().equals("Y")) {
                // 存在且激活 返回true
                return user;
            } else {
                // 未激活 抛出异常
                throw new StatusException("账号未激活");
            }
        }
        // 用户不存在 抛出异常
        throw new UserNotFoundException("账号/密码输入错误");
    }
}
