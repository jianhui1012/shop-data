package com.example.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;


@Data
@TableName("t_goods")
public class Good extends Model<Good> {
    /**
      * 主键
      */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
      * 物品名称 
      */
    @ApiModelProperty(value = "物品名称")
    private String name;

    /**
      * 物品类型 
      */
    @ApiModelProperty(value = "物品类型")
    private Integer type;

    /**
      * 物品图标 
      */
    @ApiModelProperty(value = "物品图标")
    private String goodUrl;

    /**
      * 物品编号 
      */
    @ApiModelProperty(value = "物品编号")
    private String goodId;

    /***
     * 店铺id
     */
    @ApiModelProperty(value = "店铺id")
    private String shopId;

    @ApiModelProperty(value = "商品库存")
    private Long stock;

    @ApiModelProperty(value = "已借出数量")
    private Long borrowCount;
}