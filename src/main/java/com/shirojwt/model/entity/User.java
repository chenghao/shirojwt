package com.shirojwt.model.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * User
 */
@TableName("user")
public class User implements Serializable {
    private static final long serialVersionUID = 3342723124953988435L;

    /**
     * ID
     */
    @TableId
    private Integer id;

    /**
     * 帐号
     */
    private String account;

    /**
     * 密码
     */
    private String password;

    /**
     * 昵称
     */
    private String username;

    /**
     * 注册时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date regTime;

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
     * 获取帐号
     */
    public String getAccount() {
        return account;
    }

    /**
     * 设置帐号
     */
    public void setAccount(String account) {
        this.account = account;
    }

    /**
     * 获取密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置密码
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取昵称
     */
    public String getUsername() {
        return username;
    }

    /**
     * 设置昵称
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 获取注册时间
     */
    public Date getRegTime() {
        return regTime;
    }

    /**
     * 设置注册时间
     */
    public void setRegTime(Date regTime) {
        this.regTime = regTime;
    }
}