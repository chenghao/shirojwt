package com.hao.sys.model.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 操作日志
 */
@TableName("sys_oper_log")
@Data
public class OperLog implements Serializable {

    @TableId
    private Long id;

    private String title; //'模块标题'
    private String businessType; // '业务类型（BusinessType）'
    private String method; // '方法名称'
    private Integer operId; // '操作人员ID'
    private String operUrl; // '请求URL'
    private String operIp; // '主机地址'
    private String operLocation; // '操作地点'
    private String operParam; // '请求参数'
    private String status; // '操作状态（SUCCESS正常， FAIL异常）'
    private String errorMsg; // '错误消息'

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;  //创建时间

}
