package com.qe.vt.dataprovider;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import com.qe.vt.pojo.RequestPojo;
import com.qe.vt.utils.DateUtils;

public class ValidationData {
	
	private static Map<String,RequestPojo> invalidDataMap = new HashMap<>();	

	public static Map<String,RequestPojo> getMapOfProductTypeAndInvalidData() {
		invalidDataMap.put("Spot", getInvalidCounterpartyData());
		invalidDataMap.put("Forward", getInvalidCurrencyPairData());
		invalidDataMap.put("VanillaOptionAmer", getExpiryDateAfterDeliveryDateInvalidData());
		invalidDataMap.put("VanillaOptionEuro", getInvalidStyleData());
		return invalidDataMap;
	}
	
	public static RequestPojo getInvalidDataForBatchApi(JSONObject obj) {
		String type=obj.getString("type");
		
		if(type.contains("Vanilla")) {
			String style=obj.getString("style");
			if(style.contains("AMERICAN"))
				return getMapOfProductTypeAndInvalidData().get("VanillaOptionAmer");
			else if(style.contains("EUROPEAN"))
				return getMapOfProductTypeAndInvalidData().get("VanillaOptionEuro");
		}
		else return getMapOfProductTypeAndInvalidData().get(type);
		
		return null;
	}
	
	public static List<RequestPojo> getValidDataForBatchApi() {
		List<RequestPojo> listOfPojo = new LinkedList<RequestPojo>();
		listOfPojo.add(new RequestPojo());
		listOfPojo.add(new RequestPojo());
		listOfPojo.add(new RequestPojo());
		listOfPojo.add(new RequestPojo());
		return listOfPojo;
	}
	
	public static RequestPojo getValidDataForTradeSpotForward() {
		RequestPojo pojo = new RequestPojo();
		pojo.setTrader(TestDataGenerator.getFullName());
		pojo.setTradeDate(DateUtils.getWeekdayDate());
		pojo.setValueDate(DateUtils.getWeekdayDateAfter(pojo.getTradeDate()));
		pojo.setCustomer(TestDataGenerator.getValidCustomer());
		pojo.setLegalEntity(TestDataGenerator.getValidLegalEntity());
		pojo.setCcyPair(TestDataGenerator.getCurrencyCodePair());
		return pojo;
	}
	
	public static List<RequestPojo> getInvalidDataForBatchApi() {
		List<RequestPojo> listOfPojo = new LinkedList<RequestPojo>();
		listOfPojo.add(getInvalidCounterpartyData());
		listOfPojo.add(getInvalidCurrencyPairData());
		listOfPojo.add(getExpiryDateAfterDeliveryDateInvalidData());
		listOfPojo.add(getInvalidStyleData());
		return listOfPojo;
	}
	
	public static RequestPojo getValidDataForOptionsWithEuropeanStyle() {
		RequestPojo pojo = new RequestPojo();
		pojo.setStyle(TestDataGenerator.getStyleAsEuropean());
		return pojo;
	}
	
	public static RequestPojo getValidDataForOptionsWithAmericanStyle() {
		RequestPojo pojo = new RequestPojo();
		pojo.setStyle(TestDataGenerator.getStyleAsAmerican());
		return pojo;
	}
	
	public static RequestPojo getAmericanStyleWithoutStartExerciseDateData() {
		return getValidDataForOptionsWithAmericanStyle();
	}
	
	public static RequestPojo getEuropeanStyleWithStartExerciseDateData() {
		return getValidDataForOptionsWithEuropeanStyle();
	}
	
	public static RequestPojo getValueDateOlderThanTradeDateData() {
		RequestPojo pojo = new RequestPojo();
		pojo.setTrader(TestDataGenerator.getFullName());
		pojo.setTradeDate(DateUtils.getWeekdayDate());
		pojo.setValueDate(DateUtils.getWeekDayDateBefore(pojo.getTradeDate()));
		pojo.setCustomer(TestDataGenerator.getValidCustomer());
		pojo.setLegalEntity(TestDataGenerator.getValidLegalEntity());
		pojo.setCcyPair(TestDataGenerator.getCurrencyCodePair());
		return pojo;
	}
	
	public static RequestPojo getWeekendValueDateData() {
		RequestPojo pojo = new RequestPojo();
		String weekendDay=DateUtils.getWeekendDate();
		pojo.setTradeDate(DateUtils.getPastDate(weekendDay, 1));
		pojo.setValueDate(weekendDay);
		return pojo;
	}
	
