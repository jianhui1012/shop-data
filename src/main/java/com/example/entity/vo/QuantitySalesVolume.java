package com.example.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Desc:
 * Company: lifang
 * Copyright: Copyright (c) 2023
 *
 * @author djh
 * @since 2023/03/16 09/10
 */
@Data
public class QuantitySalesVolume {

    @ApiModelProperty(value = "数量")
    private int quantity;
    @ApiModelProperty(value = "成本")
    private double cost;
    @ApiModelProperty(value = "销售额")
    private int salesVolume;
}
