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
 * @since 2023/04/10 11/17
 */
@Data
public class QSVData {

    @ApiModelProperty(value = "查询店铺卷烟/非烟当月/今年以来/去年同期/平均同期数量、成本、销售额")
    private List<QuantitySalesVolume> qsvList;

    @ApiModelProperty(value = "下个月卷烟/非烟进货资金")
    private double nextMonthAmount;

    @ApiModelProperty(value = "预计卷烟/非烟产生的利润")
    private double profit;
}
