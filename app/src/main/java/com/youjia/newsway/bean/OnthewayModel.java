package com.youjia.newsway.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/12/3.
 */

public class OnthewayModel {
    String userImg;//用户图片路径
    String userName;//用户昵称
    String userGrade;//用户等级
    String location;//定位
    String time;//时间
    String recommend;//推荐
    String phone;//电话

    List<String> imagePaths;//发表的图片路径
    boolean isWantGo;//是否赞
    int wantGoNum;//点赞的次数
    int comment;//评价
    int share;//分享

    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserGrade() {
        return userGrade;
    }

    public void setUserGrade(String userGrade) {
        this.userGrade = userGrade;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRecommend() {
        return recommend;
    }

    public void setRecommend(String recommend) {
        this.recommend = recommend;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<String> getImagePaths() {
        return imagePaths;
    }

    public void setImagePaths(List<String> imagePaths) {
        this.imagePaths = imagePaths;
    }


    public boolean isWantGo() {
        return isWantGo;
    }

    public void setWantGo(boolean wantGo) {
        isWantGo = wantGo;
    }

    public int getWantGoNum() {
        return wantGoNum;
    }

    public void setWantGoNum(int wantGoNum) {
        this.wantGoNum = wantGoNum;
    }

    public int getComment() {
        return comment;
    }

    public void setComment(int comment) {
        this.comment = comment;
    }

    public int getShare() {
        return share;
    }

    public void setShare(int share) {
        this.share = share;
    }
}
