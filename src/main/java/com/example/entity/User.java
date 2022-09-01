package com.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.example.common.handler.ListHandler;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@TableName(value = "t_user", autoResultMap = true)
public class User extends Model<User> {
    /**
     * 主键
     */
    @ApiModelProperty(value = "id",hidden = true)
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "用户名",required = true)
    private String username;

    @ApiModelProperty(value = "密码",required = true)
    private String password;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "居住地址")
    private String address;

    @ApiModelProperty(value = "年龄")
    private Integer age;

    @ApiModelProperty(value = "店铺id，身份是店铺老板才会有值，默认为空")
    private Integer  shopId;

    @ApiModelProperty(value = "角色",hidden = true)
    @TableField(typeHandler = ListHandler.class)
    private List<Long> role;

    @ApiModelProperty(value = "权限",hidden = true)
    @TableField(exist = false)
    private List<Permission> permission;

}
