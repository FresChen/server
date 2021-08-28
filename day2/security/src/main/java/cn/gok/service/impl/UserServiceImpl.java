package cn.gok.service.impl;

import cn.gok.entity.User;
import cn.gok.mapper.UserMapper;
import cn.gok.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired(required = false)
    private UserMapper userMapper;
    @Override
    public int addUser(User user) {
        return userMapper.addUser(user);
    }

    @Override
    public User findUserByAccount(String userName) {
        return userMapper.findUserByAccount(userName);
    }

    @Override
    public int reset(User user) {
        return userMapper.reset(user);
    }
//    登录
    @Override
    public User login(User user) {
        return userMapper.login(user);
    }

    //查询
    @Override
    public PageInfo<User> list(String searchKey, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<User> list=userMapper.list(searchKey);
        PageInfo<User> pageInfo=new PageInfo<>(list);
        return pageInfo;
    }
    //删除
    @Override
    public int del(User user) {
        return userMapper.del(user);
    }
    //增加
    @Override
    public int add(User user) {
        return userMapper.add(user);
    }

    @Override
    public int update(User user) {
        return userMapper.update(user);
    }

    //详情
    @Override
    public User detail(User user) {
        return userMapper.detail(user);
    }


}
