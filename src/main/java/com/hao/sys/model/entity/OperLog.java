package com.hao.sys.model.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

//@TableName("sys_oper_log")
@Data
public class OperLog implements Serializable {

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
    private Date operTime;

}
