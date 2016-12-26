package com.youjia.newsway.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.youjia.newsway.R;
import com.youjia.newsway.base.BaseActivity;
import com.youjia.newsway.tools.SPUtils;
import com.zcw.togglebutton.ToggleButton;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/12/9.
 */

public class IntegralSettingActiviy extends BaseActivity {
    @InjectView(R.id.integral_setting_back)
    ImageView backImg;
    @InjectView(R.id.integral_setting_textsize)
    TextView changTextSize;
    @InjectView(R.id.integral_setting_openDanmaku)
    ToggleButton openDanmaku;
    @InjectView(R.id.integral_setting_wifiImg)
    ToggleButton wifiImg;
    @InjectView(R.id.integral_setting_garbageSize)
    TextView garbageSize;
    @InjectView(R.id.integral_setting_clean)
    RelativeLayout cleanGarbage;
    @InjectView(R.id.integral_setting_aboutUs)
    RelativeLayout aboutUs;
    @InjectView(R.id.integral_setting_tuichu)
    TextView tuichu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.integral_setting_activity);
        ButterKnife.inject(this);
    }

    @Override
    protected void findViewById() {

    }

    @OnClick({R.id.integral_setting_back, R.id.integral_setting_textsize, R.id.integral_setting_clean, R.id.integral_setting_aboutUs, R.id.integral_setting_tuichu})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.integral_setting_back:
                finish();
                break;
            case R.id.integral_setting_textsize:
                break;
            case R.id.integral_setting_clean:
                break;
            case R.id.integral_setting_aboutUs:
                break;
            case R.id.integral_setting_tuichu:
                SPUtils.remove(IntegralSettingActiviy.this,"uid");
                Toast.makeText(this, "退出成功----"+SPUtils.contains(IntegralSettingActiviy.this,"uid"), Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
