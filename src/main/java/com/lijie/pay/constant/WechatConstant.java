/**
 * @ClassName: WechatConstant
 * @Description: TODO
 * @author: cc
 * @date: 2018年8月18日 上午11:08:10
 */
package com.lijie.pay.constant;

/**
 * 微信验证信息常量 （读取wechat.properties文件）
 *
 * @Package: cc.feefox.wechat.common.constant
 * @author: cc
 * @date: 2018年8月18日 上午11:08:10
 */
//@Component
public class WechatConstant {

    /**
     * 访问token地址
     */
    public final static String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";//access_token的获取
    // 图文内的图片地址获取接口地址
    public static final String UPLOADIMAGEURL = "https://api.weixin.qq.com/cgi-bin/media/uploadimg?access_token=ACCESS_TOKEN";
    // 图文封面图片获取接口地址
    public static final String POSTIMAGEMEDIAURL = "http://file.api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=image";
    // 图文素材上传接口地址
    public static final String POSTNEWSURL = "https://api.weixin.qq.com/cgi-bin/media/uploadnews?access_token=ACCESS_TOKEN";
    // 群发接口地址
    public static final String SENDTOALLURL = "https://api.weixin.qq.com/cgi-bin/message/mass/sendall?access_token=ACCESS_TOKEN";
    // 预览接口地址
    public static final String SENDTOPREVIEWURL = "https://api.weixin.qq.com/cgi-bin/message/mass/preview?access_token=ACCESS_TOKEN";
    // 按照openid进行群发消息(OpenID最少2个，最多10000个 10000个)
    public static final String SEND_MASS_MESSAGE_URL = "https://api.weixin.qq.com/cgi-bin/message/mass/send";
    // 发送模板消息地址
    public static final String SEND_TEMPLATE_MESSAGE_URL = "https://api.weixin.qq.com/cgi-bin/message/template/send";
    //发送模板消息
    public static final String FIRST = "first";
    public static final String FIRST_CONTENT = "您好，您有一条重要消息需要登录J&P自动计税系统查看处理";
    public static final String KEYWORD1 = "keyword1";
    public static final String KEYWORD1_CONTENT = "J&P UK ACCOUNTANTS";
    public static final String KEYWORD2 = "keyword2";
    public static final String KEYWORD3 = "keyword3";
    public static final String REMARK = "remark";
    public static final String PLUS = "+";
    public static final String PAUSE = "、";
//    public static String APPID = "wx1bf11634d7944600";
//    public static String APPSECRET = "bc119b11b4f41553543cd7bcbcb6c429";
//    public static String TOKEN = "weixin";


    public static String APPID = "wx434cdf890d37c36f";
    public static String APPSECRET = "4e6f3e4b47e2c8f5bc89c86c36e88b7c";
    public static String TOKEN = "jpweixin";

    /*
     * 通过线程定时获取ACCESS_TOKEN  见 TokenThread和GetAccessToken这俩个类
     */
    public static String ACCESS_TOKEN;
}
