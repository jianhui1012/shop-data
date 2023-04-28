package com.example.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Desc:
 * Company: lifang
 * Copyright: Copyright (c) 2023
 *
 * @author djh
 * @since 2023/04/28 16/56
 */
@Data
public class CombinationBean {

    @ApiModelProperty(value = "卷烟名称")
    private String jyGoodName;
    @ApiModelProperty(value = "非烟名称1")
    private String fyGoodName1;
    @ApiModelProperty(value = "非烟名称2")
    private String fyGoodName2;
    @ApiModelProperty(value = "非烟名称3")
    private String fyGoodName3;
}
