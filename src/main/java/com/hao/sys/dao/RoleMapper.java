package com.hao.sys.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.hao.sys.model.RoleDto;
import com.hao.sys.model.UserDto;

import java.util.List;

public interface RoleMapper extends BaseMapper<RoleDto> {

    List<RoleDto> findRoleByUser(UserDto userDto);
}
