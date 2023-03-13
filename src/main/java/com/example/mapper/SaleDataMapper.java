package com.example.mapper;

import com.example.entity.SaleData;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SaleDataMapper extends BaseMapper<SaleData> {

    List<SaleData> selectByMyWrapper(@Param("shop_name") String shopName);
}
