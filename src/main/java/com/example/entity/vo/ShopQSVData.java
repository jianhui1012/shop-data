package com.example.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Desc:
 * Company: lifang
 * Copyright: Copyright (c) 2023
 *
 * @author djh
 * @since 2023/04/10 13/58
 */
@Data
public class ShopQSVData {

    @ApiModelProperty(value = "卷烟销售汇总数据")
    private QSVData jyQSVData;

    @ApiModelProperty(value = "非烟销售汇总数据")
    private QSVData fyQSVData;

}
