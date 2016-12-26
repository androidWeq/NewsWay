package com.youjia.newsway.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.youjia.newsway.R;
import com.youjia.newsway.adapter.ExchangeRecordAdapter;
import com.youjia.newsway.base.BaseActivity;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/12/20.
 */

public class ExchangeRecordActivity extends BaseActivity {

    @InjectView(R.id.exchange_record_back)
    ImageView imgBack;
    @InjectView(R.id.exchange_record_title)
    TextView textTitle;
    @InjectView(R.id.exchange_record_listview)
    ListView listview;

    ArrayList<HashMap<String,String>> datas=new ArrayList<>();
    ExchangeRecordAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exchange_record);
        ButterKnife.inject(this);


        for(int i=0;i<20;i++){
            HashMap<String,String> hs=new HashMap<>();
            hs.put("title",i+"");
            datas.add(hs);
        }

        adapter=new ExchangeRecordAdapter(this,datas);
        listview.setAdapter(adapter);

    }

    @Override
    protected void findViewById() {

    }

    @OnClick(R.id.exchange_record_back)
    public void onClick() {
        finish();
    }
}
