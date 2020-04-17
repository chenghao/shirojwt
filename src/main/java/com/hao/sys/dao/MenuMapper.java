package com.hao.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hao.sys.model.MenuDto;
import com.hao.sys.model.RoleDto;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuMapper extends BaseMapper<MenuDto> {

    List<MenuDto> findMenuByRole(RoleDto roleDto);
}
