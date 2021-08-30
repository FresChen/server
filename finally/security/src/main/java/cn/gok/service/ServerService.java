package cn.gok.service;

import cn.gok.entity.Server;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ServerService {
    public PageInfo<Server> list(String searchKey, Integer pageNum, Integer pageSize);


    public int save(Server server);

    public Server findServer(String ip);

    public int update(Server server);

    public int updateName(Server server);

    public Server findupdate(String ip,Long id);
    public Server find(String userName);
    public int delete(Server server);

    public Server detail(Server server);

    //查询state
    public List<Server> selectState();
}
