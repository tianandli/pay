package com.lijie.pay.wechat.token;

import com.alibaba.fastjson.JSONObject;
import com.lijie.pay.constant.WechatConstant;
import com.lijie.pay.utils.HttpUtil;
import lombok.extern.slf4j.Slf4j;


/**
 * @Package: cc.feefox.wechat.token
 * @author: cc
 * @date: 2018年8月20日 上午10:45:43
 */
@Slf4j
public class GetAccessToken {


    public static AccessToken getInterfaceToken(String appid, String appsecret) throws Exception {
        AccessToken accessToken = null;
        String requestUrl = WechatConstant.ACCESS_TOKEN_URL.replace("APPID", appid).replace("APPSECRET", appsecret);
        JSONObject jsonObject = HttpUtil.httpRequest(requestUrl, "GET", null);

        if (null != jsonObject) {
            try {
                accessToken = new AccessToken();
                accessToken.setAccess_token(jsonObject.getString("access_token"));
                accessToken.setExpires_in(jsonObject.getInteger("expires_in"));
            } catch (Exception e) {
                accessToken = null;
                log.error(jsonObject.toString());
                System.out.println(e.getMessage());
            }
        }

        return accessToken;

    }
}