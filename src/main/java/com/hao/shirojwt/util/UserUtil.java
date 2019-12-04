package com.hao.shirojwt.util;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hao.sys.dao.UserMapper;
import com.hao.shirojwt.exception.BDException;
import com.hao.sys.model.UserDto;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 获取当前登录用户工具类
 */
@Component
public class UserUtil {
    @Autowired
    private UserMapper userMapper;


    /**
     * 获取当前登录用户
     * @param
     */
    public UserDto getUser() {
        String account = getAccount();
        UserDto userDto = new UserDto();
        userDto.setAccount(account);
        userDto = userMapper.selectOne(new QueryWrapper<>(userDto));
        // 用户是否存在
        if (userDto == null) {
            throw new BDException("该帐号不存在(The account does not exist.)");
        }
        return userDto;
    }

    /**
     * 获取当前登录用户Id
     * @param
     */
    public Integer getUserId() {
        return getUser().getId();
    }

    /**
     * 获取当前登录用户Token
     * @param
     */
    public String getToken() {
        return SecurityUtils.getSubject().getPrincipal().toString();
    }

    /**
     * 获取当前登录用户Account
     * @param
     */
    public String getAccount() {
        String token = getToken();
        // 解密获得Account
        String account = JwtUtil.getClaim(token, Constant.ACCOUNT);
        return account;
    }
}
