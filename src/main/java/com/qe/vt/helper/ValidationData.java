package com.qe.vt.helper;

import java.util.LinkedList;
import java.util.List;

import com.qe.vt.pojo.RequestPojo;
import com.qe.vt.utils.DateUtils;
import com.qe.vt.utils.TestDataProvider;

public class ValidationData {
	
	public static RequestPojo getValidDataForTradeSpotForward() {
		RequestPojo pojo = new RequestPojo();
		pojo.setTrader(TestDataProvider.getFullName());
		pojo.setTradeDate(DateUtils.getWeekdayDate());
		pojo.setValueDate(DateUtils.getWeekdayDateAfter(pojo.getTradeDate()));
		pojo.setCustomer(TestDataProvider.getValidCustomer());
		pojo.setLegalEntity(TestDataProvider.getValidLegalEntity());
		pojo.setCcyPair(TestDataProvider.getCurrencyCodePair());
		return pojo;
	}
	
	public static List<RequestPojo> getValidDataForBatchApi() {
		List<RequestPojo> listOfPojo = new LinkedList<RequestPojo>();
		listOfPojo.add(new RequestPojo());
		listOfPojo.add(new RequestPojo());
		listOfPojo.add(new RequestPojo());
		listOfPojo.add(new RequestPojo());
		return listOfPojo;
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
		pojo.setStyle(TestDataProvider.getStyleAsEuropean());
		return pojo;
	}
	
	public static RequestPojo getValidDataForOptionsWithAmericanStyle() {
		RequestPojo pojo = new RequestPojo();
		pojo.setStyle(TestDataProvider.getStyleAsAmerican());
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
		pojo.setTrader(TestDataProvider.getFullName());
		pojo.setTradeDate(DateUtils.getWeekdayDate());
		pojo.setValueDate(DateUtils.getWeekDayDateBefore(pojo.getTradeDate()));
		pojo.setCustomer(TestDataProvider.getValidCustomer());
		pojo.setLegalEntity(TestDataProvider.getValidLegalEntity());
		pojo.setCcyPair(TestDataProvider.getCurrencyCodePair());
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
		pojo.setCustomer(TestDataProvider.getInvalidCustomer());
		return pojo;
	}
	
	public static RequestPojo getInvalidLegalEntityData() {
		RequestPojo pojo = new RequestPojo();
		pojo.setLegalEntity(TestDataProvider.getInvalidLegalEntity());
		return pojo;
	}

	public static RequestPojo getInvalidProductTypeData() {
		RequestPojo pojo = new RequestPojo();
		pojo.setType(TestDataProvider.getInvalidProductType());
		return pojo;
	}
	
	public static RequestPojo getInvalidCurrencyPairData() {
		RequestPojo pojo = new RequestPojo();
		pojo.setCcyPair(TestDataProvider.getInvalidISOCurrencyCode());
		return pojo;
	}
	
	public static RequestPojo getInvalidStyleData() {
		RequestPojo pojo = new RequestPojo();
		pojo.setStyle(TestDataProvider.getInvalidStyle());
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
