package com.qe.vt.utils;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class RandomUtils {

private static Random random = new Random();
	
	public static String getRandomString(int length) {
		int leftLimit = 97; // letter 'a'
	    int rightLimit = 122; // letter 'z'
	    String generatedString = random.ints(leftLimit, rightLimit + 1)
	      .limit(length)
	      .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
	      .toString();
	    return generatedString;
	}
	
	public static int getRandomSingleDigit() {
		ThreadLocalRandom random = ThreadLocalRandom.current();
	    return random.nextInt(4, 10);
	}
	
	public static int getRandomDigit(int low,int high) {
		ThreadLocalRandom random = ThreadLocalRandom.current();
	    return random.nextInt(low, high);
	}
}
