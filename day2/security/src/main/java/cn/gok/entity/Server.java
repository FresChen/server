package cn.gok.entity;

import cn.hutool.core.date.DateTime;
import lombok.Data;

@Data
public class Server {
    private Long id;
    private String name;
    private String ip;
    private String state;
    private String mark;
    private String createTime;
    private DateTime time;
    private String user;
}
