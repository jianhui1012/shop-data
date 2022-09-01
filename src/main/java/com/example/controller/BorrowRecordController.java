package com.example.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.example.common.Result;
import com.example.entity.BorrowRecord;
import com.example.service.BorrowRecordService;
import com.example.entity.User;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
    @PostMapping
    public Result<?> requestBorrowGood(@RequestBody BorrowRecord borrowRecord) {
        if (borrowRecord == null) {
            throw new CustomException("-1", "参数错误");
        }
        return Result.success(borrowRecordService.createBorrowRecord(borrowRecord));
    }

    @ApiOperation(value = "归还商品接口")
    @PostMapping("/{borrowRecordId}")
    public Result<?> giveBackGood(@PathVariable Long borrowRecordId) {
        if (borrowRecordId == null) {
            throw new CustomException("-1", "参数错误");
        }
        BorrowRecord data = borrowRecordService.getById(borrowRecordId);
        data.setBorrowStatus(2);
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

    @GetMapping("/{id}")
    public Result<?> findById(@PathVariable Long id) {
        return Result.success(borrowRecordService.getById(id));
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
