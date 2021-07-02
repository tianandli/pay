package com.lijie.pay.wechat;

import lombok.Data;

import java.util.TreeMap;

/**
 * 模板消息
 *
 * @author phil
 * @date 2017年7月2日
 */
@Data
public class WeChatTemplateMsg {
    /**
     * 接收者openid
     */
    private String touser;
    /**
     * 模板ID
     */
    private String template_id;

    /**
     * 模板跳转链接
     */
    @NotRequire
    private String url;

    private TreeMap<String, TreeMap<String, String>> data;

    /**
     * 参数
     *
     * @param value
     * @param color 可不填
     * @return
     */
    public static TreeMap<String, String> item(String value, String color) {
        TreeMap<String, String> params = new TreeMap<String, String>();
        params.put("value", value);
        params.put("color", color);
        return params;
    }
}
