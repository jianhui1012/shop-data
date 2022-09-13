package com.example.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;

import java.util.Date;


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
    @ApiModelProperty(value = "物品类型，借用类型和享用类型，默认借用类型",example = "0")
    private String type;

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

    /**
      * 店铺ID
      */
    @ApiModelProperty(value = "店铺id",example = "0")
    private Long shopId;

    /**
      * 创建时间
      */
    @ApiModelProperty(value = "创建时间",hidden = true,example = "0")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date  createTime;

    /**
      * 更新时间
      */
    @ApiModelProperty(value = "更新时间",hidden = true,example = "0")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date  updateTime;

    /**
      * 商品库存
      */
    @ApiModelProperty(value = "商品库存",example = "0")
    private Long stock;

    /**
      * 已借出数量
      */
    @ApiModelProperty(value = "已借出数量",example = "0")
    private Long borrowCount;

}