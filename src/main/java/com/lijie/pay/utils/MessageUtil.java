
package com.lijie.pay.utils;


import com.lijie.pay.wechat.*;
import com.thoughtworks.xstream.XStream;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Package: com.jg.projects.jp.taxautomation.model.wechat.util
 * @author: maguojun
 * @date: 2019-07-0210:21
 */
public class MessageUtil {
    public static final String MESSAGE_TEXT = "text";
    public static final String MESSAGE_IMAGE = "image";
    public static final String MESSAGE_VOICE = "voice";
    public static final String MESSAGE_VIDEO = "video";
    public static final String MESSAGE_LINK = "link";
    public static final String MESSAGE_LOCATION = "location";
    public static final String MESSAGE_EVENT = "event";

    public static final String EVENT_SUB = "subscribe";
    public static final String EVENT_UNSUB = "unsubscribe";
    public static final String EVENT_CLICK = "CLICK";
    public static final String EVENT_VIEW = "VIEW";

    /**
     * 将java对象转换为xml
     *
     * @param msg
     * @return
     */
    public static String baseMessageToXml(BaseMessage msg) {
        String result = "";
        if (msg != null) {
            XStream xs = XmlUtil.XStreamFactroy.init(true);
            xs.alias("xml", msg.getClass());
            result = xs.toXML(msg);
        }
        return result;
    }

    /**
     * 文本消息
     *
     * @param text
     * @return
     */
    public static String textMessageToXml(TextMessage text) {
        String result = "";
        if (text != null) {
            XStream xs = XmlUtil.XStreamFactroy.init(true);
            xs.alias("xml", TextMessage.class);
            result = xs.toXML(text);
        }
        return result;
    }

    /**
     * 图片消息
     *
     * @param image
     * @return
     */
    public static String imageMessageToXml(ImageMessage image) {
        String result = "";
        if (image != null) {
            XStream xs = XmlUtil.XStreamFactroy.init(true);
            xs.alias("xml", ImageMessage.class);
            result = xs.toXML(image);
        }
        return result;
    }

    /**
     * 图文消息对象转换成xml
     * <p>
     * MessageUtil.java
     *
     * @param message
     * @return
     */
    public static String newsMessagegToXml(NewsMessage message) {
        String result = "";
        if (message != null) {
            XStream xs = XmlUtil.XStreamFactroy.init(true);
            xs.alias("xml", NewsMessage.class);
            xs.alias("item", new Article().getClass());
            result = xs.toXML(message);
        }
        return result;
    }

    /**
     * xml转为map
     *
     * @param request
     * @return
     * @throws DocumentException
     * @throws IOException
     */
    public static Map<String, String> xmlToMap(HttpServletRequest request) throws DocumentException, IOException {
        Map<String, String> map = new HashMap<String, String>();

        SAXReader reader = new SAXReader();

        InputStream ins = request.getInputStream();
        Document doc = reader.read(ins);

        Element root = doc.getRootElement();
        List<Element> list = root.elements();
        for (Element e : list) {
            map.put(e.getName(), e.getText());
        }
        ins.close();
        return map;
    }

    public static String initText(String toUserName, String fromUserName, String content) {
        TextMessage text = new TextMessage();
        text.setFromUserName(toUserName);
        text.setToUserName(fromUserName);
        text.setMsgType(MESSAGE_TEXT);
        text.setCreateTime(new Date().getTime());
        text.setContent(content);
        return textMessageToXml(text);
    }

    public static String menuText() {
        StringBuffer sb = new StringBuffer();
        sb.append("      欢迎来到J&P大家庭，\n");
        sb.append("     J&P公众平台将为您提供优质的服务！\n");
//		sb.append("该公众号已实现以下功能：\n");
//		sb.append("回复“天气”、“翻译” 将有该功能的介绍与使用，\n");
//		sb.append("如您在使用该公众有任何宝贵意见，欢迎反馈！\n\n");
//		sb.append("反馈邮箱：zhenqicai@sohu.com");
        return sb.toString();
    }
}
