/**
 * @ClassName: JsonUtil
 * @Description: TODO
 * @author: cc
 * @date: 2018年8月20日 上午11:31:06
 */
package com.lijie.pay.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.lang3.StringUtils;

/**
 * @Package: cc.feefox.wechat.common.util
 * @author: cc
 * @date: 2018年8月20日 上午11:31:06 
 */
public class JsonUtil {

    private static Gson gson = null;

    static {
        if (gson == null) {
            gson = new GsonBuilder().serializeNulls().disableHtmlEscaping().create();
        }
    }

    /**
     * 转成bean
     *
     * @param json
     * @param cls
     * @return
     */
    public static <T> T fromJsonString(String json, Class<T> classOfT) {
        T t = null;
        if (gson != null) {
            t = gson.fromJson(json, classOfT);
        }
        return t;
    }

    public static String toJsonString(Object obj) {
        if (obj == null) {
            return "{}";
        }
        if (obj instanceof String) {
            if (StringUtils.isEmpty(obj.toString())) {
                return "{}";
            }
        }
        String json = "";
        try {
            if (gson != null) {
                json = gson.toJson(obj);
            }
        } catch (Exception e) {
            return "{}";
        }
        return json;
    }

}
