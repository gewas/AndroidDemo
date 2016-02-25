package com.icer.myutils.util.text;

@SuppressWarnings("unused")
public class PriceUtil {

    /**
     * 去掉小数中无意义的数字"0"如："9.00"中的".00"、"0.09700"中最后的"00"
     *
     * @param price 价格数字，如："450.12"
     */
    public static String fixDot0(String price) {
        String p = price;
        boolean isDecimal = p.matches("[0-9]+\\.[0-9]*");
        if (isDecimal)
            for (int i = p.length() - 1; i >= 0; i--) {
                String tail = p.charAt(i) + "";
                if ("0".equals(tail)) {
                    p = p.substring(0, p.length() - 1);
                } else if (".".equals(tail)) {
                    p = p.substring(0, p.length() - 1);
                    break;
                } else {
                    break;
                }
            }
        return p;
    }

    public static double fixDot0(double price) {
        return Double.parseDouble(fixDot0(price + ""));
    }

    public static float fixDot0(float price) {
        return Float.parseFloat(fixDot0(price + ""));
    }

    public static long fixDot0(long price) {
        return Long.parseLong(fixDot0(price + ""));
    }

    public static int fixDot0(int price) {
        return Integer.parseInt(fixDot0(price + ""));
    }

    /**
     * 截取至两位小数，最小精度至分
     *
     * @param price 价格数字，如："450.12"
     */
    public static String fixPrecision2Cent(String price) {
        double p = Double.parseDouble(price);
        return String.format("%.2f", p);
    }
}
