package cn.gok.controller;

import cn.gok.entity.Result;
import cn.gok.entity.Server;
import cn.gok.service.ServerService;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api/servers")
public class ServerController {
    @Autowired
    private ServerService serverService;
    /**
     * 列表/查询
     * @param json
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public Result list(@RequestBody JSONObject json){
        String searchKey = json.getString("searchKey");
        //给他默认的页码以及默认的每页的数目
        Integer pageNum = json.getInteger("pageNum")==null?1:json.getInteger("pageNum");
        Integer pageSize = json.getInteger("pageSize")==null?5:json.getInteger("pageSize");
        PageInfo<Server> list = serverService.list(searchKey,pageNum,pageSize);

        return Result.success(list);
    }

    /**
     * 新增
     * @param server
     * @return
     */
    @ResponseBody
    @RequestMapping("/add")
    public Result save(@RequestBody Server server){
        Result result=null;
           int check = serverService.save(server);
            if(check>0){
                result=Result.error("保存失败");
            }else {
                result=Result.success(null);
            }
        return result;
    }

    /**
     * 编辑
     * @param server
     * @return
     */
    @ResponseBody
    @RequestMapping("/update")
    public Result update(@RequestBody Server server){
        Result result=null;
           int check= serverService.update(server);
           if(check>0){
               result=Result.error("保存失败");
           }else {
               result=Result.success(null);
           }

        return result;
    }

    /**
     * 删除
     * @param server
     * @return
     */
    @ResponseBody
    @RequestMapping("/del")
    public Result delete(@RequestBody Server server){
        Result result=null;
       int check= serverService.delete(server);
       if(check>0){
           result=Result.error("删除失败");
       }else {
           result= Result.success(null);
       }
        return  result;
    }

    /**
     * 详情
     * @param server
     * @return
     */
    @ResponseBody
    @RequestMapping("/detail")
    public Result detail(@RequestBody Server server){

        return Result.success(serverService.detail(server));
    }
}
