package cn.gok.service.impl;

import cn.gok.entity.Loophole;
import cn.gok.mapper.LoopholeMapper;
import cn.gok.service.LoopholeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
@Service
public class LoopholeServiceImpl implements LoopholeService {
    @Autowired(required = false)
    private LoopholeMapper loopholeMapper;
    @Override
    public PageInfo<Loophole> list(String searchKey, Integer pageNum, Integer pageSize) {
        //分页
        PageHelper.startPage(pageNum,pageSize);
        List<Loophole> list=loopholeMapper.list(searchKey);
        PageInfo<Loophole> pageInfo=new PageInfo<>(list);
        return  pageInfo;
    }

    @Override
    public int save(Loophole loophole) {
        return loopholeMapper.save(loophole);
    }

    @Override
    public Loophole findloophole(String ip) {
        return loopholeMapper.findloophole(ip);
    }

    @Override
    public int update(Loophole loophole) {
        return loopholeMapper.update(loophole);
    }

    @Override
    public int delete(Loophole loophole) {
        return loopholeMapper.delete(loophole);
    }

    @Override
    public Loophole detail(Loophole loophole) {
        return loopholeMapper.detail(loophole);
    }

    @Override
    public int updateUser(Loophole loophole) {
        return loopholeMapper.updateUser(loophole);
    }

    @Override
    public List<Loophole> getloopholes() {
        return loopholeMapper.getloopholes();
    }

    @Override
    public int insert(Loophole loophole) {
        return loopholeMapper.insert(loophole);
    }


    @Override
    public Loophole findById(Long id) {
        return loopholeMapper.findById(id);
    }

    //导入
    @Override
    public boolean batchImport(String fileName, MultipartFile file) throws Exception {
        boolean notnull = false;
          List<Loophole> loopholesList = new ArrayList<>();
        if(!fileName.matches("^.+\\.(?i)(xls)$")){ //
            throw new Exception("上传文件格式不正确");
        }
        boolean isExcel2007 = true;
        if(fileName.matches("^.+\\.(?i)(xlsx)$")){
            isExcel2007 = false;
        }
        InputStream is = file.getInputStream();
        Workbook wb = null;
        if (isExcel2007) {
            wb = new HSSFWorkbook(is);
        }else{
            wb = new XSSFWorkbook(is);
        }
        Sheet sheet = wb.getSheetAt(0);
        if(sheet != null){
            notnull = true;
        }
        Loophole loophole= null;
        for(int r=1;r<=sheet.getLastRowNum();r++){ //r = 1 表示从第二行开始循环 如果你的第二行开始是数据
            Row row = sheet.getRow(r);//通过sheet表单对象得到行对象
            if(row == null){
                continue;
            }
            //sheet.getLastRowNum() 的值是 10，所以Excel表中的数据至少是10条；不然报错 NullPointerException
            loophole = new Loophole();
            row.getCell(0).setCellType(CellType.STRING);//将每一行第一个单元格设置为字符串类型
            row.getCell(1).setCellType(CellType.STRING);
            row.getCell(2).setCellType(CellType.STRING);
            row.getCell(3).setCellType(CellType.STRING);
//            row.getCell(4).setCellType(CellType.STRING);
            String lip =row.getCell(0).getStringCellValue();//得到每一行第一个单元格的值
            if(lip == null || lip.isEmpty()){
                throw new Exception("导入失败(第\"+(r+1)+\"行，服务器IP未填写)");
            }
            String port = row.getCell(1).getStringCellValue();//得到每一行的第二个单元格的值
            if(port == null || port.isEmpty()){
                throw new Exception("导入失败(第\"+(r+1)+\"行，端口号未填写)");
            }
            String content = row.getCell(2).getStringCellValue();//得到每一行的第三个单元格的值
            if(content == null || content.isEmpty()){
                throw new Exception("导入失败(第\"+(r+1)+\"行，内容未填写)");
            }

            String type = row.getCell(3).getStringCellValue();//得到每一行的第四个单元格的值
            if(type == null || type.isEmpty()){
                throw new Exception("导入失败(第\"+(r+1)+\"行，类型未填写)");
            }
//            String state = row.getCell(4).getStringCellValue();//得到每一行的第五个单元格的值
//            if(state == null || state.isEmpty()){
//                throw new Exception("导入失败(第\"+(r+1)+\"行，服务器状态未填写)");
//            }


            //完整的循环一次 就组成了一个对象
            loophole.setIp(lip);
            loophole.setPort(port);
            loophole.setContent(content);
            loophole.setType(type);
//            loophole.setState(state);

            loopholesList.add(loophole);
        }
        for(Loophole loopholeResord : loopholesList){
            Long id = loopholeResord.getId();
            Loophole loophole1 = loopholeMapper.findById(id);
            if(loophole1 == null){
                loopholeMapper.insert(loopholeResord);
                System.out.println("==>插入："+loopholeResord);
            }else{
                loopholeMapper.update(loopholeResord);
                System.out.println("==>更新："+loopholeResord);
            }
        }
        return notnull;
    }

