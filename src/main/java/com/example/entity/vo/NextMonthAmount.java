package com.example.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Desc:
 * Company: lifang
 * Copyright: Copyright (c) 2023
 *
 * @author djh
 * @since 2023/03/16 10/11
 */
@Data
public class NextMonthAmount {

    @ApiModelProperty(value = "下个月卷烟的进货资金")
    private Double nextMonthJyAmount;
    @ApiModelProperty(value = "下个月非烟的进货资金")
    private Double nextMonthFyAmount;
    @ApiModelProperty(value = "下个月进货资金")
    private Double nextMonthAmount;
}
