package cn.gok.mapper;

import cn.gok.entity.User;

public interface UserMapper {
    /**
     * 用户注册
     * @param user 用户注册的信息
     * @return
     */
    public int addUser(User user);
    /**
     * 通过账号查找用户是否存在，用于用户注册
     * @param username 账号
     * @return 用户存在或null
     */
    public User findUserByAccount(String username);

    /**
     * 忘记密码
     * @return
     * */
    public int reset(User user);

    /**
     * 用户登录
     * @param user
     * @return
     */
    public User login(User user);
}
