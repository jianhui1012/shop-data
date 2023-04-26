package com.example.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

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

    @ApiModelProperty(value = "销售汇总数据")
    private ShopQSVData shopQSVData;

    @ApiModelProperty(value = "查询店铺按月份找出卷烟销售数量、成本、毛利（卷烟量本利）")
    private List<ShopMonthData> shopMonthDataList;

    @ApiModelProperty(value = "查询店铺非烟进货提醒表的增幅")
    private List<PurchaseReminder> zfPurchaseReminder;

    @ApiModelProperty(value = "查询店铺非烟进货提醒表的降幅")
    private List<PurchaseReminder> jfPurchaseReminder;

    private List<SaleSuggestionResp> saleJySuggestions;

    private List<SaleSuggestionResp> saleFySuggestions;
}
