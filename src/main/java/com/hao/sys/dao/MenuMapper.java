package com.hao.sys.dao;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hao.sys.model.MenuDto;
import com.hao.sys.model.RoleDto;

import java.util.List;

@DS("master")
public interface MenuMapper extends BaseMapper<MenuDto> {

    List<MenuDto> findMenuByRole(RoleDto roleDto);
}
