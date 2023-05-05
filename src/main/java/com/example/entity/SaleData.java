package com.example.entity;

import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;


@Data
@TableName("trs_sal_saledtl_jh_pa")
public class SaleData extends Model<SaleData> {

    private String saleDtlUuid;

    private String billCode;

    private String bizMonth;

    private Integer sumQuantityJy;

    private Integer sumAmountJy;

    private String sumCostAmountJy;

    private Integer sumQuantityFy;

    private String sumAmountFy;

    private String sumCostAmountFy;

    private String saleTime;

    private Integer isTobacco;

    private Integer quantity;

    private String amount;

    private String costAmount;

    private Long customerCode;

    private String customerName;

    private String custTypeName;

    private String goodsName;

    private String unitName;


}