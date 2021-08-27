package cn.gok.service.impl;

import cn.gok.entity.User;
import cn.gok.mapper.UserMapper;
import cn.gok.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired(required = false)
    private UserMapper userMapper;
    @Override
    public int addUser(User user) {
        return userMapper.addUser(user);
    }

    @Override
    public User findUserByAccount(String username) {
        return userMapper.findUserByAccount(username);
    }

    @Override
    public int reset(User user) {
        return userMapper.reset(user);
    }

    @Override
    public User login(User user) {
        return userMapper.login(user);
    }
}
