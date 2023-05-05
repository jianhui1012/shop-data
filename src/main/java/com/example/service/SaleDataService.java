package com.example.service;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.text.StrBuilder;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.BeanUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.SaleData;
import com.example.entity.vo.*;
import com.example.mapper.SaleDataMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SaleDataService extends ServiceImpl<SaleDataMapper, SaleData> {

    @Resource
    private SaleDataMapper saleDataMapper;

    public QuantitySalesVolume selectJyQuantitySalesVolume(int type, String shopName, String time) {
        return saleDataMapper.selectJyQuantitySalesVolume(type, shopName, time);
    }

    public QuantitySalesVolume selectJyAvgQuantitySalesVolume(String shopName, String time, String typeName) {
        return saleDataMapper.selectJyAvgQuantitySalesVolume(shopName,typeName,time);
    }

    public QuantitySalesVolume selectFyQuantitySalesVolume(int type, String shopName, String time) {
        return saleDataMapper.selectFyQuantitySalesVolume(type, shopName, time);
    }

    public QuantitySalesVolume selectFyAvgQuantitySalesVolume(String shopName, String time, String typeName) {
        return saleDataMapper.selectFyAvgQuantitySalesVolume(shopName, typeName,time);
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
        List<SaleSuggestion>  saleSuggestions;
        if(type==1){
            saleSuggestions = saleDataMapper.selectJySaleSuggestion(shopName, type);
        }else {
            saleSuggestions = saleDataMapper.selectFySaleSuggestion(shopName, type);
        }
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
        List<BillCodeBean> billCodeBeans = saleDataMapper.selectCombinationList(shopName, time);
        HashMap<String, List<BillCodeBean>> hashMap = new HashMap<>();
        for (BillCodeBean billCodeBean : billCodeBeans) {
            String billCode = billCodeBean.getBillCode();
            if (hashMap.containsKey(billCode)) {
                List<BillCodeBean> sonList =  hashMap.get(billCode);
                sonList.add(billCodeBean);
            }else {
                List<BillCodeBean> tempList = new ArrayList<>();
                tempList.add(billCodeBean);
                hashMap.put(billCode,tempList);
            }
        }

        List<CombinationBean> combinationBeans = new ArrayList<>();
        hashMap.forEach((key, value) -> {
            List<BillCodeBean> billCodeBeans1 = value;
            Collections.sort(billCodeBeans1);
            long count1 = billCodeBeans1.stream().filter(v->1 == v.getIsTobacco()).count();
            long count2 = billCodeBeans1.stream().filter(v->0 == v.getIsTobacco()).count();
            if(count1>0 && count2>0&&billCodeBeans1.size()>=3){
                StringBuilder goodTag = new StringBuilder();
                for (BillCodeBean codeBean : billCodeBeans1) {
                    goodTag.append(codeBean.getName()).append(",");
                }
                String goodTagStr = goodTag.toString();
                if (StrUtil.isNotEmpty(goodTagStr)) {
                    String[] nameArray = goodTagStr.split(",");
                    CombinationBean combinationBean = new CombinationBean();
                    combinationBean.setJyGoodName(nameArray.length  >= 1 ? nameArray[0] : "");
                    combinationBean.setFyGoodName1(nameArray.length >= 2 ? nameArray[1] : "");
                    combinationBean.setFyGoodName2(nameArray.length >= 3 ? nameArray[2] : "");
                    combinationBean.setFyGoodName3(nameArray.length >= 4 ? nameArray[3] : "");
                    if (combinationBeans.size() < 10) {
                        combinationBeans.add(combinationBean);
                    }
                }
            }
        });
        return combinationBeans;
    }
}
