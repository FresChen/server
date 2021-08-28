package cn.gok.service.impl;

import cn.gok.entity.Server;
import cn.gok.mapper.ServerMapper;
import cn.gok.service.ServerService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ServerServiceImpl implements ServerService {

    @Autowired(required = false)
    private ServerMapper serverMapper;
    @Override
    public PageInfo<Server> list(String searchKey, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Server> list=serverMapper.list(searchKey);
        PageInfo<Server> pageInfo=new PageInfo<>(list);
        return pageInfo;
    }


    @Override
    public int save(Server server) {
        serverMapper.save(server);
        return 0;
    }

    @Override
    public int update(Server server) {
        serverMapper.update(server);
        return 0;
    }

    @Override
    public int delete(Server server) {
        serverMapper.delete(server);
        return 0;
    }

    @Override
    public Server detail(Server server) {
        return serverMapper.detail(server);
    }
}
