package com.hao.sys.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.hao.sys.model.MenuDto;
import com.hao.sys.model.RoleDto;

import java.util.List;

public interface MenuMapper extends BaseMapper<MenuDto> {

    List<MenuDto> findMenuByRole(RoleDto roleDto);
}
