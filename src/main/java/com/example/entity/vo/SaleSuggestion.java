package com.example.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Desc:
 * Company: lifang
 * Copyright: Copyright (c) 2023
 *
 * @author djh
 * @since 2023/04/21 16/48
 */
@Data
public class SaleSuggestion {

    @ApiModelProperty(value = "商品名称")
    private String name;
    @ApiModelProperty(value = "策略")
    private String category;
    @ApiModelProperty(value = "数量")
    private String quantity;
    @ApiModelProperty(value = "利润")
    private String profit;
}
