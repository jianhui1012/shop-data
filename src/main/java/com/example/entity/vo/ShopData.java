package com.example.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * Desc:
 * Company: lifang
 * Copyright: Copyright (c) 2023
 *
 * @author djh
 * @since 2023/03/24 14/22
 */
@Data
public class ShopData {

    @ApiModelProperty(value = "查询店铺当月的数量、成本、销售额")
    private QuantitySalesVolume dyQuantitySalesVolume;
    @ApiModelProperty(value = "查询店铺今年以来的数量、成本、销售额")
    private QuantitySalesVolume jNQuantitySalesVolume;
    @ApiModelProperty(value = "查询店铺去年同期的数量、成本、销售额")
    private QuantitySalesVolume qNQuantitySalesVolume;

    @ApiModelProperty(value = "查询店铺平均同期的数量、成本、销售额")
    private QuantitySalesVolume avgQuantitySalesVolume;

    @ApiModelProperty(value = "查询店铺下个月的进货资金")
    private NextMonthAmount nextMonthAmount;

    @ApiModelProperty(value = "查询店铺预计产生的利润")
    private double profit;

    @ApiModelProperty(value = "查询店铺按月份找出卷烟销售数量、成本、毛利（卷烟量本利）")
    private List<ShopMonthData> shopMonthDataList;

    @ApiModelProperty(value = "查询店铺非烟进货提醒表的增幅")
    private List<PurchaseReminder> zfPurchaseReminder;

    @ApiModelProperty(value = "查询店铺非烟进货提醒表的降幅")
    private List<PurchaseReminder> jfPurchaseReminder;
}
