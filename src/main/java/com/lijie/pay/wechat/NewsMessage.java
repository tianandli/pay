package com.lijie.pay.wechat;

import java.util.List;

/**
 * @ClassName NewsMessage
 * @Description 文本消息
 * @Author maguojun
 * @Date 2019-07-0215:11
 * @Version 1.0
 **/
public class NewsMessage extends BaseMessage {

    // 图文消息个数，限制为10条以内
    private int ArticleCount;
    // 多条图文消息信息，默认第一个item为大图
    private List<Article> Articles;

    public int getArticleCount() {
        return ArticleCount;
    }

    public void setArticleCount(int articleCount) {
        ArticleCount = articleCount;
    }

    public List<Article> getArticles() {
        return Articles;
    }

    public void setArticles(List<Article> articles) {
        Articles = articles;
    }
}