package com.youjia.newsway.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.youjia.newsway.R;
import com.youjia.newsway.adapter.ExchangeDetailAdapter;
import com.youjia.newsway.base.BaseActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/12/20.
 */

public class ExchangeDetailActivity extends BaseActivity {

    @InjectView(R.id.exchange_detail_back)
    ImageView imgBack;
    @InjectView(R.id.exchange_detail_title)
    TextView textTitle;
    @InjectView(R.id.exchange_detail_listview)
    ListView listview;
    ExchangeDetailAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exchange_details);
        ButterKnife.inject(this);
        adapter=new ExchangeDetailAdapter();
        listview.setAdapter(adapter);
    }


    protected void findViewById() {

    }

    @OnClick(R.id.exchange_detail_back)
    public void onClick() {
        finish();
    }
}
