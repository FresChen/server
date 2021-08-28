package cn.gok.service;

import cn.gok.entity.Server;
import com.github.pagehelper.PageInfo;

public interface ServerService {
    public PageInfo<Server> list(String searchKey, Integer pageNum, Integer pageSize);


    public int save(Server server);

    public int update(Server server);

    public int delete(Server server);

    public Server detail(Server server);
}
