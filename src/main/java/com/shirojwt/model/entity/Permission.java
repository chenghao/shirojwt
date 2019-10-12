package com.shirojwt.model.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * Permission
 */
@TableName("permission")
public class Permission implements Serializable {
    private static final long serialVersionUID = -8834983208597107688L;

    /**
     * ID
     */
    @TableId
    private Integer id;

    /**
     * 资源名称
     */
    private String name;

    /**
     * 权限代码字符串
     */
    private String perCode;

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
     * 获取资源名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置资源名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取权限代码字符串
     */
    public String getPerCode() {
        return perCode;
    }

    /**
     * 设置权限代码字符串
     */
    public void setPerCode(String perCode) {
        this.perCode = perCode;
    }
}