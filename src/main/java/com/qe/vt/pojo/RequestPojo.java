package com.qe.vt.pojo;

import lombok.Data;

@Data
public class RequestPojo{
	private String trader;
    private String type;
    private String customer;
    private Integer amount1;
    private Integer amount2;
    private String tradeDate;
    private String valueDate;
    private Integer rate;
    private String legalEntity;
    private String direction;
    private String ccyPair;
    private String style;
    private String strategy;
    private String exerciseStartDate;
    private String deliveryDate;
    private String expiryDate;
    private Integer premium;
    private String premiumCcy;
    private String premiumType;
    private String premiumDate;
    private String payCcy;
}
