package com.example.service;

import com.example.entity.BorrowRecord;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mapper.BorrowRecordMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class BorrowRecordService extends ServiceImpl<BorrowRecordMapper, BorrowRecord> {

    @Resource
    private BorrowRecordMapper borrowRecordMapper;

}
