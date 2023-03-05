package com.example.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.text.StrBuilder;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.Result;
import com.example.common.utils.KeyConst;
import com.example.entity.BorrowRecord;
import com.example.entity.Good;
import com.example.entity.User;
import com.example.exception.CustomException;
import com.example.service.BorrowRecordService;
import com.example.service.GoodService;
import com.example.service.UserService;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;

@Api(tags = "借用记录接口")
@RestController
@RequestMapping("/api/borrowRecord")
public class BorrowRecordController {
    @Resource
    private BorrowRecordService borrowRecordService;
    @Resource
    private GoodService goodService;
    @Resource
    private UserService userService;
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
        return Result.success(borrowRecordService.updateStatus(borrowRecordId, status));
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
    public Result<?> getByShopIdAndStatus(@PathVariable Long shopId, @ApiParam(value = "status不传的话则获取该店铺下所有的借用记录 status:1-已借用，2-已归还 3-申请借用,等待店铺老板同意")
    @RequestParam(required = false) Integer status) {
        if (shopId == null) {
            throw new CustomException("-1", "参数错误");
        }
        LambdaQueryWrapper<BorrowRecord> queryWrapper = Wrappers.<BorrowRecord>lambdaQuery().
                eq(BorrowRecord::getShopId, shopId).orderByDesc(BorrowRecord::getStartTime);
        if (status != null) {
            queryWrapper = queryWrapper.eq(BorrowRecord::getBorrowStatus, status);
        }
        return Result.success(borrowRecordService.list(queryWrapper));
    }

    @GetMapping
    public Result<?> findAll() {
        LambdaQueryWrapper<BorrowRecord> queryWrapper = Wrappers.<BorrowRecord>lambdaQuery()
                .orderByDesc(BorrowRecord::getStartTime);
        return Result.success(borrowRecordService.list(queryWrapper));
    }

    @GetMapping("/page")
    public Result<?> findPage(@RequestParam(required = false, defaultValue = "") String name,
                              @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                              @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        LambdaQueryWrapper<BorrowRecord> query = Wrappers.<BorrowRecord>lambdaQuery().orderByDesc(BorrowRecord::getStartTime);
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

    @ApiOperation(value = "登记接口")
    @PostMapping("/requestTakeGood")
    public Result<?> requestTakeGood(@RequestBody BorrowRecord borrowRecord) {
        if (borrowRecord == null) {
            throw new CustomException("-1", "参数错误");
        }
        return Result.success(borrowRecordService.createTakeRecord(borrowRecord));
    }

    @ApiOperation(value = "根据用户id和店铺id获取未归还的商品提示")
    @GetMapping("/getNonReturnedTips/{shopId}")
    public Result<?> getNonReturnedTips(@PathVariable Long shopId, @RequestParam Integer userId) {
        if (shopId == null) {
            throw new CustomException("-1", "店铺id不能为空");
        }
        if (userId == null) {
            throw new CustomException("-1", "用户id不能为空");
        }
        LambdaQueryWrapper<BorrowRecord> queryWrapper = Wrappers.<BorrowRecord>lambdaQuery().
                eq(BorrowRecord::getShopId, shopId).eq(BorrowRecord::getBorrowUserId, userId)
                .eq(BorrowRecord::getBorrowStatus, KeyConst.STATUS_BORROWED)
                .orderByDesc(BorrowRecord::getStartTime);
        List<BorrowRecord> recordList = borrowRecordService.list(queryWrapper);
        StrBuilder tipText = new StrBuilder();
        if (recordList.size() > 0) {
            List<String> goodIdList = recordList.stream().map(BorrowRecord::getGoodsId).collect(Collectors.toList());
            List<Good> goodList = goodService.getBaseMapper().selectList(Wrappers.<Good>lambdaQuery().in(Good::getGoodId, goodIdList));
            List<String> goodListName = goodList.stream().map(Good::getName).collect(Collectors.toList());
            String nameStr = Convert.toStr(goodListName);
            if (nameStr.length() > 2) {
                tipText.append("有");
                tipText.append(nameStr.substring(1, nameStr.length() - 1));
                tipText.append("未归还");
            }
        }
        return Result.success(tipText.toString());
    }


    @ApiOperation(value = "生成借用记录接口")
    @GetMapping("/creatTestBorrowGood")
    public Result<?> requestBorrowGood() {
        if (borrowRecordService.list().size() >= 2000) {
            throw new CustomException("-1", "数量量大于2000了");
        }
        List<User> userList = userService.list();
        List<Good> goodList = goodService.list();

        BorrowRecord borrowRecord;
        for (int i = 0; i <= 2000; i++) {
            borrowRecord = new BorrowRecord();
            //用户数据
            User user = RandomUtil.randomEle(userList);
            borrowRecord.setBorrowUserId(Math.toIntExact(user.getId()));
            borrowRecord.setUsername(user.getUsername());
            borrowRecord.setPhone(user.getPhone());
            //商品数据随机
            Good good = RandomUtil.randomEle(goodList);
            borrowRecord.setGoodsId(good.getGoodId());
            borrowRecord.setShopId(good.getShopId());
            //时间生成
            String ksrq = "2022-10-01 06:00:00";
            String jsrq = "2023-02-28 23:00:00";
            //分别获取开始时间和结束时间时间戳
            long a = DateUtil.parse(ksrq).getTime();
            long b = DateUtil.parse(jsrq).getTime();
            long c = RandomUtil.randomLong(a, b);
            //将时间戳转为日期
            DateTime dateTime = new DateTime(c);
            String sjrq = DateUtil.format(dateTime, "yyyy-MM-dd HH:mm:ss");
            System.out.println(sjrq);
            borrowRecord.setStartTime(new Date(c));
            long c1 = c+RandomUtil.randomLong(5*60*60*1000,24*5*60*60*1000);
            borrowRecord.setEndTime(new Date(c1));
            if("享用类型".equals(good.getType())){
                borrowRecord.setBorrowStatus(4);
                borrowRecord.setTakeCount(RandomUtil.randomInt(1,10));
            }else {
                borrowRecord.setBorrowStatus(2);
            }
            borrowRecordService.save(borrowRecord);
        }
        return Result.success();
    }

}
