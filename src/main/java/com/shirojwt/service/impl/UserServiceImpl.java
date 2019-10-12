package com.shirojwt.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.shirojwt.dao.UserMapper;
import com.shirojwt.model.UserDto;
import com.shirojwt.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDto> implements UserService {

}
