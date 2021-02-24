package com.ldh.coin.notification.entity;

import java.io.Serializable;

/**
 * @author mango
 * @description 公告详情
 * @date 2021/1/31 17:34
 */

public class AnnouncementEntity implements Serializable {

    private static final long serialVersionUID = 7488827979720028421L;
    private String title;
    private String url;
    private String date;
    private String detail;
    private boolean coin;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isCoin() {
        return coin;
    }

    public void setCoin(boolean coin) {
        this.coin = coin;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    @Override
    public String toString() {
        return "AnnouncementEntity{" +
                "title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", date='" + date + '\'' +
                ", detail='" + detail + '\'' +
                ", coin=" + coin +
                '}';
    }
}
