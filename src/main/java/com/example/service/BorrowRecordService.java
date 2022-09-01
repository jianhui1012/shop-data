package com.example.service;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.common.Result;
import com.example.entity.BorrowRecord;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.Good;
import com.example.entity.Permission;
import com.example.exception.CustomException;
import com.example.mapper.BorrowRecordMapper;
import com.example.mapper.GoodMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class BorrowRecordService extends ServiceImpl<BorrowRecordMapper, BorrowRecord> {

    @Resource
    private BorrowRecordMapper borrowRecordMapper;

    @Resource
    private GoodMapper goodMapper;

    public Boolean createBorrowRecord(BorrowRecord borrowRecord) {
        String goodStr = borrowRecord.getGoodsId();
        String[] ids =  goodStr.split(",");
        if (ArrayUtil.isEmpty(ids)) {
            throw new CustomException("-1", "商品Id列表不能为空");
        }
        for (String id : ids) {
            Good good = goodMapper.selectOne(Wrappers.<Good>lambdaQuery().like(Good::getGoodId, id));
            //库存-借出数量小于等于0借用不足
            long sumCount = good.getStock();
            long borrowCount = good.getBorrowCount();
            if (sumCount - borrowCount <= 0) {
                throw new CustomException("-1", "可借用数量不足");
            }
            borrowRecord.setBorrowStatus(3);
            return this.save(borrowRecord);
        }
        return true;
    }
}
