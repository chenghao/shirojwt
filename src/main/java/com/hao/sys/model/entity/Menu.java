package com.hao.sys.model.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 菜单
 */
@TableName("sys_menu")
@Data
public class Menu implements Serializable {

    @TableId
    private Integer id;
    private String name; //菜单名称
    private Integer pid; //父id
    private String url; //url
    private String template; //模版
    private String controller; //控制器
    private float sort; //排序
    private String authority; //对应权限
    private int type; //类型（1目录，2菜单，3按钮）
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

}