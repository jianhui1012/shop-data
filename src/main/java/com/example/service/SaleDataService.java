package com.example.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.SaleData;
import com.example.entity.vo.NextMonthAmount;
import com.example.entity.vo.PurchaseReminder;
import com.example.entity.vo.QuantitySalesVolume;
import com.example.entity.vo.ShopMonthData;
import com.example.mapper.SaleDataMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SaleDataService extends ServiceImpl<SaleDataMapper, SaleData> {

    @Resource
    private SaleDataMapper saleDataMapper;

    public QuantitySalesVolume selectQuantitySalesVolume(int type, String shopName, String time) {
        return saleDataMapper.selectQuantitySalesVolume(type,shopName,time);
    }

    public QuantitySalesVolume selectAvgQuantitySalesVolume(String shopName, String time) {
        return saleDataMapper.selectAvgQuantitySalesVolume(shopName,time);
    }

    public NextMonthAmount selectNextMonthAmount(String shopName, String time) {
        return saleDataMapper.selectNextMonthAmount(shopName,time);
    }

    public double selectProfit(String shopName, String time) {
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
