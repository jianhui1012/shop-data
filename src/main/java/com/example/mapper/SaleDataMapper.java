package com.example.mapper;

import com.example.entity.SaleData;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.vo.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SaleDataMapper extends BaseMapper<SaleData> {

    QuantitySalesVolume selectJyQuantitySalesVolume(@Param("type") int type, @Param("shopName") String shopName,
                                                        @Param("time") String time);

    QuantitySalesVolume selectJyAvgQuantitySalesVolume(@Param("shopName") String shopName,
                                                  @Param("time") String time);

    QuantitySalesVolume selectFyQuantitySalesVolume(@Param("type") int type, @Param("shopName") String shopName,
                                                    @Param("time") String time);

    QuantitySalesVolume selectFyAvgQuantitySalesVolume(@Param("shopName") String shopName,
                                                       @Param("time") String time);

    NextMonthAmount selectNextMonthAmount(@Param("shopName") String shopName,
                                          @Param("time") String time);

    Profit selectProfit(@Param("shopName") String shopName,
                        @Param("time") String time);

    List<ShopMonthData> selectShopMonthData(@Param("shopName") String shopName);


    List<PurchaseReminder> selectFyZFPurchaseReminder(@Param("shopName") String shopName, @Param("time") String time);

    List<PurchaseReminder> selectFyJFPurchaseReminder(@Param("shopName") String shopName, @Param("time") String time);

    List<SaleSuggestion> selectSaleSuggestion(@Param("shopName") String shopName, @Param("type") int type);
}
