package com.shirojwt.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.shirojwt.model.RoleDto;
import com.shirojwt.model.UserDto;

import java.util.List;

public interface RoleMapper extends BaseMapper<RoleDto> {

    List<RoleDto> findRoleByUser(UserDto userDto);
}
