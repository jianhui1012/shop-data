package com.example.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Desc:
 * Company: lifang
 * Copyright: Copyright (c) 2023
 *
 * @author djh
 * @since 2023/03/16 11/22
 */
@Data
public class PurchaseReminder {

    @ApiModelProperty(value = "商品名称")
    private String goodName;
    private int sl4;
    private int sl5;
    @ApiModelProperty(value = "增幅数据")
    private String fd;

}
