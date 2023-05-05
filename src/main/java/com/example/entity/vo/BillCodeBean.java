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
public class BillCodeBean implements Comparable<BillCodeBean>{
    private String billCode;
    private String name;
    //1卷烟 0非烟
    private int IsTobacco;
    @Override
    public int compareTo(BillCodeBean codeBean) {
        return codeBean.getIsTobacco() - this.getIsTobacco();
    }
}
