package com.lijie.pay.wechat;

/**
 * @ClassName MenuMessage
 * @Description 事件消息
 * @Author maguojun
 * @Date 2019-07-0215:11
 * @Version 1.0
 **/
public class MenuMessage extends BaseMessage {

    private String EventKey;

    public String getEventKey() {
        return EventKey;
    }

    public void setEventKey(String eventKey) {
        EventKey = eventKey;
    }
}
