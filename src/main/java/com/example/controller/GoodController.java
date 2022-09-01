package com.example.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.example.common.Result;
import com.example.entity.Good;
import com.example.service.GoodService;
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

@Api(tags = "商品接口")
@RestController
@RequestMapping("/api/good")
public class GoodController {
    @Resource
    private GoodService goodService;
    @Resource
    private HttpServletRequest request;

    public User getUser() {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            throw new CustomException("-1", "请登录");
        }
        return user;
    }

    @PostMapping
    public Result<?> save(@RequestBody Good good) {
        return Result.success(goodService.save(good));
    }

    @PutMapping
    public Result<?> update(@RequestBody Good good) {
        return Result.success(goodService.updateById(good));
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        goodService.removeById(id);
        return Result.success();
    }

    @GetMapping("/{shopId}")
    public Result<?> findByShopId(@PathVariable Long shopId) {
        if (shopId == null) {
            throw new CustomException("-1", "店铺id不能为空");
        }
        LambdaQueryWrapper<Good> query = Wrappers.<Good>lambdaQuery().orderByDesc(Good::getId);
        query.eq(Good::getShopId,shopId);
        return Result.success(goodService.list(query));
    }

    @GetMapping
    public Result<?> findAll() {
        return Result.success(goodService.list());
    }

    @GetMapping("/page")
    public Result<?> findPage(@RequestParam(required = false, defaultValue = "") String name,
                                                @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                                @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        LambdaQueryWrapper<Good> query = Wrappers.<Good>lambdaQuery().orderByDesc(Good::getId);
        if (StrUtil.isNotBlank(name)) {
            query.like(Good::getName, name);
        }
        return Result.success(goodService.page(new Page<>(pageNum, pageSize), query));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response) throws IOException {

        List<Map<String, Object>> list = CollUtil.newArrayList();

        List<Good> all = goodService.list();
        for (Good obj : all) {
            Map<String, Object> row = new LinkedHashMap<>();
            row.put("ID", obj.getId());
            row.put("物品名称", obj.getName());
            row.put("物品类型", obj.getType());
            row.put("物品图标", obj.getGoodUrl());
            row.put("物品编号", obj.getGoodId());

            list.add(row);
        }

        // 2. 写excel
        ExcelWriter writer = ExcelUtil.getWriter(true);
        writer.write(list, true);

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("商品信息", "UTF-8");
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
        List<Good> saveList = new ArrayList<>();
        for (List<Object> row : lists) {
            Good obj = new Good();
            obj.setName((String) row.get(1));
            obj.setType(Integer.valueOf((String) row.get(2)));
            obj.setGoodUrl((String) row.get(3));
            obj.setGoodId((String) row.get(4));

            saveList.add(obj);
        }
        goodService.saveBatch(saveList);
        return Result.success();
    }

}
