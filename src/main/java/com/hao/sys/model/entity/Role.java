package com.hao.sys.model.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 角色
 */
@TableName("sys_role")
@Data
public class Role implements Serializable {
    private static final long serialVersionUID = 6382925944937625109L;

    @TableId
    private Integer id; //ID
    private String code; //角色code
    private String name; //角色名称

}