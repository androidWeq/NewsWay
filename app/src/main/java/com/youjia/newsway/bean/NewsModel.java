package com.youjia.newsway.bean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/11/30.
 */
public class NewsModel {


    int pageIndex;
    String typeId;
    /**
     *唯一性：新闻的标识
     */
    int id;

    /**
     *图片
     */
    String img;   //图片
    /**
     *标题
     */
    String title;
    /**
     *描述
     */
    String digest; //描述
    /**
     *图片数组，三张图的cell需要用到这个字段
     */
    ArrayList<String> imgextra;

    /**
     *详情的URL
     */
    String link;

    /**
     *判断是不是longTableViewCell 如果是imgType = 1
     */

    int imgType;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public ArrayList<String> getImgextra() {
        return imgextra;
    }

    public void setImgextra(ArrayList<String> imgextra) {
        this.imgextra = imgextra;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getImgType() {
        return imgType;
    }

    public void setImgType(int imgType) {
        this.imgType = imgType;
    }
}
