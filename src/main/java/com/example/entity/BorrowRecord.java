package com.example.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    private Integer borrowUserId;

    /***
     * 借用者姓名
     */
    private String username;

    /***
     * 借用者手机
     */
    private String phone;

    /**
      * 物品编号 
      */
    private String goodsId;

    /**
      * 借用时间 
      */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date startTime;

    /**
      * 归还时间 
      */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date endTime;

    /**
      * 借用状态  1-借用中，2-已归还 3-申请借用,等待店铺老板同意 0-默认
      */
    private Integer borrowStatus;
}