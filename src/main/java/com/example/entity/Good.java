package com.example.entity;

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
    private String name;

    /**
      * 物品类型 
      */
    private Integer type;

    /**
      * 物品图标 
      */
    private String goodUrl;

    /**
      * 物品编号 
      */
    private String goodId;

    /***
     * 店铺id
     */
    private String shopId;

}