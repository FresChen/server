package cn.gok.mapper;

import cn.gok.entity.Server;

import java.util.List;

public interface ServerMapper {
    //列表查询
    public List<Server> list(String searchKey);

    //新增
    public int save(Server server);
    /**
     * 通过ip查找用户是否存在，用于添加编辑
     * @param ip
     * @return 用户存在或null
     */
    public Server findServer(String ip);
    /**
     * 通过user查找user表是否存在，用于用户添加
     * @param
     * @return 用户存在或null
     */
    public Server find(String userName);
    //编辑
    public int update(Server server);

    public int updateName(Server server);

    public Server findupdate(String ip,Long id);
    //删除
    public int delete(Server server);

    //详情
    public Server detail(Server server);

    //查询state
    public List<Server> selectState();
}
