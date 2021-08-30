package cn.gok.controller;

import cn.gok.entity.Loophole;
import cn.gok.entity.Result;
import cn.gok.service.LoopholeService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@CrossOrigin
@Controller
@RequestMapping("/api/loophole")
public class LoopholeController {

    @Autowired
    private LoopholeService loopholeService;

    @RequestMapping("/list")
    @ResponseBody
    public Result list(@RequestBody JSONObject json) {
        String searchKey = json.getString("searchKey");
        //给他默认的页码以及默认的每页的数目
        Integer pageNum = json.getInteger("pageNum") == null ? 1 : json.getInteger("pageNum");
        Integer pageSize = json.getInteger("pageSize") == null ? 5 : json.getInteger("pageSize");
        PageInfo<Loophole> list = loopholeService.list(searchKey, pageNum, pageSize);

        return Result.success(list);
    }

    @RequestMapping("/add")
    @ResponseBody
    public Result save(@RequestBody Loophole loophole) {
        Result result = null;

        Loophole loophole1 = loopholeService.findloophole(loophole.getIp());
        if (loophole1 != null) {
            int check = loopholeService.save(loophole);
            if (check > 0) {
                result = Result.success(null);
            } else {
                result = Result.error("保存失败");
            }
        } else {
            result = Result.error("ip不存在");
        }

        return result;
    }

    @RequestMapping("/update")
    @ResponseBody
    public Result update(@RequestBody Loophole loophole){
        Result result=null;
        Loophole loophole1=loopholeService.findloophole(loophole.getIp());
        if(loophole1!=null){
            int check=loopholeService.update(loophole);
            if(check>0){
                result=Result.success(null);
            }else {
                result= Result.error("保存失败");
            }
        }else {
            result=Result.error("ip不存在");
        }

        return result;
    }

    @RequestMapping("/del")
    @ResponseBody
    public Result delete(@RequestBody Loophole loophole) {
        Result result = null;
        int check = loopholeService.delete(loophole);
        if (check > 0) {
            result = Result.success(null);
        } else {
            result = Result.error("删除失败");
        }
        return result;
    }

    @RequestMapping("/detail")
    @ResponseBody
    public Result detail(@RequestBody Loophole loophole) {

        return Result.success(loopholeService.detail(loophole));
    }

    @RequestMapping("/updateuser")
    @ResponseBody
    public Result updateUser(@RequestBody Object object) {
        JSON json = (JSON) JSON.toJSON(object);
        Loophole loopHole = JSONObject.toJavaObject(json, Loophole.class);
        return Result.success(loopholeService.updateUser(loopHole));
    }


    @PostMapping("/import")
    @ResponseBody
    public boolean exImport(@RequestParam("file") MultipartFile file) {
        boolean a = false;
        String fileName = file.getOriginalFilename();
        System.out.println(fileName);
        try {
            a = loopholeService.batchImport(fileName, file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(a);
        return a;
    }

    @GetMapping("/export")
    @ResponseBody
    public void export(HttpServletResponse response) throws IOException {
        List<Loophole> loopholes = loopholeService.getloopholes();
        HSSFWorkbook wb = loopholeService.exportToExcel(loopholes);
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        OutputStream os = response.getOutputStream();
        response.setHeader("Content-disposition", "attachment;filename=loophole.xlsx"); //默认Excel名称
        wb.write(os);
        os.flush();
        os.close();
    }

    @RequestMapping("/updatestate")
    @ResponseBody
    public Result updateState(@RequestBody Loophole loopHole) {
        loopholeService.updateState(loopHole);
        return Result.success(null);
    }

    @RequestMapping("/finishstate")
    @ResponseBody
    public Result finishState(@RequestBody Loophole loopHole){
        loopholeService.finishState(loopHole);
        return  Result.success(null);

    }

    @RequestMapping("/selectstate")
    @ResponseBody
    public Result list(@RequestBody Loophole loopHole) {
        List<Loophole> list =  loopholeService.selectState();
        return Result.success(list);
    }

}
