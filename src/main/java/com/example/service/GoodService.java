package com.example.service;

import com.example.entity.Good;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mapper.GoodMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class GoodService extends ServiceImpl<GoodMapper, Good> {

    @Resource
    private GoodMapper goodMapper;

}
