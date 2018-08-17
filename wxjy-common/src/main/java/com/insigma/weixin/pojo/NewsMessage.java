package com.insigma.weixin.pojo;

import java.util.List;

/**
 *
 * Description:
 * NewsMessageResp.java Create on 2014-1-3 下午05:19:26
 * @author cupeas@163.com
 * @version 1.0
 * Copyright (c) 2014 Company,Inc. All Rights Reserved.
 */
public class NewsMessage  extends BaseMessage {
    // 图文消息个数，限制为10条以内
    private int ArticleCount;
    // 多条图文消息信息，默认第一个item为大图
    private List<News> Articles;

    public int getArticleCount() {
        return ArticleCount;
    }

    public void setArticleCount(int articleCount) {
        ArticleCount = articleCount;
    }

    public List<News> getArticles() {
        return Articles;
    }

    public void setArticles(List<News> articles) {
        Articles = articles;
    }
}
