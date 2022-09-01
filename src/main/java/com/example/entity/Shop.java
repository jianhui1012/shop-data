package com.example.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;


@Data
@TableName("t_shop")
public class Shop extends Model<Shop> {
    /**
      * 主键
      */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
      * 店名 
      */
    @ApiModelProperty(value = "店名")
    private String name;

    /**
      * 地址 
      */
    @ApiModelProperty(value = "地址")
    private String address;

    /**
      * 联系人 
      */
    @ApiModelProperty(value = "联系人")
    private String phonename;

    /**
      * 联系电话 
      */
    @ApiModelProperty(value = "联系电话")
    private String phone;

    /**
      * 经度 
      */
    @ApiModelProperty(value = "经度,后续扩展使用")
    private float longitude;

    /**
      * 纬度 
      */
    @ApiModelProperty(value = "纬度,后续扩展使用")
    private float  latitude;

}