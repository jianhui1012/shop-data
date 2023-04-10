package com.example.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.SaleData;
import com.example.entity.vo.*;
import com.example.mapper.SaleDataMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SaleDataService extends ServiceImpl<SaleDataMapper, SaleData> {

    @Resource
    private SaleDataMapper saleDataMapper;

    public QuantitySalesVolume selectJyQuantitySalesVolume(int type, String shopName, String time) {
        return saleDataMapper.selectJyQuantitySalesVolume(type,shopName,time);
    }

    public QuantitySalesVolume selectJyAvgQuantitySalesVolume(String shopName, String time) {
        return saleDataMapper.selectJyAvgQuantitySalesVolume(shopName,time);
    }

    public QuantitySalesVolume selectFyQuantitySalesVolume(int type, String shopName, String time) {
        return saleDataMapper.selectFyQuantitySalesVolume(type,shopName,time);
    }

    public QuantitySalesVolume selectFyAvgQuantitySalesVolume(String shopName, String time) {
        return saleDataMapper.selectFyAvgQuantitySalesVolume(shopName,time);
    }

    public NextMonthAmount selectNextMonthAmount(String shopName, String time) {
        return saleDataMapper.selectNextMonthAmount(shopName,time);
    }

    public Profit selectProfit(String shopName, String time) {
        return saleDataMapper.selectProfit(shopName,time);
    }

    public  List<ShopMonthData> selectShopMonthData(String shopName) {
        return saleDataMapper.selectShopMonthData(shopName);
    }

    public List<PurchaseReminder> selectFyZFPurchaseReminder(String shopName, String time) {
        return saleDataMapper.selectFyZFPurchaseReminder(shopName,time);
    }


    public List<PurchaseReminder> selectFyJFPurchaseReminder(String shopName, String time) {
        return saleDataMapper.selectFyJFPurchaseReminder(shopName,time);
    }


}
