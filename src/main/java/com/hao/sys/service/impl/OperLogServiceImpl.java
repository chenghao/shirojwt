package com.hao.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hao.sys.dao.OperLogMapper;
import com.hao.sys.model.OperLogDto;
import com.hao.sys.service.OperLogService;
import org.springframework.stereotype.Service;

@Service
public class OperLogServiceImpl extends ServiceImpl<OperLogMapper, OperLogDto> implements OperLogService {

}
