package com.hao.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hao.sys.model.UserDto;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper extends BaseMapper<UserDto> {

}
