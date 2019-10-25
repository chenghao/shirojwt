package com.hao.sys.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.hao.sys.model.PermissionDto;
import com.hao.sys.model.RoleDto;

import java.util.List;

public interface PermissionMapper extends BaseMapper<PermissionDto> {

    List<PermissionDto> findPermissionByRole(RoleDto roleDto);

}
