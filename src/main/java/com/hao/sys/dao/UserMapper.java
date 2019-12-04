package com.hao.sys.dao;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hao.sys.model.UserDto;

@DS("master")
public interface UserMapper extends BaseMapper<UserDto> {

}
