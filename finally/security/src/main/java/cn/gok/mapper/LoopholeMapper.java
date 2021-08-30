package cn.gok.mapper;

import cn.gok.entity.Loophole;

import java.util.List;

public interface LoopholeMapper {
    //列表查询
    public List<Loophole> list(String searchKey);
    //增
    public int save(Loophole loophole);
    /**
     * 通过ip查找用户是否存在，用于用户添加
     * @param ip
     * @return 用户存在或null
     */
    public Loophole findloophole(String ip);
    //更新
    public int update(Loophole loophole);
    //删
    public int delete(Loophole loophole);
    //详情
    public Loophole detail(Loophole loophole);
    //发布任务
    public int updateUser(Loophole loopHole);

//导入导出
    public List<Loophole> getloopholes();//查询所有
    public Loophole findById(Long id);
    public int insert(Loophole loophole);

    public int updateState(Loophole loopHole);

    public int finishState(Loophole loopHole);

    //查询state
    public List<Loophole> selectState();
}
