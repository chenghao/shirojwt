package com.hao.sys.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hao.sys.dao.UserMapper;
import com.hao.sys.model.UserDto;
import com.hao.sys.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDto> implements UserService {

}
