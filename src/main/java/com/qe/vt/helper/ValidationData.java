package com.qe.vt.helper;

import com.qe.vt.pojo.RequestPojo;
import com.qe.vt.utils.DateUtils;
import com.qe.vt.utils.TestDataProvider;

public class ValidationData {
	
	public static RequestPojo getValidDataForTradeSpotForward() {
		RequestPojo pojo = new RequestPojo();
		pojo.setTrader(TestDataProvider.getFullName());
		pojo.setTradeDate(DateUtils.getWeekdayDate());
		pojo.setValueDate(DateUtils.getWeekdayDateAhead(pojo.getTradeDate()));
		pojo.setCustomer(TestDataProvider.getValidCustomer());
		pojo.setLegalEntity(TestDataProvider.getValidLegalEntity());
		pojo.setCcyPair(TestDataProvider.getCurrencyCodePair());
		return pojo;
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
	
	public static RequestPojo getValueDateOlderThanTradeDateData() {
		RequestPojo pojo = new RequestPojo();
		pojo.setTrader(TestDataProvider.getFullName());
		pojo.setTradeDate(DateUtils.getWeekdayDate());
		pojo.setValueDate(DateUtils.getWeekDayDateBehind(pojo.getTradeDate()));
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
		pojo.setExerciseStartDate(DateUtils.getWeekdayDateAhead(pojo.getTradeDate()));
		pojo.setPremiumDate(DateUtils.getWeekdayDateAhead(pojo.getExerciseStartDate()));
		pojo.setDeliveryDate(DateUtils.getWeekdayDateAhead(pojo.getPremiumDate()));
		pojo.setExpiryDate(DateUtils.getWeekdayDateAhead(pojo.getDeliveryDate()));
		return pojo;
	}
	
	public static RequestPojo getPremiumDateAfterDeliveryDateInvalidData() {
		RequestPojo pojo = new RequestPojo();
		pojo.setTradeDate(DateUtils.getWeekdayDate());
		pojo.setExerciseStartDate(DateUtils.getWeekdayDateAhead(pojo.getTradeDate()));
		pojo.setExpiryDate(DateUtils.getWeekdayDateAhead(pojo.getExerciseStartDate()));
		pojo.setDeliveryDate(DateUtils.getWeekdayDateAhead(pojo.getExpiryDate()));
		pojo.setPremiumDate(DateUtils.getWeekdayDateAhead(pojo.getDeliveryDate()));
		return pojo;
	}
	
	public static RequestPojo getExerciseStartDateBeforeTradeDateInvalidData() {
		RequestPojo pojo = new RequestPojo();
		pojo.setExerciseStartDate(DateUtils.getWeekdayDate());
		pojo.setTradeDate(DateUtils.getWeekdayDateAhead(pojo.getExerciseStartDate()));
		pojo.setExpiryDate(DateUtils.getWeekdayDateAhead(pojo.getTradeDate()));
		pojo.setPremiumDate(DateUtils.getWeekdayDateAhead(pojo.getExpiryDate()));
		pojo.setDeliveryDate(DateUtils.getWeekdayDateAhead(pojo.getPremiumDate()));
		return pojo;
	}
	
	public static RequestPojo getExerciseStartDateAfterExpiryDateInvalidData() {
		RequestPojo pojo = new RequestPojo();
		pojo.setTradeDate(DateUtils.getWeekdayDate());
		pojo.setExpiryDate(DateUtils.getWeekdayDateAhead(pojo.getTradeDate()));
		pojo.setExerciseStartDate(DateUtils.getWeekdayDateAhead(pojo.getExpiryDate()));
		pojo.setPremiumDate(DateUtils.getWeekdayDateAhead(pojo.getExerciseStartDate()));
		pojo.setDeliveryDate(DateUtils.getWeekdayDateAhead(pojo.getPremiumDate()));
		return pojo;
	}
}
