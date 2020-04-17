package com.hao.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hao.sys.model.RoleDto;
import com.hao.sys.model.UserDto;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleMapper extends BaseMapper<RoleDto> {

    List<RoleDto> findRoleByUser(UserDto userDto);
}
