package com.youjia.newsway.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;

import com.youjia.newsway.R;
import com.youjia.newsway.adapter.MineSubscriptionaAdapter;
import com.youjia.newsway.base.BaseActivity;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/12/8.
 */

public class MineSubscriptionActivity extends BaseActivity {


    @InjectView(R.id.mine_subscription_back)
    ImageView backImg;
    @InjectView(R.id.mine_subscription_listview)
    ListView listview;
    ArrayList<HashMap<String,String>> datas=new ArrayList<>();
    MineSubscriptionaAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mine_subscription_activity);
        ButterKnife.inject(this);
        initData();
        initListView();
    }

    /**
     *获得数据
     */
    private void initData() {
        HashMap<String,String> hs;
        for(int i=0;i<10;i++) {
            hs=new HashMap<String,String>();
            hs.put("title","地铁咨询"+i);
            hs.put("num",(i*5000+312)+"");
            datas.add(hs);
        }
    }

    /**
     * 绑定listview
     */
    public void initListView(){
        adapter = new MineSubscriptionaAdapter(this, datas);
        listview.setAdapter(adapter);
    }

    protected void findViewById() {

    }

    /**
     * 返回按钮的点击事件
     */
    @OnClick(R.id.mine_subscription_back)
    public void onClick() {
        finish();
    }
}
