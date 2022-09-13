package com.example.service;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.common.Result;
import com.example.common.utils.KeyConst;
import com.example.entity.BorrowRecord;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.Good;
import com.example.entity.Permission;
import com.example.exception.CustomException;
import com.example.mapper.BorrowRecordMapper;
import com.example.mapper.GoodMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class BorrowRecordService extends ServiceImpl<BorrowRecordMapper, BorrowRecord> {

    @Resource
    private BorrowRecordMapper borrowRecordMapper;

    @Resource
    private GoodMapper goodMapper;

    public Boolean createBorrowRecord(BorrowRecord borrowRecord) {
        String goodStr = borrowRecord.getGoodsId();
        String[] ids = goodStr.split(",");
        if (ArrayUtil.isEmpty(ids)) {
            throw new CustomException("-1", "商品Id列表不能为空");
        }
        for (String id : ids) {
            Good good = goodMapper.selectOne(Wrappers.<Good>lambdaQuery().like(Good::getGoodId, id));
            if (good == null) {
                throw new CustomException("-1", "借用的商品不存在");
            }
            //库存-借出数量小于等于0借用不足
            Long sumCount = good.getStock();
            Long borrowCount = good.getBorrowCount();
            if (sumCount - borrowCount <= 0) {
                throw new CustomException("-1", "可借用数量不足");
            }
            int size = borrowRecordMapper.selectCount(Wrappers.<BorrowRecord>lambdaQuery()
                    .like(BorrowRecord::getGoodsId, id).in(BorrowRecord::getBorrowStatus,
                            KeyConst.STATUS_BORROWED, KeyConst.STATUS_REQUEST_BORROW));
            if (size > 0) {
                throw new CustomException("-1", "该物品已经被占用");
            }
            borrowRecord.setStartTime(new Date());
            borrowRecord.setBorrowStatus(KeyConst.STATUS_REQUEST_BORROW);
            return this.save(borrowRecord);
        }
        return true;
    }

    public Boolean createTakeRecord(BorrowRecord borrowRecord) {
        String goodStr = borrowRecord.getGoodsId();
        String[] ids = goodStr.split(",");
        if (ArrayUtil.isEmpty(ids)) {
            throw new CustomException("-1", "商品Id列表不能为空");
        }
        for (String id : ids) {
            Good good = goodMapper.selectOne(Wrappers.<Good>lambdaQuery().like(Good::getGoodId, id));
            if (good == null) {
                throw new CustomException("-1", "借用的商品不存在");
            }
            if (!"享用类型".equals(good.getType())) {
                throw new CustomException("-1", "商品不是享用类型的");
            }
            //库存-借出数量小于等于0借用不足
            Long sumCount = good.getStock();
            int borrowCount = borrowRecord.getTakeCount();
            if (sumCount - borrowCount <= 0) {
                throw new CustomException("-1", "可享用数量不足");
            }

            borrowRecord.setStartTime(new Date());
            borrowRecord.setBorrowStatus(KeyConst.STATUS_TAKE);
            boolean result = this.save(borrowRecord);
            if (result) {
                //更新物品表库存
                good.setStock(sumCount - borrowRecord.getTakeCount());
                result = retBool(goodMapper.updateById(good));
            }
            return result;
        }
        return true;
    }
}
