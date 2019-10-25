package com.hao.sys.model.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * Role
 */
@TableName("role")
public class Role implements Serializable {
    private static final long serialVersionUID = 6382925944937625109L;

    /**
     * ID
     */
    @TableId
    private Integer id;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 获取ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取角色名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置角色名称
     */
    public void setName(String name) {
        this.name = name;
    }
}