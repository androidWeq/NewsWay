package com.youjia.newsway.bean;

/**
 * Created by Wenjian on 2016/12/3.
 */

public class Province {


    private int id;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Province(int id, String name ) {
        this.name = name;
        this.id = id;
    }

    @Override
    public String toString() {
        return name ;
    }
}
