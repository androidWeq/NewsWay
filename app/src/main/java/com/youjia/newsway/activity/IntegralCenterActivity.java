package com.youjia.newsway.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.youjia.newsway.R;
import com.youjia.newsway.adapter.IntegralCenterAdapter;
import com.youjia.newsway.base.BaseActivity;
import com.youjia.newsway.bean.IntegralCenterModel;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/12/8.
 */

public class IntegralCenterActivity extends BaseActivity {
    @InjectView(R.id.integral_center_back)
    ImageView backImg;
    @InjectView(R.id.integral_center_center)
    TextView toMall;
    @InjectView(R.id.integral_center_jifen)
    TextView integral;
    @InjectView(R.id.integral_center_listview)
    ListView listview;
    ArrayList<IntegralCenterModel> datas;//listview的数据元
    IntegralCenterAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.integral_center_activity);
        ButterKnife.inject(this);
        initData();
        initListView();
    }

    /**
     * 初始化listview
     */
    private void initListView() {
        adapter=new IntegralCenterAdapter(this,datas);
        listview.setAdapter(adapter);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        datas=new ArrayList<>();
        for (int i=0;i<9;i++) {
            IntegralCenterModel model=new IntegralCenterModel();
            if (i <= 5) {
                model.setStyle("日常任务");
                model.setTitle("这是数据"+i);
                model.setReward(5);
                model.setCurrentProgress(i);
                model.setTarget(5);
            }else if (i>5 && i<=7){
                model.setStyle("进阶任务");
                model.setTitle("这是数据"+i);
                model.setReward(100);
                model.setCurrentProgress(i);
                model.setTarget(10);
            }else{
                model.setStyle("竞猜任务");
                model.setTitle("这是数据"+i);
                model.setReward(100);
                model.setCurrentProgress(i);
                model.setTarget(9);
            }
            datas.add(model);
        }
    }

    @Override
    protected void findViewById() {

    }

    @OnClick({R.id.integral_center_back, R.id.integral_center_center})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.integral_center_back:
                finish();
                break;
            case R.id.integral_center_center:
                break;
        }
    }
}
