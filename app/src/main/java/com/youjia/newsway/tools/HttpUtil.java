package com.youjia.newsway.tools;

import com.youjia.newsway.dao.RequestSerives;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.Result;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by Administrator on 2016/12/21.
 */

public class HttpUtil {


    //private static final String HOST = "http://www.update.test";//换成你上传用的服务器地址
    private static Retrofit retrofit;
    private static final int DEFAULT_TIMEOUT = 10;//超时时长，单位：秒



    /**
     * 初始化 Retrofit
     */
    private static Retrofit getApiRetrofit() {
        if (retrofit == null) {
            OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();
            okHttpBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
            retrofit = new Retrofit.Builder()
                    .client(okHttpBuilder.build())
                    .baseUrl(Urls.base)
                    //增加返回值为String的支持
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
        }
        return retrofit;
    }

    /**
     * 创建数据请求服务
     */
        private static RequestSerives getApiService() {
        return HttpUtil.getApiRetrofit().create(RequestSerives.class);
    }

    /**
     * 上传头像
     */
    public static Call<String> uploadMemberIcon(List<MultipartBody.Part> partList) {
        return HttpUtil.getApiService().uploadMemberIcon(partList);
    }

    /**
     * 上传头像
     */
    public static Call<Result<String>> uploadMemberIcon(MultipartBody.Part part, RequestBody token) {
        return HttpUtil.getApiService().uploadMemberIcon(part,token);
    }

    /**
     * 上传头像
     */
    public static Call<ResponseBody> upload2(MultipartBody.Part part, RequestBody token) {
        return HttpUtil.getApiService().upload2(token,part);
    }

    /**
     * 上传头像
     */
    public static Call<String> upload2(String url,RequestBody Body) {
        return HttpUtil.getApiService().upload(url,Body);
    }

    /**
     * 注册
     * @param uname  用户名
     * @param psd   密码
     * @return
     */
    public static Call<String> register(String uname,String psd) {
        return HttpUtil.getApiService().register(uname,psd);
    }

    /**
     * 注册
     * @param uname  用户名
     * @param psd   密码
     * @return
     */
    public static Call<String> login(String uname,String psd,String lastIp) {
        return HttpUtil.getApiService().login(uname,psd,lastIp);
    }

    /**
     * 判断当前手机号时候已被注册
     * @param phone  手机号码
     * @return
     */
    public static Call<String> checkAccount(String phone) {
        return HttpUtil.getApiService().checkAccount(phone);
    }


    /**
     * 发送验证码
     * @param account 手机号码
     * @param code   前台自己生成的4位数验证码
     * @return
     */
    public static Call<String> sendVerificationCode(String account, String code) {
        return HttpUtil.getApiService().sendVerificationCode(account,code);
    }

    /**
     * 重置密码
     * @param account 手机号码
     * @param psd       新密码
     * @param code    前台生成的验证码
     * @return
     */
    public static Call<String> resetPassword(String account, String psd,String code) {
        return HttpUtil.getApiService().resetPassword(account,psd,code);
    }
}
