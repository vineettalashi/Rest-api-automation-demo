package com.qe.vt.utils;

import java.util.Currency;
import java.util.Random;

import com.github.javafaker.Faker;

public class Test {
	private static Random random = new Random();
	static Faker faker = new Faker();
	public static void main(String[] args) {
		
		//System.out.println(faker.currency().code());
		//System.out.println(Currency.getInstance(faker.currency().code()));
		System.out.println(Currency.getAvailableCurrencies().size());
		System.out.println(Currency.getAvailableCurrencies().toArray()[RandomUtils.getRandomDigit(0,225)]);
	}
}
