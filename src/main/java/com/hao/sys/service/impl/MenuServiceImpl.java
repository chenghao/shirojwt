package com.hao.sys.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hao.sys.dao.MenuMapper;
import com.hao.sys.model.MenuDto;
import com.hao.sys.service.MenuService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, MenuDto> implements MenuService {

    @Override
    public List<MenuDto> getSelfMenu() {
        return null;
    }
}
