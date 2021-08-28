package cn.gok.mapper;

import cn.gok.entity.Server;

import java.util.List;

public interface ServerMapper {
    //列表查询
    public List<Server> list(String searchKey);

    //新增
    public int save(Server server);

    //编辑
    public int update(Server server);

    //删除
    public int delete(Server server);

    //详情
    public Server detail(Server server);
}
