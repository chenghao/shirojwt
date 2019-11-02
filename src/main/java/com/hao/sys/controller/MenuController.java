package com.hao.sys.controller;

import com.hao.shirojwt.annotation.Log;
import com.hao.shirojwt.annotation.enums.BusinessType;
import com.hao.shirojwt.util.PropertiesUtil;
import com.hao.shirojwt.util.R;
import com.hao.shirojwt.util.UserUtil;
import com.hao.sys.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 菜单
 */
@RestController
@RequestMapping("/sys/menu")
public class MenuController {

    //RefreshToken过期时间
    private String refreshTokenExpireTime = PropertiesUtil.getInstance().getProperty("refreshTokenExpireTime");

    @Autowired
    private UserUtil userUtil;

    @Autowired
    private MenuService menuService;

    /**
     * 获取自己的所有菜单（左边）
     * @return
     */
    @Log(title = "获取自己的所有菜单", businessType = BusinessType.QUERY)
    @GetMapping
    public R getAllMenuByUserId(){

        return R.ok();
    }

}