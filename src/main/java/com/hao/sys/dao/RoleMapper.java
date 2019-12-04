package com.hao.sys.dao;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hao.sys.model.RoleDto;
import com.hao.sys.model.UserDto;

import java.util.List;

@DS("master")
public interface RoleMapper extends BaseMapper<RoleDto> {

    List<RoleDto> findRoleByUser(UserDto userDto);
}
