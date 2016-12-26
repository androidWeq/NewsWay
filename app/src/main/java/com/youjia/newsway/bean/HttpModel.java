package com.youjia.newsway.bean;

/**
 * Created by Administrator on 2016/12/22.
 */

public class HttpModel {
    int code;
    String info;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public HttpModel(int code, String info) {
        this.code = code;
        this.info = info;
    }
}
