package com.example.service;

import com.example.entity.Shop;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mapper.ShopMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ShopService extends ServiceImpl<ShopMapper, Shop> {

    @Resource
    private ShopMapper shopMapper;

}
