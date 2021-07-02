package com.lijie.pay.wechat.token;


import com.lijie.pay.constant.WechatConstant;
import lombok.extern.slf4j.Slf4j;

/**
 * 定时获取accesstoken
 *
 * @Package: cc.feefox.wechat.token
 * @author: cc
 * @date: 2018年8月20日 上午10:44:10
 */
@Slf4j
public class TokenThread implements Runnable {

    public static AccessToken accesstoken = null;

    public void run() {

        while (true) {
            try {
                accesstoken = GetAccessToken.getInterfaceToken(WechatConstant.APPID, WechatConstant.APPSECRET);
                if (null != accesstoken) {
                    WechatConstant.ACCESS_TOKEN = accesstoken.getAccess_token();
                    log.info("获取accesstoken成功，accesstoken：" + accesstoken.getAccess_token() + " 有效时间为"
                            + accesstoken.getExpires_in());
                    Thread.sleep((accesstoken.getExpires_in() - 200) * 1000);// 休眠7000秒
                } else {
                    log.info("获取accesstoken失败");
                    Thread.sleep(60 * 1000);
                }
            } catch (Exception e) {
                try {
                    Thread.sleep(60 * 1000);
                } catch (Exception e2) {
                    log.info(e2.getMessage());
                }
            }
        }
    }
}