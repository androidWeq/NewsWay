package com.youjia.newsway.activity;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.youjia.newsway.R;
import com.youjia.newsway.adapter.IntegralMallAdapter;
import com.youjia.newsway.base.BaseActivity;
import com.youjia.newsway.tools.GlideImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Administrator on 2016/12/9.
 */

public class IntegralMallActivity extends BaseActivity implements View.OnClickListener{


    @InjectView(R.id.integral_mall_recycleview)
    RecyclerView recycleview;
    IntegralMallAdapter adapter;
    ArrayList<HashMap<String, String>> datas = new ArrayList<>();
    View  head;//头布局
    Banner banner;
    private TextView toExchangeRecord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.integral_mall_activity);
        ButterKnife.inject(this);
        findViewById();
        initBanner();
        initRecycleView();


    }

    /**
     * 初始化recycleView
     */
    private void initRecycleView() {
        for (int i = 0; i < 10; i++) {
            HashMap<String, String> hs = new HashMap<>();
            hs.put("title", "权志龙兑换" + i);
            hs.put("jifen", i * 500 + "");
            datas.add(hs);
        }

        //初始化布局管理器
        final GridLayoutManager lm = new GridLayoutManager(this,2);

        //设置布局管理器
        recycleview.setLayoutManager(lm);
        adapter = new IntegralMallAdapter(datas, this);
        adapter.setmHeaderView(head);
        //绘制item间的分割线，
        recycleview.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                int postion = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewAdapterPosition();
                if (adapter.isHeader(postion)) {
                    outRect.set(0, 0, 0, -50);
                } else {
                    outRect.set(20, 20, 20, 20);
                }
            }
        });
        recycleview.setAdapter(adapter);

    }

    /**
     * 初始化Banner
     */
    private void initBanner() {
        String url0 = "http://childmusic.qiniudn.com/huandeng/1.png";
        String url1 = "http://childmusic.qiniudn.com/huandeng/2.png";
        String url2 = "http://childmusic.qiniudn.com/huandeng/3.png";
        String url3 = "http://childmusic.qiniudn.com/huandeng/4.png";
        String url4 = "http://childmusic.qiniudn.com/huandeng/5.png";
        String url5 = "http://childmusic.qiniudn.com/huandeng/6.png";
        List<String> images = new ArrayList<String>();
        images.add(url0);
        images.add(url1);
        images.add(url2);
        images.add(url3);
        images.add(url4);
        images.add(url5);
        //轮播图的样式   设置为指示器+标题
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置指示器的位置
        banner.setIndicatorGravity(BannerConfig.CENTER);
        //设置图片
        banner.setImages(images).setImageLoader(new GlideImageLoader()).start();
    }


    protected void findViewById() {
        head= LayoutInflater.from(this).inflate(R.layout.integral_mall_recycleview_head,null);
        toExchangeRecord= (TextView) head.findViewById(R.id.integral_mall_head_toexchangeRecord);
        toExchangeRecord.setOnClickListener(this);
        banner= (Banner) head.findViewById(R.id.integral_mall_banner);
    }


    @Override
    public void onClick(View v) {
        Intent intent=null;
        switch (v.getId()){
            case R.id.integral_mall_head_toexchangeRecord:
                intent=new Intent(IntegralMallActivity.this,ExchangeRecordActivity.class);
                break;
        }

        if (intent != null) {
            startActivity(intent);
        }
    }
}
