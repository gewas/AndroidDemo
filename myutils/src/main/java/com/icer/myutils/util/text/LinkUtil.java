package com.icer.myutils.util.text;


@SuppressWarnings("ALL")
public class LinkUtil {
    public static final String TAG = LinkUtil.class.getSimpleName();

    public static final String SPACE = " ";

    public static String processText(String text) {
        String res = text;

        if (text.matches(".*[hH][tT][tT][pP][sS]?://.+\\..*")) {
            // 先处理https
            String[] ta = text.split("[hH][tT][tT][pP][sS]:/");
            String pro = "";
            for (int i = 0; i < ta.length; i++) {
                String ti = ta[i];
                if (ti.matches("/.+\\..*")) {
                    ti = "https:/" + ti;
                    // 寻找链接与文字内容的分界
                    for (int j = 0; j < ti.length(); j++) {
                        String ss = ti.substring(j, j + 1);// 取单个字符
                        if (!ss.matches("[a-zA-Z0-9\\-_%.=?&/:;]")) {
                            String s1 = ti.substring(0, j);
                            String s2 = ti.substring(j);
                            if (!s2.startsWith(SPACE))
                                ti = s1 + SPACE + s2;
                            if (i > 0) {
                                String tf = ta[i - 1];
                                if (tf.length() > 0) {
                                    String tfl = tf.substring(tf.length() - 1);
                                    if (!tfl.equals(SPACE)) {
                                        ti = SPACE + ti;
                                    }
                                }
                            }
                            break;
                        } else if (j == ti.length() - 1) {
                            if (i > 0) {
                                String tf = ta[i - 1];
                                if (tf.length() > 0) {
                                    String tfl = tf.substring(tf.length() - 1);
                                    if (!tfl.equals(SPACE)) {
                                        ti = SPACE + ti;
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
                pro += ti;
            }


            ta = pro.split("[hH][tT][tT][pP]:/");
            String pro2 = "";
            for (int i = 0; i < ta.length; i++) {
                String ti = ta[i];
                if (ti.matches("/.+\\..*")) {
                    ti = "http:/" + ti;
                    // 寻找链接与文字内容的分界
                    for (int j = 0; j < ti.length(); j++) {
                        String ss = ti.substring(j, j + 1);// 取单个字符
                        if (!ss.matches("[a-zA-Z0-9\\-_%.=?&/:;]")) {
                            String s1 = ti.substring(0, j);
                            String s2 = ti.substring(j);
                            if (!s2.startsWith(SPACE))
                                ti = s1 + SPACE + s2;
                            if (i > 0) {
                                String tf = ta[i - 1];
                                if (tf.length() > 0) {
                                    String tfl = tf.substring(tf.length() - 1);
                                    if (!tfl.equals(SPACE)) {
                                        ti = SPACE + ti;
                                    }
                                }
                            }
                            break;
                        } else if (j == ti.length() - 1) {
                            if (i > 0) {
                                String tf = ta[i - 1];
                                if (tf.length() > 0) {
                                    String tfl = tf.substring(tf.length() - 1);
                                    if (!tfl.equals(SPACE)) {
                                        ti = SPACE + ti;
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
                pro2 += ti;
            }

            res = pro2;
        }

        return res;
    }
}
