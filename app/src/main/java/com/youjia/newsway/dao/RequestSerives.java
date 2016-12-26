package com.youjia.newsway.dao;

import com.youjia.newsway.tools.Urls;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.adapter.rxjava.Result;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by Administrator on 2016/11/29.
 */

public interface RequestSerives {

    //获得热门新闻
    @GET(Urls.hotnews)
    Call<String> getHotNews();
    //获得娱乐新闻
    @GET(Urls.play)
    Call<String> getPlayNews();
    //获得热门新闻
    @GET(Urls.game)
    Call<String> getGameNews();
    /*获取公交路线*/
    @GET("busline")
    Call<String> getSubwayLine(@Query("dtype") String isJson,
                                     @Query("city") String city,
                                     @Query("bus") String lineName,
                                     @Query("key") String key);


    /**
     * 上传头像
     * @param url 服务器地址
     * @param Body //表单的对象
     * @return
     */
    @POST()
    Call<String> upload(
            @Url() String url,
            @Body RequestBody Body);

    /**
     * 注册
     * @param username
     * @param password
     * @return
     */
    @POST(Urls.register)
    Call<String> register(
            @Query("account") String username,
            @Query("password") String password

    );

    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    @POST(Urls.login)
    Call<String> login(
            @Query("account") String username,
            @Query("password") String password,
            @Query("lastLoginIp") String lastIp
    );



    @POST(Urls.resetPsd)
    Call<String> resetPassword(
            @Query("account") String username,
            @Query("password") String password,
            @Query("code") String code
    );



    /**
     * 检查手机号是否已存在
     * @param phone
     * @return
     */
    @GET(Urls.checkAccount)
    Call<String> checkAccount(@Query("account") String phone);

    /**
     * 前台向后台发送4位数验证码
     * @param account
     * @param code
     * @return
     */
    @POST(Urls.sendCode)
    Call<String> sendVerificationCode(
            @Query("account") String account,
            @Query("code") String code
    );


    /**
     * 上传头像
     */
    @Multipart
    @POST(Urls.uoloadImg)
    Call<String> uploadMemberIcon(@Part("images") List<MultipartBody.Part> partList);

    /**
     * 上传头像
     */
    @Multipart
    @POST(Urls.uoloadImg)
    Call<Result<String>> uploadMemberIcon(@Part("image") MultipartBody.Part part, @Part("id") RequestBody token);



    @Multipart
    @POST("upload")
    Call<ResponseBody> upload2(@Part("description") RequestBody description,
                              @Part("img") MultipartBody.Part file);

}