    //导出
    @Override
    public HSSFWorkbook exportToExcel(List<Loophole> loopholes) { //将集合中的数据存储到Execl工作簿中
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("loophole_info");

        HSSFRow row = null;
        row = sheet.createRow(0);//创建第一行
        row.setHeight((short) 800);// 设置行高
        HSSFCell c00 = row.createCell(0); //创建第一个单元格
        c00.setCellValue("漏洞管理列表");//设置单元格内容

//设置标题样式
        c00.setCellStyle(ExcelImportUtils.createTitleCellStyle(wb));

//合并单元格(firstRow:起始行,lastRow:结束行,firstCol:起始列,lastCol:结束列)
        CellRangeAddress rowRegion = new CellRangeAddress(0,0,0,4);
        sheet.addMergedRegion(rowRegion);

        //创建表头行，并设置样式
        row = sheet.createRow(1); //创建第二行
        row.setHeight((short)500);//设置行高
        String[] row_head = {"服务器IP","端口号","内容","类型","服务器状态"};
        for(int i=0;i<row_head.length;i++){ //创建表头
            HSSFCell tempCell = row.createCell(i);
            tempCell.setCellValue(row_head[i]); //设置单元格内容

//设置表头样式
           tempCell.setCellStyle(ExcelImportUtils.createHeadCellStyle(wb));
        }

        //定义表格内容(每行数据)

//集合(loopholes)中有多少个元素就生成多少行
        for(int i=0;i<loopholes.size();i++) {

            row = sheet.createRow(i + 2);
            Loophole loophole = loopholes.get(i);
            for (int j = 0; j < 5; j++) { //每行有4列
                HSSFCell tempCell = row.createCell(j); //设置单元格内容
                //设置内容样式
                tempCell.setCellStyle(ExcelImportUtils.createContentCellStyle(wb));

                if (j == 0) {
                    tempCell.setCellValue(loophole.getIp());
                } else if (j == 1) {
                    tempCell.setCellValue(loophole.getPort());
                } else if (j == 2) {
                    tempCell.setCellValue(loophole.getContent());
                } else if (j == 3) {
                    tempCell.setCellValue(loophole.getType());
                } else if (j == 4) {
                    tempCell.setCellValue(loophole.getState());
                }
            }
        }
        // sheet.setDefaultRowHeight((short)(16.5*20)); 设置默认行高
        //列宽自适应
        for(int i=0;i<5;i++){
            sheet.autoSizeColumn(i);
        }
        return wb;
    }

    @Override
    public int updateState(Loophole loopHole) {
        return loopholeMapper.updateState(loopHole);
    }

    @Override
    public int finishState(Loophole loopHole) {
        return loopholeMapper.finishState(loopHole);
    }

    @Override
    public List<Loophole> selectState() {
        return loopholeMapper.selectState();
    }
}
