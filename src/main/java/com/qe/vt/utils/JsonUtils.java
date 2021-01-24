package com.qe.vt.utils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qe.vt.pojo.RequestPojo;

public class JsonUtils {
	private JsonUtils() {}	
	
	public static <T> T getPojoClassFromJsonString(String responseString, Class<T> pojoClassName){
        
        T object = null;
		try {
			ObjectMapper mapper = new ObjectMapper();
			object = mapper.readValue(responseString, pojoClassName);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
        return object;
    }
	
	public static RequestPojo[] getArrayOfRequestPojoClassFromJsonString(String responseString) throws IOException {
        RequestPojo[] reqPojo = null;
		ObjectMapper mapper = new ObjectMapper();
		reqPojo = mapper.readValue(responseString, RequestPojo[].class);
        return reqPojo;
    }
	
	public static JSONArray getJSONArrayFromJsonFile(File file) {
		String jsonFileAsAString; 
		JSONArray array = null;
		try {
			jsonFileAsAString = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
	        array = new JSONArray(jsonFileAsAString);
		} catch (Exception e) {
			e.printStackTrace();
		}
        return array;
	}
	
	public static JSONObject getJSONObjectFromJsonFile(File file) {
		String jsonFileAsAString;
		JSONObject obj = null;
		try {
			jsonFileAsAString = FileUtils.readFileToString(file,StandardCharsets.UTF_8);
	        obj = new JSONObject(jsonFileAsAString);
		} catch (Exception e) {
			e.printStackTrace();
		}
        return obj;
	}

    public static String getJsonStringToPost(File file,RequestPojo pojo) {
        return getUpdatedJson(getJSONObjectFromJsonFile(file), pojo);
        }
    
    public static String getRequestObjectToPost(File file,RequestPojo pojo) {
        return getUpdatedJson(getJSONObjectFromJsonFile(file), pojo);
        }
    
    public static String getUpdatedJson(JSONObject obj,RequestPojo pojo) {
    	return getUpdatedJsonObject(obj, pojo).toString();
    }
    
    public static JSONObject getUpdatedJsonObject(JSONObject obj,RequestPojo pojo) {
	   	if(null!=pojo.getAmount1())
	        obj.put("amount1", pojo.getAmount1());
	   	if(null!=pojo.getAmount2())
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
        if(null!=pojo.getRate())
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
        if(null!=pojo.getPremium())
        	obj.put("premium", pojo.getPremium());
        if(StringUtils.isNotBlank(pojo.getPremiumCcy())) 
        	obj.put("premiumCcy", pojo.getPremiumCcy());
        if(StringUtils.isNotBlank(pojo.getPremiumType())) 
        	obj.put("premiumType", pojo.getPremiumType());
        if(StringUtils.isNotBlank(pojo.getPremiumDate())) 
        	obj.put("premiumDate", pojo.getPremiumDate());
        if(StringUtils.isNotBlank(pojo.getPayCcy())) 
        	obj.put("payCcy", pojo.getPayCcy()); 
        return obj;
   }

}