package com.hao.sys.model.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户
 */
@TableName("sys_user")
@Data
public class User implements Serializable {
    private static final long serialVersionUID = 3342723124953988435L;

    @TableId
    private Integer id; //ID
    private String account; //帐号
    private String password; //密码
    private String username; //昵称
    private Integer delState; //删除状态（0未删除，1已删除）

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date regTime; //注册时间

}