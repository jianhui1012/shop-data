package com.example.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.example.common.Result;
import com.example.entity.vo.*;
import com.example.service.SaleDataService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

;

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
        return Result.success(saleDataService.selectJyQuantitySalesVolume(type, shopName, time));
    }

    @ApiOperation(value = "查询店铺平均的的数量、成本、销售额接口")
    @GetMapping(value = "/selectAvgQuantitySalesVolume")
    public Result<QuantitySalesVolume> selectAvgQuantitySalesVolume(@ApiParam(value = "店铺名称") @RequestParam String shopName,
                                                                    @ApiParam(value = "查询的时间 不传或者为空的话默认是当前时间") @RequestParam(required = false) String time,
    @ApiParam(value = "档位") String typeName) {
        if (StrUtil.isEmpty(time)) {
            time = DateUtil.now();
        }
        return Result.success(saleDataService.selectJyAvgQuantitySalesVolume(shopName, time,typeName));
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

    @ApiOperation(value = "查询店铺按月份找出卷烟销售数量、成本、毛利（卷烟量本利）", response = ShopMonthData.class)
    @GetMapping(value = "/selectShopMonthData")
    public Result<List<ShopMonthData>> selectShopMonthData(@ApiParam(value = "店铺名称") @RequestParam String shopName) {
        return Result.success(saleDataService.selectShopMonthData(shopName,1));
    }

    @ApiOperation(value = "查询店铺非烟进货提醒表的增幅")
    @GetMapping(value = "/selectFyZFPurchaseReminder")
    public Result<List<PurchaseReminder>> selectFyZFPurchaseReminder(@ApiParam(value = "店铺名称") @RequestParam String shopName,
                                                                     @ApiParam(value = "查询的时间 不传或者为空的话默认是当前时间") @RequestParam(required = false) String time) {
        return Result.success(saleDataService.selectFyZFPurchaseReminder(shopName, time));
    }


    @ApiOperation(value = "查询店铺非烟进货提醒表的降幅")
    @GetMapping(value = "/selectFyJFPurchaseReminder")
    public Result<List<PurchaseReminder>> selectFyJFPurchaseReminder(@ApiParam(value = "店铺名称") @RequestParam String shopName,
                                                                     @ApiParam(value = "查询的时间 不传或者为空的话默认是当前时间") @RequestParam(required = false) String time) {
        return Result.success(saleDataService.selectFyJFPurchaseReminder(shopName, time));
    }


    @ApiOperation(value = "查询店铺分析数据(整合了所有数据)")
    @GetMapping(value = "/selectShopData")
    public Result<ShopData> selectShopData(@ApiParam(value = "店铺名称") @RequestParam String shopName,
                                           @ApiParam(value = "查询的时间 不传或者为空的话默认是当前时间") @RequestParam(required = false) String time,@ApiParam(value = "档位") @RequestParam String typeName) {
        ShopData shopData = new ShopData();
        if (StrUtil.isEmpty(time)) {
            time = DateUtil.now();
        }
        Profit profit = saleDataService.selectProfit(shopName, time);
        NextMonthAmount nextMonthAmount = saleDataService.selectNextMonthAmount(shopName, time);
        //查询类型：1是当月，2是今年以来 3是去年同期
        QSVData jyQsvData = new QSVData();
        List<QuantitySalesVolume> jyQSVList = new ArrayList<>();
        jyQSVList.add(saleDataService.selectJyQuantitySalesVolume(1, shopName, time));
        jyQSVList.add(saleDataService.selectJyQuantitySalesVolume(2, shopName, time));
        jyQSVList.add(saleDataService.selectJyQuantitySalesVolume(3, shopName, time));
        jyQSVList.add(saleDataService.selectJyAvgQuantitySalesVolume(shopName, time,typeName));
        //卷烟数据
        jyQsvData.setQsvList(jyQSVList);
        jyQsvData.setNextMonthAmount(nextMonthAmount.getNextMonthJyAmount());
        jyQsvData.setProfit(profit.getJyProfit());

        QSVData fyQsvData = new QSVData();
        List<QuantitySalesVolume> fyQSVList = new ArrayList<>();
        fyQSVList.add(saleDataService.selectFyQuantitySalesVolume(1, shopName, time));
        fyQSVList.add(saleDataService.selectFyQuantitySalesVolume(2, shopName, time));
        fyQSVList.add(saleDataService.selectFyQuantitySalesVolume(3, shopName, time));
        fyQSVList.add(saleDataService.selectFyAvgQuantitySalesVolume(shopName, time,typeName));
        //非烟数据
        fyQsvData.setQsvList(fyQSVList);
        fyQsvData.setNextMonthAmount(nextMonthAmount.getNextMonthFyAmount());
        fyQsvData.setProfit(profit.getFyProfit());
        ShopQSVData shopQSVData = new ShopQSVData();
        shopQSVData.setJyQSVData(jyQsvData);
        shopQSVData.setFyQSVData(fyQsvData);
        shopData.setShopQSVData(shopQSVData);

        shopData.setShopMonthJyDataList(saleDataService.selectShopMonthData(shopName,1));
        shopData.setShopMonthFyDataList(saleDataService.selectShopMonthData(shopName,0));

        shopData.setZfPurchaseReminder(saleDataService.selectFyZFPurchaseReminder(shopName, time));
        shopData.setJfPurchaseReminder(saleDataService.selectFyJFPurchaseReminder(shopName, time));
        //卷烟
        shopData.setSaleJySuggestions(saleDataService.selectSaleSuggestion(shopName,1));
        //非烟
        shopData.setSaleFySuggestions(saleDataService.selectSaleSuggestion(shopName,0));
        shopData.setCombinationBeans(saleDataService.selectCombinationList(shopName,time));
        return Result.success(shopData);
    }
}