	public static RequestPojo getInvalidCounterpartyData() {
		RequestPojo pojo = new RequestPojo();
		pojo.setCustomer(TestDataGenerator.getInvalidCustomer());
		return pojo;
	}
	
	public static RequestPojo getInvalidLegalEntityData() {
		RequestPojo pojo = new RequestPojo();
		pojo.setLegalEntity(TestDataGenerator.getInvalidLegalEntity());
		return pojo;
	}

	public static RequestPojo getInvalidProductTypeData() {
		RequestPojo pojo = new RequestPojo();
		pojo.setType(TestDataGenerator.getInvalidProductType());
		return pojo;
	}
	
	public static RequestPojo getInvalidCurrencyPairData() {
		RequestPojo pojo = new RequestPojo();
		pojo.setCcyPair(TestDataGenerator.getInvalidISOCurrencyCode());
		return pojo;
	}
	
	public static RequestPojo getInvalidStyleData() {
		RequestPojo pojo = new RequestPojo();
		pojo.setStyle(TestDataGenerator.getInvalidStyle());
		return pojo;
	}

	public static RequestPojo getExpiryDateAfterDeliveryDateInvalidData() {
		RequestPojo pojo = new RequestPojo();
		pojo.setTradeDate(DateUtils.getWeekdayDate());
		pojo.setExerciseStartDate(DateUtils.getWeekdayDateAfter(pojo.getTradeDate()));
		pojo.setPremiumDate(DateUtils.getWeekdayDateAfter(pojo.getExerciseStartDate()));
		pojo.setDeliveryDate(DateUtils.getWeekdayDateAfter(pojo.getPremiumDate()));
		pojo.setExpiryDate(DateUtils.getWeekdayDateAfter(pojo.getDeliveryDate()));
		return pojo;
	}
	
	public static RequestPojo getPremiumDateAfterDeliveryDateInvalidData() {
		RequestPojo pojo = new RequestPojo();
		pojo.setTradeDate(DateUtils.getWeekdayDate());
		pojo.setExerciseStartDate(DateUtils.getWeekdayDateAfter(pojo.getTradeDate()));
		pojo.setExpiryDate(DateUtils.getWeekdayDateAfter(pojo.getExerciseStartDate()));
		pojo.setDeliveryDate(DateUtils.getWeekdayDateAfter(pojo.getExpiryDate()));
		pojo.setPremiumDate(DateUtils.getWeekdayDateAfter(pojo.getDeliveryDate()));
		return pojo;
	}
	
	public static RequestPojo getExerciseStartDateBeforeTradeDateInvalidData() {
		RequestPojo pojo = new RequestPojo();
		pojo.setExerciseStartDate(DateUtils.getWeekdayDate());
		pojo.setTradeDate(DateUtils.getWeekdayDateAfter(pojo.getExerciseStartDate()));
		pojo.setExpiryDate(DateUtils.getWeekdayDateAfter(pojo.getTradeDate()));
		pojo.setPremiumDate(DateUtils.getWeekdayDateAfter(pojo.getExpiryDate()));
		pojo.setDeliveryDate(DateUtils.getWeekdayDateAfter(pojo.getPremiumDate()));
		return pojo;
	}
	
	public static RequestPojo getExerciseStartDateAfterExpiryDateInvalidData() {
		RequestPojo pojo = new RequestPojo();
		pojo.setTradeDate(DateUtils.getWeekdayDate());
		pojo.setExpiryDate(DateUtils.getWeekdayDateAfter(pojo.getTradeDate()));
		pojo.setExerciseStartDate(DateUtils.getWeekdayDateAfter(pojo.getExpiryDate()));
		pojo.setPremiumDate(DateUtils.getWeekdayDateAfter(pojo.getExerciseStartDate()));
		pojo.setDeliveryDate(DateUtils.getWeekdayDateAfter(pojo.getPremiumDate()));
		return pojo;
	}
	
	public static RequestPojo getInvalidAmountsInvalidPayCcyInvalidPremium() {
		RequestPojo pojo = new RequestPojo();
		pojo.setAmount1(0);
		pojo.setAmount2(0);
		pojo.setPayCcy("");
		pojo.setPremium(0);
		return pojo;
	}
	
	public static RequestPojo getInvalidAmountsInvalidRateInvalidTrader() {
		RequestPojo pojo = new RequestPojo();
		pojo.setAmount1(0);
		pojo.setAmount2(0);
		pojo.setTrader("");
		pojo.setRate(0);
		return pojo;
	}

}
