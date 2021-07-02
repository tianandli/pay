package com.lijie.pay.wechat;

/**
 * @ClassName ImageMessage
 * @Description 图片消息
 * @Author maguojun
 * @Date 2019-07-0215:11
 * @Version 1.0
 **/
public class ImageMessage extends BaseMessage {

    // 图片链接
    private String PicUrl;

    public String getPicUrl() {
        return PicUrl;
    }

    public void setPicUrl(String picUrl) {
        PicUrl = picUrl;
    }
}