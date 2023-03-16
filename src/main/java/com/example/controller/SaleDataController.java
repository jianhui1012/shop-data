package com.example.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.example.common.Result;
import com.example.entity.vo.NextMonthAmount;
import com.example.entity.vo.PurchaseReminder;
import com.example.entity.vo.QuantitySalesVolume;
import com.example.entity.vo.ShopMonthData;
import com.example.service.SaleDataService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

;import java.util.List;

@RestController
@RequestMapping("/api/saleData")
public class SaleDataController {
    @Resource
    private SaleDataService saleDataService;
    @Resource
    private HttpServletRequest request;

    @GetMapping("/{id}")
    public Result<?> findById(@PathVariable Long id) {
        return Result.success(saleDataService.getById(id));
    }

    @ApiOperation(value = "查询店铺当月/今年以来/去年同期的数量、成本、销售额接口")
    @GetMapping(value = "/getQuantitySalesVolume")
    public Result<QuantitySalesVolume> getQuantitySalesVolume(@ApiParam(value = "查询类型：1是当月，2是今年以来 3是去年同期") @RequestParam int type,
                                                              @ApiParam(value = "店铺名称") @RequestParam String shopName,
                                                              @ApiParam(value = "查询的时间 不传或者为空的话默认是当前时间") @RequestParam(required = false) String time) {
        if (!(type == 1 || type == 2 || type == 3)) {
            return Result.error(Result.FAIL_CODE, "查询类型需要传：1是当月 2是今年以来 3是去年同期");
        }
        if (StrUtil.isEmpty(time)) {
            time = DateUtil.now();
        }
        return Result.success(saleDataService.selectQuantitySalesVolume(type, shopName, time));
    }

    @ApiOperation(value = "查询店铺平均的的数量、成本、销售额接口")
    @GetMapping(value = "/selectAvgQuantitySalesVolume")
    public Result<QuantitySalesVolume> selectAvgQuantitySalesVolume(@ApiParam(value = "店铺名称") @RequestParam String shopName,
                                            @ApiParam(value = "查询的时间 不传或者为空的话默认是当前时间") @RequestParam(required = false) String time) {
        if (StrUtil.isEmpty(time)) {
            time = DateUtil.now();
        }
        return Result.success(saleDataService.selectAvgQuantitySalesVolume(shopName, time));
    }

    @ApiOperation(value = "查询店铺下个月的进货资金接口")
    @GetMapping(value = "/selectNextMonthAmount")
    public Result<NextMonthAmount> selectNextMonthAmount(@ApiParam(value = "店铺名称") @RequestParam String shopName,
                                                         @ApiParam(value = "查询的时间 不传或者为空的话默认是当前时间") @RequestParam(required = false) String time) {
        if (StrUtil.isEmpty(time)) {
            time = DateUtil.now();
        }
        return Result.success(saleDataService.selectNextMonthAmount(shopName, time));
    }

    @ApiOperation(value = "查询店铺预计产生的利润")
    @GetMapping(value = "/selectProfit")
    public Result<?> selectProfit(@ApiParam(value = "店铺名称") @RequestParam String shopName,
                                           @ApiParam(value = "查询的时间 不传或者为空的话默认是当前时间") @RequestParam(required = false) String time) {
        if (StrUtil.isEmpty(time)) {
            time = DateUtil.now();
        }
        return Result.success(saleDataService.selectProfit(shopName, time));
    }

    @ApiOperation(value = "查询店铺按月份找出卷烟销售数量、成本、毛利（卷烟量本利）",response = ShopMonthData.class)
    @GetMapping(value = "/selectShopMonthData")
    public Result<List<ShopMonthData>> selectShopMonthData(@ApiParam(value = "店铺名称") @RequestParam String shopName) {
        return Result.success(saleDataService.selectShopMonthData(shopName));
    }

    @ApiOperation(value = "查询店铺非烟进货提醒表的增幅")
    @GetMapping(value = "/selectFyZFPurchaseReminder")
    public Result<List<PurchaseReminder>> selectFyZFPurchaseReminder(@ApiParam(value = "店铺名称") @RequestParam String shopName,
                                                                     @ApiParam(value = "查询的时间 不传或者为空的话默认是当前时间") @RequestParam(required = false)String time) {
        return Result.success(saleDataService.selectFyZFPurchaseReminder(shopName,time));
    }
}
