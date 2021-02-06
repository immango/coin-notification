package com.example.coin.notification.entity;

import lombok.Data;

/**
 * @author mango
 * @description 公告详情
 * @date 2021/1/31 17:34
 */

public class AnnouncementEntity {
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
}
