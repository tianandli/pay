package com.lijie.pay.wechat.token;

/**
 * access_token格式：json {"access_token":"ACCESS_TOKEN","expires_in":7200}
 * @Package: cc.feefox.wechat.token
 * @author: cc
 * @date: 2018年8月20日 上午10:46:25
 */
public class AccessToken {

    private String access_token;//获取到的凭证
    private int expires_in;//凭证有效时间，单位：秒

    public AccessToken(String access_token, int expires_in) {

        super();
        this.access_token = access_token;
        this.expires_in = expires_in;
    }

    public AccessToken() {
        super();
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }

}