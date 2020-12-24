package com.qe.vt.utils;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;

import com.qe.vt.pojo.RequestPojo;

public class JsonUtils {

    public static String getJsonToPost(File file,RequestPojo pojo) throws IOException {
        String JsonFileAsAString = FileUtils.readFileToString(file, "UTF-8");
        JSONObject obj = new JSONObject(JsonFileAsAString);
        
        if(StringUtils.isNotBlank(pojo.getAmount1())) 
        	obj.put("amount1", pojo.getAmount1());
        if(StringUtils.isNotBlank(pojo.getAmount2())) 
        	obj.put("amount2", pojo.getAmount2());
        if(StringUtils.isNotBlank(pojo.getTrader())) 
        	obj.put("trader", pojo.getTrader());
        if(StringUtils.isNotBlank(pojo.getType())) 
        	obj.put("type", pojo.getType());
        if(StringUtils.isNotBlank(pojo.getCustomer())) 
        	obj.put("customer", pojo.getCustomer());
        if(StringUtils.isNotBlank(pojo.getTradeDate())) 
        	obj.put("tradeDate", pojo.getTradeDate());
        if(StringUtils.isNotBlank(pojo.getValueDate())) 
        	obj.put("valueDate", pojo.getValueDate());
        if(StringUtils.isNotBlank(pojo.getRate())) 
        	obj.put("rate", pojo.getRate());
        if(StringUtils.isNotBlank(pojo.getLegalEntity())) 
        	obj.put("legalEntity", pojo.getLegalEntity());
        if(StringUtils.isNotBlank(pojo.getDirection())) 
        	obj.put("direction", pojo.getDirection());
        if(StringUtils.isNotBlank(pojo.getCcyPair())) 
        	obj.put("ccyPair", pojo.getCcyPair());
        if(StringUtils.isNotBlank(pojo.getStyle())) 
        	obj.put("style", pojo.getStyle());
        if(StringUtils.isNotBlank(pojo.getStrategy())) 
        	obj.put("strategy", pojo.getStrategy());
        if(StringUtils.isNotBlank(pojo.getExerciseStartDate())) 
        	obj.put("exerciseStartDate", pojo.getExerciseStartDate());
        if(StringUtils.isNotBlank(pojo.getDeliveryDate())) 
        	obj.put("deliveryDate", pojo.getDeliveryDate());
        if(StringUtils.isNotBlank(pojo.getExpiryDate())) 
        	obj.put("expiryDate", pojo.getExpiryDate());
        if(StringUtils.isNotBlank(pojo.getPremium())) 
        	obj.put("premium", pojo.getPremium());
        if(StringUtils.isNotBlank(pojo.getPremiumCcy())) 
        	obj.put("premiumCcy", pojo.getPremiumCcy());
        if(StringUtils.isNotBlank(pojo.getPremiumType())) 
        	obj.put("premiumType", pojo.getPremiumType());
        if(StringUtils.isNotBlank(pojo.getPremiumDate())) 
        	obj.put("premiumDate", pojo.getPremiumDate());

        return obj.toString();
    }

}