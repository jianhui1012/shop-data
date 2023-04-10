package com.example.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Desc:
 * Company: lifang
 * Copyright: Copyright (c) 2023
 *
 * @author djh
 * @since 2023/04/10 11/01
 */
@Data
public class Profit {

    @ApiModelProperty(value = "查询店铺卷烟预计产生的利润")
    private double jyProfit;

    @ApiModelProperty(value = "查询店铺非烟预计产生的利润")
    private double fyProfit;
}
