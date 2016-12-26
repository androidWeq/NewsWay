package com.youjia.newsway.bean;

/**
 * Created by Wenjian on 2016/12/3.
 */

public class City {
    private int id;
    private int pid;
    private String name;
    private String zipcode;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public City(int id, int pid, String name, String zipcode) {
        this.id = id;
        this.pid = pid;
        this.name = name;
        this.zipcode = zipcode;
    }

    @Override
    public String toString() {
        return name;
    }
}
