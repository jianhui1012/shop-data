package com.example.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.SaleData;
import com.example.entity.vo.*;
import com.example.mapper.SaleDataMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SaleDataService extends ServiceImpl<SaleDataMapper, SaleData> {

    @Resource
    private SaleDataMapper saleDataMapper;

    public QuantitySalesVolume selectJyQuantitySalesVolume(int type, String shopName, String time) {
        return saleDataMapper.selectJyQuantitySalesVolume(type, shopName, time);
    }

    public QuantitySalesVolume selectJyAvgQuantitySalesVolume(String shopName, String time, String typeName) {
        return saleDataMapper.selectJyAvgQuantitySalesVolume(shopName, time, typeName);
    }

    public QuantitySalesVolume selectFyQuantitySalesVolume(int type, String shopName, String time) {
        return saleDataMapper.selectFyQuantitySalesVolume(type, shopName, time);
    }

    public QuantitySalesVolume selectFyAvgQuantitySalesVolume(String shopName, String time, String typeName) {
        return saleDataMapper.selectFyAvgQuantitySalesVolume(shopName, time, typeName);
    }

    public NextMonthAmount selectNextMonthAmount(String shopName, String time) {
        NextMonthAmount nextMonthAmount = new NextMonthAmount();
        Double double1 = saleDataMapper.selectNextMonthAmount(shopName, time, 1);
        Double double2 = saleDataMapper.selectNextMonthAmount(shopName, time, 0);
        double jy = double1 == null ? 0 : double1;
        double fy = double2 == null ? 0 : double2;
        nextMonthAmount.setNextMonthJyAmount(jy);
        nextMonthAmount.setNextMonthFyAmount(fy);
        nextMonthAmount.setNextMonthAmount(jy + fy);
        return nextMonthAmount;
    }

    public Profit selectProfit(String shopName, String time) {
        Profit profit = new Profit();
        Double double1 = saleDataMapper.selectProfit(shopName, time, 1);
        Double double2 = saleDataMapper.selectProfit(shopName, time, 0);
        double jy = double1 == null ? 0 : double1;
        double fy = double2 == null ? 0 : double2;
        profit.setJyProfit(jy);
        profit.setFyProfit(fy);
        profit.setTotalProfit(jy + fy);
        return profit;
    }

    public List<ShopMonthData> selectShopMonthData(String shopName, int type) {
        if (type == 1) {
            return saleDataMapper.selectShopMonthJyData(shopName, type);
        }
        return saleDataMapper.selectShopMonthData(shopName, type);
    }

    public List<PurchaseReminder> selectFyZFPurchaseReminder(String shopName, String time) {
        return saleDataMapper.selectFyZFPurchaseReminder(shopName, time);
    }


    public List<PurchaseReminder> selectFyJFPurchaseReminder(String shopName, String time) {
        return saleDataMapper.selectFyJFPurchaseReminder(shopName, time);
    }

    public List<SaleSuggestionResp> selectSaleSuggestion(String shopName, int type) {
        List<SaleSuggestion> saleSuggestions = saleDataMapper.selectSaleSuggestion(shopName, type);
        Map<String, List<SaleSuggestion>> groupByUserNameMap = saleSuggestions.stream().collect(Collectors.groupingBy(SaleSuggestion::getCategory));
        List<SaleSuggestionResp> saleSuggestionResps = new ArrayList<>();
        SaleSuggestionResp saleSuggestionResp;
        for (String key : groupByUserNameMap.keySet()) {
            saleSuggestionResp = new SaleSuggestionResp();
            saleSuggestionResp.setName(key);
            List<SaleSuggestion> saleList = groupByUserNameMap.get(key);
            if (saleList != null && saleList.size() > 5) {
                saleList = saleList.subList(0, 5);
            }
            saleSuggestionResp.setSaleSuggestionList(saleList);
            saleSuggestionResps.add(saleSuggestionResp);
        }
        return saleSuggestionResps;
    }

    public List<CombinationBean> selectCombinationList(String shopName, String time) {
        //saleDataMapper.selectCombinationList(shopName, time);
        List<CombinationBean> combinationBeans = new ArrayList<>();
        CombinationBean combinationBean;
        for (int i = 0; i < 10; i++) {
            combinationBean = new CombinationBean();
            combinationBean.setJyGoodName("芙蓉王(硬)");
            combinationBean.setFyGoodName1("李子园果蔬酸奶饮品");
            combinationBean.setFyGoodName2("农夫山泉天然饮用水2L");
            combinationBean.setFyGoodName3("好劲道老坛酸菜");
            combinationBeans.add(combinationBean);
        }
        return combinationBeans;
    }
}
