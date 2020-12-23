package com.qe.vt.utils;

import java.util.Currency;

public class TestDataProvider {
	
    public static String getFullName() {
        return RandomUtils.getRandomString(RandomUtils.getRandomSingleDigit())+" "+RandomUtils.getRandomString(RandomUtils.getRandomSingleDigit());
    }

    public static String getValidCustomer() {
        return "PLUTO1";
    }

    public static String getInvalidCustomer() {
        return RandomUtils.getRandomString(RandomUtils.getRandomSingleDigit());
    }

    public static String getValidLegalEntity() {
        return "CSZurich";
    }

    public static String getInvalidLegalEntity() {
        return RandomUtils.getRandomString(RandomUtils.getRandomSingleDigit());
    }

    public static String getCurrencyCode() {
        return Currency.getAvailableCurrencies().toArray()[RandomUtils.getRandomDigit(0,225)].toString();
    }

    public static String getCurrencyCodePair() {
        String currencyPair;
        String currency1 = getCurrencyCode();
        String currency2 = getCurrencyCode();
        currencyPair = currency1 + currency2;
        return currencyPair;
    }

    public static String getInvalidProductType() {
        return "INVALID_PRODUCT_TYPE";
    }

   
}
