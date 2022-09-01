package com.example.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.example.common.Result;
import com.example.entity.Shop;
import com.example.service.ShopService;
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

@Api(tags = "店铺接口")
@RestController
@RequestMapping("/api/shop")
public class ShopController {
    @Resource
    private ShopService shopService;
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
    public Result<?> save(@RequestBody Shop shop) {
        return Result.success(shopService.save(shop));
    }

    @PutMapping
    public Result<?> update(@RequestBody Shop shop) {
        return Result.success(shopService.updateById(shop));
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        shopService.removeById(id);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result<?> findById(@PathVariable Long id) {
        return Result.success(shopService.getById(id));
    }

    @GetMapping
    public Result<?> findAll() {
        return Result.success(shopService.list());
    }

    @GetMapping("/page")
    public Result<?> findPage(@RequestParam(required = false, defaultValue = "") String name,
                                                @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                                @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        LambdaQueryWrapper<Shop> query = Wrappers.<Shop>lambdaQuery().orderByDesc(Shop::getId);
        if (StrUtil.isNotBlank(name)) {
            query.like(Shop::getName, name);
        }
        return Result.success(shopService.page(new Page<>(pageNum, pageSize), query));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response) throws IOException {

        List<Map<String, Object>> list = CollUtil.newArrayList();

        List<Shop> all = shopService.list();
        for (Shop obj : all) {
            Map<String, Object> row = new LinkedHashMap<>();
            row.put("ID", obj.getId());
            row.put("店名", obj.getName());
            row.put("地址", obj.getAddress());
            row.put("联系人", obj.getPhonename());
            row.put("联系电话", obj.getPhone());
            row.put("经度", obj.getLongitude());
            row.put("纬度", obj.getLatitude());

            list.add(row);
        }

        // 2. 写excel
        ExcelWriter writer = ExcelUtil.getWriter(true);
        writer.write(list, true);

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("店铺信息", "UTF-8");
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
        List<Shop> saveList = new ArrayList<>();
        for (List<Object> row : lists) {
            Shop obj = new Shop();
            obj.setName((String) row.get(1));
            obj.setAddress((String) row.get(2));
            obj.setPhonename((String) row.get(3));
            obj.setPhone((String) row.get(4));
            obj.setLongitude((float) row.get(5));
            obj.setLatitude((float) row.get(6));

            saveList.add(obj);
        }
        shopService.saveBatch(saveList);
        return Result.success();
    }

}
