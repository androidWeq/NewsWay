package com.youjia.newsway.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.youjia.newsway.R;
import com.youjia.newsway.adapter.CommentsAdapter;
import com.youjia.newsway.base.BaseActivity;
import com.youjia.newsway.review.DividerItemDecoration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import master.flame.danmaku.controller.DrawHandler;
import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.DanmakuTimer;
import master.flame.danmaku.danmaku.model.IDanmakus;
import master.flame.danmaku.danmaku.model.android.DanmakuContext;
import master.flame.danmaku.danmaku.model.android.Danmakus;
import master.flame.danmaku.danmaku.parser.BaseDanmakuParser;
import master.flame.danmaku.ui.widget.DanmakuView;

/**
 * Created by Administrator on 2016/12/6.
 */

public class ConmmentsActivity extends BaseActivity {

    @InjectView(R.id.back_topmodel_back)
    ImageView goBack;
    @InjectView(R.id.back_topmodel_weather)
    ImageView toWeather;
    @InjectView(R.id.comments_activity_recylceview)
    RecyclerView recycleview;
    @InjectView(R.id.ontheway_relistview_item_checkbox)
    CheckBox wantgoCheckbox;
    @InjectView(R.id.ontheway_relistview_item_comment)
    TextView conmentText;
    @InjectView(R.id.ontheway_relistview_item_share)
    TextView shareText;
    @InjectView(R.id.comments_activity_danmaku)
    DanmakuView danmakuView;
    private boolean showDanmaku;
    /*弹幕的解析器*/
    private DanmakuContext danmakuContext;
    private BaseDanmakuParser parser = new BaseDanmakuParser() {
        @Override
        protected IDanmakus parse() {
            return new Danmakus();
        }
    };
    ArrayList<HashMap<String, String>> datas;//评论数据源
    CommentsAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comments_activity);
        ButterKnife.inject(this);
        initDanmaku();
        initRecycleView();
    }

    /**
     * 初始化recycleview
     */
    private void initRecycleView() {
        if (datas==null) {
            datas=new ArrayList<HashMap<String, String>>();
        }
        HashMap<String, String> hs;
        for (int i=0;i<5;i++) {
            hs=new HashMap<String,String>();
            hs.put("username","雨过天晴"+i);
            hs.put("time","3分钟之前");
            hs.put("comment","很好,"+i);
            hs.put("isgood", (i + 5) + "");
            datas.add(hs);
        }
        adapter = new CommentsAdapter(datas, ConmmentsActivity.this);
        LinearLayoutManager layoutManager=new LinearLayoutManager(ConmmentsActivity.this);
        recycleview.setLayoutManager(layoutManager);
        //设置分割线
        recycleview.addItemDecoration(new DividerItemDecoration(ConmmentsActivity.this,DividerItemDecoration.VERTICAL_LIST));
        recycleview.setAdapter(adapter);
        setFooterView(recycleview);
        adapter.setClickListener(new CommentsAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Toast.makeText(ConmmentsActivity.this, position+ "", Toast.LENGTH_SHORT).show();
            }
        });

    }

    /**
     * 初始化弹幕
     */
    private void initDanmaku() {
        //enableDanmakuDrawingCache()方法来提升绘制效率
        danmakuView.enableDanmakuDrawingCache(true);
        danmakuView.setCallback(new DrawHandler.Callback() {
            @Override
            public void prepared() {
                showDanmaku = true;
                danmakuView.start();
                generateSomeDanmaku();
            }

            @Override
            public void updateTimer(DanmakuTimer timer) {

            }

            @Override
            public void danmakuShown(BaseDanmaku danmaku) {

            }

            @Override
            public void drawingFinished() {

            }
        });
        //DanmakuContext可以用于对弹幕的各种全局配置进行设定，如设置字体、设置最大显示行数等
        danmakuContext = DanmakuContext.create();
        //anmakuView的prepare()方法来进行准备，
        // 准备完成后会自动调用刚才设置的回调函数中的prepared()方法，
        // 然后我们在这里再调用DanmakuView的start()方法，
        // 这样DanmakuView就可以开始正常工作了。
        danmakuView.prepare(parser, danmakuContext);

        getWindow().getDecorView().setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {

            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                if (visibility == View.SYSTEM_UI_FLAG_VISIBLE) {
                    onWindowFocusChanged(true);
                }
            }
        });
    }

    /**
     * 随机生成一些弹幕内容以供测试
     */
    private void generateSomeDanmaku() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (showDanmaku) {
                    int time = new Random().nextInt(300);
                    String content = "" + time + time;
                    addDanmaku(content, false);
                    try {
                        Thread.sleep(time);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    /**
     * 向弹幕View中添加一条弹幕
     *
     * @param content    弹幕的具体内容
     * @param withBorder 弹幕是否有边框
     */
    private void addDanmaku(String content, boolean withBorder) {
        //TYPE_SCROLL_RL表示这是一条从右向左滚动的弹幕
        BaseDanmaku danmaku = danmakuContext.mDanmakuFactory.createDanmaku(BaseDanmaku.TYPE_SCROLL_RL);
        danmaku.text = content;
        danmaku.padding = 5;
        danmaku.textSize = sp2px(20);
        danmaku.textColor = Color.WHITE;
        danmaku.setTime(danmakuView.getCurrentTime());
        if (withBorder) {
            danmaku.borderColor = Color.GREEN;
        }
        danmakuView.addDanmaku(danmaku);
    }

    /**
     * sp转px的方法。
     */
    private float sp2px(int spValue) {
        final float fontScale = getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
    /**
     * 设置教布局
     * @param view
     */
    private void setFooterView(RecyclerView view){
        View footer = LayoutInflater.from(this).inflate(R.layout.demo_footer, view, false);
        adapter.setmFooterView(footer);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (danmakuView != null && danmakuView.isPrepared()) {
            danmakuView.pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (danmakuView != null && danmakuView.isPrepared() && danmakuView.isPaused()) {
            danmakuView.resume();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        showDanmaku = false;
        if (danmakuView != null) {
            danmakuView.release();
            danmakuView = null;
        }
    }


    protected void findViewById() {

    }

    @OnClick({R.id.back_topmodel_back, R.id.back_topmodel_weather, R.id.ontheway_relistview_item_comment, R.id.ontheway_relistview_item_share})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_topmodel_back:
                break;
            case R.id.back_topmodel_weather:
                break;
            case R.id.ontheway_relistview_item_comment:
                break;
            case R.id.ontheway_relistview_item_share:
                break;
        }
    }
}
