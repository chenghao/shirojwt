package com.hao.sys.model.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * IP白名单
 */
@TableName("sys_whitelist")
@Data
public class Whitelist implements Serializable {

    @TableId
    private Integer id; //ID
    private String ip; //IP
    private String remark; //备注

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime; //创建时间

}