package com.example.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.SaleData;
import com.example.mapper.SaleDataMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SaleDataService extends ServiceImpl<SaleDataMapper, SaleData> {

    @Resource
    private SaleDataMapper saleDataMapper;

    public List<SaleData> selectByMyWrapper(String shopName) {
        return saleDataMapper.selectByMyWrapper(shopName);
    }

}
