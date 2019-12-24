package com.hao.sys.model.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 配置
 */
@TableName("sys_config")
@Data
public class Config implements Serializable {

    @TableId
    private Integer id; //ID
    private String code; //KEY
    private String value; //值
    private String remark; //备注
    private Integer delState; //删除状态（0未删除，1已删除）

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime; //创建时间

}