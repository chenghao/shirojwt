package com.hao.sys.model.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 字典
 */
@TableName("sys_dict")
@Data
public class Dict implements Serializable {

    @TableId
    private Integer id; //ID
    private Integer pid; //父id
    private String name; //字典名称
    private String value; //字典值
    private float sort; //排序
    private Integer delState; //删除状态（0未删除，1已删除）

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime; //创建时间

}