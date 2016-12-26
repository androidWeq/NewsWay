package com.youjia.newsway.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.youjia.newsway.R;
import com.youjia.newsway.base.BaseActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/12/23.
 */

public class ChooseLoginActivity extends BaseActivity {
    @InjectView(R.id.choose_login_weixin)
    ImageView imgWeixin;
    @InjectView(R.id.choose_login_shouji)
    ImageView imgPhone;
    @InjectView(R.id.choose_login_back)
    TextView textBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_login);
        ButterKnife.inject(this);
    }

    @Override
    protected void findViewById() {

    }

    @OnClick({R.id.choose_login_weixin, R.id.choose_login_shouji, R.id.choose_login_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.choose_login_weixin:

                break;
            case R.id.choose_login_shouji:
                //跳转到手机登录界面
                Intent intent=new Intent(ChooseLoginActivity.this,JudgeLoginActivity.class);
                startActivity(intent);
                break;
            case R.id.choose_login_back:
                finish();
                break;
        }
    }
}
