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
@TableName("t_borrow_record")
public class BorrowRecord extends Model<BorrowRecord> {
    /**
      * 主键
      */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
      * 借用者 
      */
    @ApiModelProperty(value = "借用者Id",example = "0")
    private Integer borrowUserId;

    /***
     * 借用者姓名
     */
    @ApiModelProperty(value = "借用者姓名",required = true)
    private String username;

    /***
     * 借用者手机
     */
    @ApiModelProperty(value = "借用者手机",required = true)
    private String phone;

    /**
      * 物品编号 
      */
    @ApiModelProperty(value = "物品编号，多个的话以逗号分隔，比如：1,2,3...",required = true)
    private String goodsId;

    @ApiModelProperty(value = "店铺Id",example = "0")
    private Long shopId;

    /**
      * 借用时间 
      */
    @ApiModelProperty(value = "借用时间",hidden = true,example = "0")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date startTime;

    /**
      * 归还时间 
      */
    @ApiModelProperty(value = "归还时间",hidden = true,example = "0")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date endTime;

    /**
      * 借用状态  1-已借用，2-已归还 3-申请借用,等待店铺老板同意 0-默认
      */
    @ApiModelProperty(value = "借用状态, 1-已借用，2-已归还 3-申请借用,等待店铺老板同意 0-默认",example = "0")
    private Integer borrowStatus = 0;
}