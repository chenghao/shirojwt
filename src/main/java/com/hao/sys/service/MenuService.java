package com.hao.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hao.sys.model.MenuDto;

import java.util.List;

public interface MenuService extends IService<MenuDto> {

    List<MenuDto> getSelfMenu();
}
