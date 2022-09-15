package com.example.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.entity.BorrowRecord;
import com.example.entity.Good;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.Shop;
import com.example.exception.CustomException;
import com.example.mapper.GoodMapper;
import com.example.mapper.ShopMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class GoodService extends ServiceImpl<GoodMapper, Good> {

    @Resource
    private GoodMapper goodMapper;

    @Resource
    private ShopMapper shopMapper;

    public boolean create(Good good) {
        verification(good);
        good.setCreateTime(new Date());
        good.setUpdateTime(new Date());
        String goodId = good.getGoodId();
        int count = goodMapper.selectCount(Wrappers.<Good>lambdaQuery().eq(Good::getGoodId, goodId));
        if (count > 0) {
            throw new CustomException("-1", "商品编号已经存在");
        }
        return retBool(goodMapper.insert(good));
    }

    public boolean updateOne(Good good) {
        verification(good);
        good.setUpdateTime(new Date());
        return retBool(goodMapper.updateById(good));
    }

    public List<Good> getShopListByType(Long shopId, String type) {
        return goodMapper.selectList(Wrappers.<Good>lambdaQuery().
                eq(Good::getType, type).eq(Good::getShopId, shopId));
    }


    public void verification(Good good) {
        if (good == null) {
            throw new CustomException("-1", "商品数据为空");
        }
        long shopId = good.getShopId();
        Shop shop = shopMapper.selectById(shopId);
        if (shop == null) {
            throw new CustomException("-1", "店铺id错误");
        }
    }


}
