package com.youjia.newsway.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.youjia.newsway.R;
import com.youjia.newsway.base.BaseActivity;
import com.youjia.newsway.bean.HttpModel;
import com.youjia.newsway.tools.CheckPhone;
import com.youjia.newsway.tools.HttpUtil;
import com.youjia.newsway.tools.L;
import com.youjia.newsway.tools.T;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 注册前判断账号是否已经存在
 */

public class JudgeLoginActivity extends BaseActivity {

    @InjectView(R.id.judge_login_back)
    ImageView imgBack;
    @InjectView(R.id.judge_login_phone)
    EditText editPhone;
    @InjectView(R.id.judge_login_submit)
    TextView textSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.judge_login);
        ButterKnife.inject(this);
        findViewById();
    }

    @Override
    protected void findViewById() {
        editPhone.addTextChangedListener(watcher);
    }

    @OnClick({R.id.judge_login_back, R.id.judge_login_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.judge_login_back:
                finish();
                break;
            case R.id.judge_login_submit:
                String number=editPhone.getText().toString().trim();
                if (CheckPhone.isPhone(number)) {
                    JudgeIntent(number);
                }else {
                    T.showShort(JudgeLoginActivity.this,"请输入正确手机号");
                    editPhone.setText("");
                }

                break;
        }
    }



    public void JudgeIntent(String number){
        Call<String> call= HttpUtil.checkAccount(number);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                HttpModel model=new Gson().fromJson(response.body(),HttpModel.class);
                L.d(model.getCode()+"请求成功-----code");
                Intent intent;
                if (1==model.getCode()) {
                    intent=new Intent(JudgeLoginActivity.this,LoginActivity.class);
                }else {
                    intent=new Intent(JudgeLoginActivity.this,RegisterActivity.class);
                }
                intent.putExtra("phone",editPhone.getText().toString().trim());
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                L.d("----判断网络失败");
            }
        });
    }

    private TextWatcher watcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.length()==11){
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
