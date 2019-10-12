package com.shirojwt.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.shirojwt.model.entity.User;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

@TableName("user")
public class UserDto extends User {
    /**
     * 登录时间
     */
    @TableField(exist = false)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date loginTime;

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }
}
