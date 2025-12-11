package com.example.patient.util;

import java.security.SecureRandom;

public class RandomAccountUtil {
    private static final SecureRandom RANDOM = new SecureRandom();

    /**
     * 生成 10 位数字字符串
     */
    public static String next10Digits() {
        // 生成 0-9999999999 之间的数，然后左补 0 到 10 位
        long num = RANDOM.nextLong(10_000_000_000L); // Java 17+
        return String.format("%010d", num);
    }

    private RandomAccountUtil() {}
}
