package com.example.mapper;

import com.example.entity.SaleData;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.vo.NextMonthAmount;
import com.example.entity.vo.PurchaseReminder;
import com.example.entity.vo.QuantitySalesVolume;
import com.example.entity.vo.ShopMonthData;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SaleDataMapper extends BaseMapper<SaleData> {

    QuantitySalesVolume selectQuantitySalesVolume(@Param("type") int type, @Param("shopName") String shopName,
                                                        @Param("time") String time);

    QuantitySalesVolume selectAvgQuantitySalesVolume(@Param("shopName") String shopName,
                                                  @Param("time") String time);

    NextMonthAmount selectNextMonthAmount(@Param("shopName") String shopName,
                                          @Param("time") String time);

    double selectProfit(@Param("shopName") String shopName,
                                          @Param("time") String time);

    List<ShopMonthData> selectShopMonthData(@Param("shopName") String shopName);


    List<PurchaseReminder> selectFyZFPurchaseReminder(@Param("shopName") String shopName, @Param("time") String time);

    List<PurchaseReminder> selectFyJFPurchaseReminder(@Param("shopName") String shopName, @Param("time") String time);
}
