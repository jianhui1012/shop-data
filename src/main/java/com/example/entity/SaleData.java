package com.example.entity;

import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;


@Data
@TableName("trs_sal_saledtl_jh_2022")
public class SaleData extends Model<SaleData> {
    /**
      * 主键
      */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String saleDtlUuid;

    private String saleUuid;

    private String custUuid;

    private String billCode;

    private Integer isReturn;

    private String bizDate;

    private String bizMonth;

    private Integer detailCountJy;

    private String sumQuantityJy;

    private String sumBaseQuantityJy;

    private String sumBranchQuantityJy;

    private String sumDefaultQuantityJy;

    private String sumAmountJy;

    private String sumCostAmountJy;

    private String sumDiscountAmountJy;

    private Integer detailCountFy;

    private String sumQuantityFy;

    private String sumBaseQuantityFy;

    private String sumAmountFy;

    private String sumCostAmountFy;

    private String sumDiscountAmountFy;

    private String saleTime;

    private String auditTime;

    private String consumerUuid;

    private String mcardUuid;

    private String billStatus;

    private Integer isPayed;

    private String remark;

    private Integer seqno;

    private String goodsUuid;

    private Integer isTobacco;

    private String unitUuid;

    private String quantity;

    private String baseQuantity;

    private Integer branchQuantity;

    private String defaultQuantity;

    private String price;

    private String amount;

    private String costAmount;

    private String discountAmount;

    private Integer stockChanged;

    private Long customerCode;

    private String customerName;

    private Long custMasterTel;

    private String custTypeUuid;

    private String custTypeName;

    private String saleRouteUuid;

    private String saleRouteName;

    private String saleDepartUuid;

    private String saleDepartName;

    private String saleCountyUuid;

    private String saleCountyName;

    private String saleCorpUuid;

    private String saleCorpName;

    private String salerUuid;

    private String salerName;

    private String goodsCode;

    private String goodsName;

    private String unitName;

    private String manageUnitUuid;

    private Integer SYSCREATORUUID;

    private String SYSCREATEDATE;

    private String SYSUPDATORUUID;

    private String SYSUPDATEDATE;

    private String SYSOWNERUUID;

    private String SYSDEPTUUID;

    private String SYSCOUNTYUUID;

    private String SYSCOMPANYUUID;

}