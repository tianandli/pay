package com.lijie.pay.wx;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class ShareFunction {
    //字符串判空
    public static boolean strIsNull(String val) {
        if (val == null) {
            return true;
        } else if ("".equals(val) || "null".equals(val) || "undefined".equals(val)) {
            return true;
        }
        return false;
    }

    //获得当前时间
    public static String getNowTime() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String nowTime = df.format(new Date());
        return nowTime;
    }

    //获得随机字符串
    public static String getRandomString(int length) {
        String res = "";
        boolean xunhuan = true;
        StringBuffer buffer = new StringBuffer("1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ");
        StringBuffer sb = new StringBuffer();
        Random r = new Random();
        while (xunhuan) {
            xunhuan = false;
            int range = buffer.length();
            for (int i = 0; i < length; i++) {
                sb.append(buffer.charAt(r.nextInt(range)));
            }
            res = sb.toString();
            sb.delete(0, length);
        }
        return res;
    }
}
