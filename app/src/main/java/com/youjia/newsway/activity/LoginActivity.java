package com.youjia.newsway.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.youjia.newsway.R;
import com.youjia.newsway.base.BaseActivity;
import com.youjia.newsway.tools.CheckPhone;
import com.youjia.newsway.tools.HttpUtil;
import com.youjia.newsway.tools.HttpUtils;
import com.youjia.newsway.tools.L;
import com.youjia.newsway.tools.MD5Utils;
import com.youjia.newsway.tools.SPUtils;
import com.youjia.newsway.tools.T;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2016/12/20.
 */

public class LoginActivity extends BaseActivity {


    @InjectView(R.id.login_back)
    ImageView imgBack;
    @InjectView(R.id.login_username)
    EditText editUsername;
    @InjectView(R.id.login_password)
    EditText editPassword;
    @InjectView(R.id.login_submit)
    TextView btnSubmit;
    @InjectView(R.id.login_forget)
    TextView textRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        ButterKnife.inject(this);
        findViewById();



    }

    protected void findViewById() {
        editUsername.setText(getIntent().getStringExtra("phone"));
        editPassword.addTextChangedListener(watcher);
    }

    @OnClick({R.id.login_back, R.id.login_submit, R.id.login_forget})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_back:
                finish();
                break;
            case R.id.login_submit:
                login();
                break;
            case R.id.login_forget:
                Toast.makeText(this, "忘记密码", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(LoginActivity.this,ForgetpsdActivity.class);
                startActivity(intent);
                break;
        }
    }


    private TextWatcher watcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (TextUtils.isEmpty(s)){
                btnSubmit.setEnabled(false);
                btnSubmit.setClickable(false);
            }else{
                btnSubmit.setEnabled(true);
                btnSubmit.setClickable(true);
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

    /**
     * 登录
     */
    public void login(){
        String username=editUsername.getText().toString().trim();
        String password=editPassword.getText().toString().trim();
        String ip= HttpUtils.getIP(LoginActivity.this);
        L.d("ip---"+ip+"----password:"+MD5Utils.getMd5Value(password));
        if(CheckPhone.isPhone(username) && !TextUtils.isEmpty(password)){
            Call<String> call= HttpUtil.login(username, MD5Utils.getMd5Value(password),ip);
            call.enqueue(new Callback<String>() {
                int code;
                String info;
                public void onResponse(Call<String> call, Response<String> response) {
                    try {
                        JSONObject object=new JSONObject(response.body());
                        code=object.getInt("code");
                        info=object.getString("info");
                        if (code==1) {
                            JSONObject data=object.getJSONObject("data");
                            JSONObject token=object.getJSONObject("token");
                            SPUtils.put(LoginActivity.this,"token",token.getString("token"));
                            SPUtils.put(LoginActivity.this,"uid",data.getInt("id"));
                            Toast.makeText(LoginActivity.this, info, Toast.LENGTH_SHORT).show();
                            Intent ietent=new Intent(LoginActivity.this,MainActivity.class);
                            startActivity(ietent);
                        }else{
                            Toast.makeText(LoginActivity.this, info, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {

                    T.showShort(LoginActivity.this,"登录网络失败");
                }
            });
        }else {
            Toast.makeText(this, "账号密码错误", Toast.LENGTH_SHORT).show();
        }
    }







}
