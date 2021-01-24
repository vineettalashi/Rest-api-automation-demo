package com.qe.vt.dataprovider;

import java.util.Currency;

import com.qe.vt.utils.RandomUtils;

public class TestDataGenerator {
	
	private TestDataGenerator() {}
	
    public static String getFullName() {
        return RandomUtils.getRandomString(RandomUtils.getRandomSingleDigit())+" "+RandomUtils.getRandomString(RandomUtils.getRandomSingleDigit());
    }

    static final String VALID_CUSTOMER="PLUTO1";
    static final String VALID_LEGAL_ENTITY="CSZurich";
    static final String INVALID_ISO_CURRENCY_CODE="ABCUSD";
    static final String INVALID_PRODUCT_TYPE="INVALID_PRODUCT_TYPE";
    static final String EUROPEAN_STYLE="EUROPEAN";
    static final String AMERICAN_STYLE="AMERICAN";

    public static String getInvalidCustomer() {
        return RandomUtils.getRandomString(RandomUtils.getRandomSingleDigit());
    }

    public static String getInvalidLegalEntity() {
        return RandomUtils.getRandomString(RandomUtils.getRandomSingleDigit());
    }

    public static String getValidISOCurrencyCode() {
        return Currency.getAvailableCurrencies().toArray()[RandomUtils.getRandomDigit(0,225)].toString();
    }
    
    public static String getCurrencyCodePair() {
        String currencyPair;
        String currency1 = getValidISOCurrencyCode();
        String currency2 = getValidISOCurrencyCode();
        currencyPair = currency1 + currency2;
        return currencyPair;
    }

	public static String getInvalidStyle() {
		return RandomUtils.getRandomString(RandomUtils.getRandomSingleDigit());
	}

   
}
