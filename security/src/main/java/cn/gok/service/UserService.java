package cn.gok.service;

import cn.gok.entity.User;

public interface UserService {
    public int addUser(User user) ;

    public User findUserByAccount(String username) ;

    public int reset(User user);

    public User login(User user);
}
