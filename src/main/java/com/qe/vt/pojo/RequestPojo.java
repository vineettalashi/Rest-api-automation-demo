package com.qe.vt.pojo;

import lombok.Data;

@Data
public class RequestPojo{
	private String trader;
    private String type;
    private String customer;
    private int amount1;
    private int amount2;
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
    private int premium;
    private String premiumCcy;
    private String premiumType;
    private String premiumDate;
    private String payCcy;
}
