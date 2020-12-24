package com.qe.vt.helper;

import com.qe.vt.pojo.RequestPojo;
import com.qe.vt.utils.DateUtils;
import com.qe.vt.utils.TestDataProvider;

public class ValidationData {
	
	public static RequestPojo getValidData() {
		RequestPojo pojo = new RequestPojo();
		pojo.setTrader(TestDataProvider.getFullName());
		pojo.setTradeDate(DateUtils.getWeekdayDate());
		pojo.setValueDate(DateUtils.getWeekdayDateAhead(pojo.getTradeDate()));
		pojo.setCustomer(TestDataProvider.getValidCustomer());
		pojo.setLegalEntity(TestDataProvider.getValidLegalEntity());
		pojo.setCcyPair(TestDataProvider.getCurrencyCodePair());
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

}
