package com.example.controller;

import com.example.common.Result;
import com.example.entity.SaleData;
import com.example.service.SaleDataService;
import com.example.entity.User;
import org.springframework.web.bind.annotation.*;
import com.example.exception.CustomException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;;

@RestController
@RequestMapping("/api/saleData")
public class SaleDataController {
    @Resource
    private SaleDataService saleDataService;
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
    public Result<?> save(@RequestBody SaleData saleData) {
        return Result.success(saleDataService.save(saleData));
    }

    @PutMapping
    public Result<?> update(@RequestBody SaleData saleData) {
        return Result.success(saleDataService.updateById(saleData));
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        saleDataService.removeById(id);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result<?> findById(@PathVariable Long id) {
        return Result.success(saleDataService.getById(id));
    }

    @GetMapping
    public Result<?> findAll() {
        return Result.success(saleDataService.selectByMyWrapper(""));
    }

}
