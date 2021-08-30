package cn.gok.entity;

import cn.hutool.core.date.DateTime;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
public class Loophole {
    private Long id;
    private String ip;
    private String port;
    private String content;
    private String type;
    private String state;
   // private String createTime;
//    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String createTime;
    private DateTime time;
    private String User;
    private String solveCont;
    private Long UserID;
    private Integer count;

}
