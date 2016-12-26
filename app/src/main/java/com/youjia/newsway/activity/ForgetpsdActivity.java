package com.youjia.newsway.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.youjia.newsway.R;
import com.youjia.newsway.base.BaseActivity;
import com.youjia.newsway.bean.HttpModel;
import com.youjia.newsway.tools.CheckPhone;
import com.youjia.newsway.tools.HttpUtil;
import com.youjia.newsway.tools.L;
import com.youjia.newsway.tools.MD5Utils;
import com.youjia.newsway.tools.T;

import java.util.HashMap;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2016/12/24.
 */

public class ForgetpsdActivity extends BaseActivity {

    @InjectView(R.id.forget_back)
    ImageView imgBack;
    @InjectView(R.id.forget_phone)
    EditText editPhone;
    @InjectView(R.id.forget_pwd)
    EditText editPwd;
    @InjectView(R.id.forget_yanzheng)
    EditText editYanzheng;
    @InjectView(R.id.forget_getyanzheng)
    TextView textGetyanzheng;
    @InjectView(R.id.forget_submit)
    TextView textSubmit;
    private String number=null;//电话号码
    private String code; //自己生成的验证码
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgetpsd);
        ButterKnife.inject(this);
        SMSSDK.registerEventHandler(ev);
        findViewById();
    }

    @Override
    protected void findViewById() {
        editYanzheng.addTextChangedListener(watcher);
    }

    @OnClick({R.id.forget_back, R.id.forget_getyanzheng, R.id.forget_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.forget_back:
                finish();
                break;
            case R.id.forget_getyanzheng:
                number = editPhone.getText().toString().trim();
                if (CheckPhone.isPhone(number)){
                    getSecurity();
                    sendCode(number);
                }else {
                    T.showShort(ForgetpsdActivity.this,"请输入正确手机号码");
                    editPhone.setText("");
                }
                break;
            case R.id.forget_submit:
                if (!TextUtils.isEmpty(editPwd.getText().toString().trim())) {
                    testSecurity();
                }else {
                    Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }


    public void sendCode(String number){
        code=(int)(Math.random()*9000+1000)+"";
        Call<String> call= HttpUtil.sendVerificationCode(number,code);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                L.d("向后台发送成功-----"+code);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }


    /**
     * 获取验证码
     *
     */
    public void getSecurity() {

        //发送短信，传入国家号和电话---使用SMSSDK核心类之前一定要在MyApplication中初始化，否侧不能使用

        SMSSDK.getVerificationCode("+86", number);
        //Toast.makeText(this, "发送成功:" + number, Toast.LENGTH_SHORT).show();


    }



    /**
     * 短信验证的回调监听
     */
    private EventHandler ev = new EventHandler() {
        @Override
        public void afterEvent(int event, int result, Object data) {
            if (result == SMSSDK.RESULT_COMPLETE) { //回调完成

                //提交验证码成功,如果验证成功会在data里返回数据。data数据类型为HashMap<number,code>
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                    Log.e("TAG", "提交验证码成功" + data.toString());
                    HashMap<String, Object> mData = (HashMap<String, Object>) data;
                    String country = (String) mData.get("country");//返回的国家编号
                    final String username = (String) mData.get("phone");//返回用户注册的手机号
                    final String psd=editPwd.getText().toString().trim();//获取密码

                    Log.e("TAG", country + "====" + username);

                    if (username.equals(number)) {
                        runOnUiThread(new Runnable() {//更改ui的操作要放在主线程，实际可以发送hander
                            @Override
                            public void run() {
                                Call<String> call=HttpUtil.resetPassword(username, MD5Utils.getMd5Value(psd),code);
                                L.d(MD5Utils.getMd5Value(psd));
                                call.enqueue(new Callback<String>() {
                                    @Override
                                    public void onResponse(Call<String> call, Response<String> response) {

                                        HttpModel model=new Gson().fromJson(response.body(),HttpModel.class);
                                        L.d("---code:"+model.getCode()+"----info:"+model.getInfo());
                                        if(1==model.getCode()){
                                            T.showShort(ForgetpsdActivity.this,"修改成功");
                                            finish();
                                        }else {
                                            T.showShort(ForgetpsdActivity.this,"修改失败");
                                        }
                                        dialog.dismiss();

                                    }

                                    @Override
                                    public void onFailure(Call<String> call, Throwable t) {
                                        T.showShort(ForgetpsdActivity.this,"本地网络请求失败");
                                    }
                                });

                            }
                        });
                    } else {
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                showDailog("验证失败");
//                                dialog.dismiss();
//                                //     Toast.makeText(MainActivity.this, "验证失败", Toast.LENGTH_SHORT).show();
//                            }
//                       });
                        Toast.makeText(ForgetpsdActivity.this, "验证码错误", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();

                    }

                } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {//获取验证码成功
                    Log.e("TAG", "获取验证码成功");
                } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {//返回支持发送验证码的国家列表

                }
            } else {
                ((Throwable) data).printStackTrace();
                T.showShort(ForgetpsdActivity.this,"请获取验证码");
                dialog.dismiss();

            }
        }
    };

    /**
     * 向服务器提交验证码，在监听回调中判断是否通过验证
     *
     */
    public void testSecurity() {
        String security = editYanzheng.getText().toString();
        if (!TextUtils.isEmpty(security) && number!=null) {
            dialog = ProgressDialog.show(this, null, "正在验证...", false, true);
            //提交短信验证码
            SMSSDK.submitVerificationCode("+86", number, security);//国家号，手机号码，验证码
            Toast.makeText(this, "提交了注册信息:" + number, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "请先获取验证码", Toast.LENGTH_SHORT).show();
        }
    }


    private void showDailog(String text) {
        new AlertDialog.Builder(this)
                .setTitle(text)
                .setPositiveButton("确定", null)
                .show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(ev);
    }

    private TextWatcher watcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.length()==4){
                textSubmit.setEnabled(true);
                textSubmit.setClickable(true);
            }else {
                textSubmit.setEnabled(false);
                textSubmit.setClickable(false);
            }

        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
            // TODO Auto-generated method stub

        }

        @Override
        public void afterTextChanged(Editable s) {
            // TODO Auto-generated method stub

        }
    };
}
