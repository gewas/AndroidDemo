package com.icer.myutils.util.random;

import java.util.Random;

/**
 * Created by icer on 2016/2/25.
 */
public class RandomDataUtil {
    public static Random random;
    public static final String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String number = "0123456789";
    public static final String symbol = " `-=~!@#$%^&*()[]\\{}|;':\",./<>?";

    static {
        random = new Random();
    }

    private RandomDataUtil() {
    }

    /**
     * @param to must be a positive number
     * @return a int number from 0 to "to"
     */
    public static int getInt(int to) {
        return getInt(0, to);
    }

    public static int getInt(int from, int to) {
        if (to < from)
            try {
                throw new Throwable("Illegal Range!");
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        return from + random.nextInt(to - from + 1);
    }

    /**
     * generate a random string
     *
     * @param minLength min length of the string
     * @param maxLength max length of the string
     */
    public static String getString(int minLength, int maxLength, boolean containAlpha, boolean containNum, boolean containSymbol) {
        String res = "";
        if (maxLength > 0 && (containAlpha || containNum || containSymbol)) {
            String lib = "";
            if (containAlpha)
                lib += alphabet;
            if (containNum)
                lib += number;
            if (containSymbol)
                lib += symbol;
            int length = getInt(minLength, maxLength);
            for (int i = 0; i < length; i++) {
                res += lib.charAt(random.nextInt(lib.length()));
            }
        }
        return res;
    }

    /**
     * generate a random string array
     *
     * @param number    length of the string array
     * @param minLength min length of a string
     * @param maxLength max length of a string
     */
    public static String[] getStrings(int number, int minLength, int maxLength, boolean containAlpha, boolean containNum, boolean containSymbol) {
        String[] res = null;
        if (number > 0) {
            String[] arr = new String[number];
            for (int i = 0; i < number; i++) {
                arr[i] = getString(minLength, maxLength, containAlpha, containNum, containSymbol);
            }
            res = arr;
        }
        return res;
    }
}
