package com.lijie.pay.wechat;

/**
 * @ClassName TextMessage
 * @Description 文本消息
 * @Author maguojun
 * @Date 2019-07-0215:11
 * @Version 1.0
 **/
public class TextMessage extends BaseMessage {

    // 消息内容
    private String Content;

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

}