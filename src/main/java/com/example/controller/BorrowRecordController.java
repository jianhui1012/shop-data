package com.example.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.common.Result;
import com.example.common.utils.KeyConst;
import com.example.entity.BorrowRecord;
import com.example.entity.Good;
import com.example.service.BorrowRecordService;
import com.example.entity.User;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;
import com.example.exception.CustomException;
import cn.hutool.core.util.StrUtil;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;
import java.math.BigDecimal;

@Api(tags = "借用记录接口")
@RestController
@RequestMapping("/api/borrowRecord")
public class BorrowRecordController {
    @Resource
    private BorrowRecordService borrowRecordService;
    @Resource
    private HttpServletRequest request;

    public User getUser() {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            throw new CustomException("-1", "请登录");
        }
        return user;
    }

    @ApiOperation(value = "申请借用接口")
    @PostMapping("/requestBorrowGood")
    public Result<?> requestBorrowGood(@RequestBody BorrowRecord borrowRecord) {
        if (borrowRecord == null) {
            throw new CustomException("-1", "参数错误");
        }
        return Result.success(borrowRecordService.createBorrowRecord(borrowRecord));
    }

    @ApiOperation(value = "归还商品/审核借用的接口-更新借用状态 ")
    @PostMapping("/updateBorrowStatus/{borrowRecordId}")
    public Result<?> updateBorrowStatus(@PathVariable Long borrowRecordId, @ApiParam(value = "1-已借用，2-已归还 3-申请借用,等待店铺老板同意")
    @RequestParam Integer status) {
        if (borrowRecordId == null || status == null) {
            throw new CustomException("-1", "参数错误");
        }
        BorrowRecord data = borrowRecordService.getById(borrowRecordId);
        if (status == KeyConst.STATUS_GIVE_BACK) {
            data.setEndTime(new Date());
        }
        data.setBorrowStatus(status);
        return Result.success(borrowRecordService.saveOrUpdate(data));
    }

    @PutMapping
    public Result<?> update(@RequestBody BorrowRecord borrowRecord) {
        if (borrowRecord == null) {
            throw new CustomException("-1", "参数错误");
        }
        return Result.success(borrowRecordService.updateById(borrowRecord));
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        borrowRecordService.removeById(id);
        return Result.success();
    }

    @ApiOperation(value = "根据店铺id和借用状态来获取该店铺下所有的借用记录")
    @GetMapping("/getByShopIdAndStatus/{shopId}")
    public Result<?> getByShopIdAndStatus(@PathVariable Long shopId,@ApiParam(value = "status不传的话则获取该店铺下所有的借用记录 status:1-已借用，2-已归还 3-申请借用,等待店铺老板同意")
    @RequestParam(required = false) Integer status) {
        if (shopId == null) {
            throw new CustomException("-1", "参数错误");
        }
        LambdaQueryWrapper<BorrowRecord> queryWrapper = Wrappers.<BorrowRecord>lambdaQuery().
                eq(BorrowRecord::getShopId, shopId);
        if (status != null) {
            queryWrapper.eq(BorrowRecord::getBorrowStatus, status);
        }
        return Result.success(borrowRecordService.list(queryWrapper));
    }

    @GetMapping
    public Result<?> findAll() {
        return Result.success(borrowRecordService.list());
    }

    @GetMapping("/page")
    public Result<?> findPage(@RequestParam(required = false, defaultValue = "") String name,
                              @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                              @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        LambdaQueryWrapper<BorrowRecord> query = Wrappers.<BorrowRecord>lambdaQuery().orderByDesc(BorrowRecord::getId);
        if (StrUtil.isNotBlank(name)) {
            //query.like(BorrowRecord::getName, name);
        }
        return Result.success(borrowRecordService.page(new Page<>(pageNum, pageSize), query));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response) throws IOException {

        List<Map<String, Object>> list = CollUtil.newArrayList();

        List<BorrowRecord> all = borrowRecordService.list();
        for (BorrowRecord obj : all) {
            Map<String, Object> row = new LinkedHashMap<>();
            row.put("ID", obj.getId());
            row.put("借用者", obj.getBorrowUserId());
            row.put("物品编号", obj.getGoodsId());
            row.put("借用时间", obj.getStartTime());
            row.put("归还时间", obj.getEndTime());
            row.put("借用状态", obj.getBorrowStatus());

            list.add(row);
        }

        // 2. 写excel
        ExcelWriter writer = ExcelUtil.getWriter(true);
        writer.write(list, true);

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("借用记录信息", "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");

        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        writer.close();
        IoUtil.close(System.out);
    }

    @GetMapping("/upload/{fileId}")
    public Result<?> upload(@PathVariable String fileId) {
        String basePath = System.getProperty("user.dir") + "/src/main/resources/static/file/";
        List<String> fileNames = FileUtil.listFileNames(basePath);
        String file = fileNames.stream().filter(name -> name.contains(fileId)).findAny().orElse("");
        List<List<Object>> lists = ExcelUtil.getReader(basePath + file).read(1);
        List<BorrowRecord> saveList = new ArrayList<>();
        for (List<Object> row : lists) {
            BorrowRecord obj = new BorrowRecord();
            obj.setBorrowUserId(Integer.valueOf((String) row.get(1)));
            obj.setGoodsId((String) row.get(2));
            obj.setStartTime((Date) row.get(3));
            obj.setEndTime((Date) row.get(4));
            obj.setBorrowStatus(Integer.valueOf((String) row.get(5)));

            saveList.add(obj);
        }
        borrowRecordService.saveBatch(saveList);
        return Result.success();
    }

}
