package com.youjia.newsway.bean;

/**
 * Created by Wenjian on 2016/12/3.
 */

public class District {
    private int id;
    private int cityid;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCityid() {
        return cityid;
    }

    public void setCityid(int cityid) {
        this.cityid = cityid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public District(int id, int cityid, String name) {
        this.id = id;
        this.cityid = cityid;
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
