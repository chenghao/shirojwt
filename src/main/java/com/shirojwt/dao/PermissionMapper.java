package com.shirojwt.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.shirojwt.model.PermissionDto;
import com.shirojwt.model.RoleDto;

import java.util.List;

public interface PermissionMapper extends BaseMapper<PermissionDto> {

    List<PermissionDto> findPermissionByRole(RoleDto roleDto);

}
