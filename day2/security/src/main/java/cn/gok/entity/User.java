package cn.gok.entity;

import cn.hutool.core.date.DateTime;
import lombok.Data;

@Data
public class User {
 private Long id;
 private String name;
 private String userName;
 private String password;
 private String sex;
 private String createTime;
 private String phone;
 private DateTime time;
}
