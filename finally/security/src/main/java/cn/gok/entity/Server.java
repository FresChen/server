package cn.gok.entity;

import cn.hutool.core.date.DateTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
public class Server {
    private Long id;
    private String name;
    private String ip;
    private String state;
    private String mark;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private String createTime;
    private DateTime time;
    private String user;
    private String oldname;
    private Integer count;
    //判断state状态 1为已处理 0为未处理
    private String state1;
}
