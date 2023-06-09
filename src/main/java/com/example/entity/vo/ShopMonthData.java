package com.example.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Desc:
 * Company: lifang
 * Copyright: Copyright (c) 2023
 *
 * @author djh
 * @since 2023/03/16 10/35
 */
@Data
public class ShopMonthData {

    @ApiModelProperty(value = "月份")
    private String shopMonth;
    @ApiModelProperty(value = "卷烟/非烟合计")
    private int amount;
    @ApiModelProperty(value = "卷烟/非烟合计成本")
    private double cost;
    @ApiModelProperty(value = "卷烟/非烟合计毛利")
    private double grossProfit;
}
