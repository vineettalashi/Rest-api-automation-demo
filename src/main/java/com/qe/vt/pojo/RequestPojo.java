package com.qe.vt.pojo;

import lombok.Data;

@Data
public class RequestPojo{
	private String trader;
    private String type;
    private String customer;
    private String amount1;
    private String amount2;
    private String tradeDate;
    private String valueDate;
    private String rate;
    private String legalEntity;
    private String direction;
    private String ccyPair;
    private String style;
    private String strategy;
    private String exerciseStartDate;
    private String deliveryDate;
    private String expiryDate;
    private String premium;
    private String premiumCcy;
    private String premiumType;
    private String premiumDate;   
}
