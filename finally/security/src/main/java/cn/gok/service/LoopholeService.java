package cn.gok.service;

import cn.gok.entity.Loophole;
import com.github.pagehelper.PageInfo;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface LoopholeService {
    //列表查询
    public PageInfo<Loophole> list(String searchKey, Integer pageNum, Integer pageSize);
    //增
    public int save(Loophole loophole);
    //查询
    public Loophole findloophole(String ip) ;
    //更新
    public int update(Loophole loophole);
    //删
    public int delete(Loophole loophole);
    //详情
    public Loophole detail(Loophole loophole);
    //发布任务
    public  int updateUser(Loophole loophole);

    public List<Loophole> getloopholes();//查询所有
    public int insert(Loophole loophole);
    public Loophole findById(Long id);
    public boolean batchImport(String fileName, MultipartFile file) throws Exception;//导入Excel
    public HSSFWorkbook exportToExcel(List<Loophole> loopholes);//导出到Excel

    //更新state
    public int updateState(Loophole loopHole);

    //更新state 漏洞处理
    public int finishState(Loophole loopHole);

    //查询state
    public List<Loophole> selectState();
}
