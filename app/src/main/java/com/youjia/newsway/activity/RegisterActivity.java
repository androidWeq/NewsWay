package com.youjia.newsway.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
 * Created by Administrator on 2016/12/19.
 */

public class RegisterActivity extends BaseActivity {


    private final static int REGISTER_SUCCESS = 1;//注册成功
    private final static int REGISTER_DEFAULT = 0;//注册成功
    @InjectView(R.id.register_back)
    ImageView imgBack;
    @InjectView(R.id.register_phone)
    EditText editPhone;
    @InjectView(R.id.register_pwd)
    EditText editPwd;
    @InjectView(R.id.register_yanzheng)
    EditText editCode;
    @InjectView(R.id.register_getyanzheng)
    TextView textGetcode;
    @InjectView(R.id.register_submit)
    TextView textSubmit;

    private String number;//电话号码
    private ProgressDialog dialog;
    private String code;//自己生成的验证码

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        ButterKnife.inject(this);
        findViewById();
        SMSSDK.registerEventHandler(ev);
    }

    protected void findViewById() {

        editPhone.setText(getIntent().getStringExtra("phone"));
        editCode.addTextChangedListener(watcher);
    }



    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case REGISTER_SUCCESS:
                    Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                    finish();
                    break;
                case REGISTER_DEFAULT:
                    Toast.makeText(RegisterActivity.this, "注册失败" + msg.arg1, Toast.LENGTH_SHORT).show();
                    break;
            }
            super.handleMessage(msg);

        }
    };


    @OnClick({R.id.register_back, R.id.register_getyanzheng, R.id.register_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register_back:
                finish();
                break;
            case R.id.register_getyanzheng:
                number = editPhone.getText().toString().trim();
                if (CheckPhone.isPhone(number)){
                    getSecurity();
                    //sendCode(number);
                }else {
                    T.showShort(RegisterActivity.this,"请输入正确手机号码");
                    editPhone.setText("");
                }


                break;
            case R.id.register_submit:

                testSecurity();
                break;
        }
    }


    /**
     * 获取验证码
     *
     */
    public void getSecurity() {

        //发送短信，传入国家号和电话---使用SMSSDK核心类之前一定要在MyApplication中初始化，否侧不能使用

            SMSSDK.getVerificationCode("+86", number);
            Toast.makeText(this, "发送成功:" + number, Toast.LENGTH_SHORT).show();


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
                                Call<String> call=HttpUtil.register(username, MD5Utils.getMd5Value(psd));
                                L.d(MD5Utils.getMd5Value(psd));
                                call.enqueue(new Callback<String>() {
                                    @Override
                                    public void onResponse(Call<String> call, Response<String> response) {
                                        HttpModel model=new Gson().fromJson(response.body(),HttpModel.class);
                                        L.d("---code:"+model.getCode()+"----info:"+model.getInfo());
                                        dialog.dismiss();
                                        if(1==model.getCode()){

                                            T.showShort(RegisterActivity.this,"注册成功");
                                            Intent ietent=new Intent(RegisterActivity.this,MainActivity.class);
                                            startActivity(ietent);
                                        }else {
                                            T.showShort(RegisterActivity.this,"注册失败");
                                        }


                                    }

                                    @Override
                                    public void onFailure(Call<String> call, Throwable t) {
                                        T.showShort(RegisterActivity.this,"本地网络请求失败");
                                    }
                                });

                            }
                        });
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                showDailog("验证失败");
                                dialog.dismiss();
                                //     Toast.makeText(MainActivity.this, "验证失败", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {//获取验证码成功
                    Log.e("TAG", "获取验证码成功");
                } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {//返回支持发送验证码的国家列表

                }
            } else {
                ((Throwable) data).printStackTrace();
                T.showShort(RegisterActivity.this,"请获取验证码");
                dialog.dismiss();

            }
        }
    };

    /**
     * 向服务器提交验证码，在监听回调中判断是否通过验证
     *
     */
    public void testSecurity() {
        String security = editCode.getText().toString();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(ev);
    }
}
