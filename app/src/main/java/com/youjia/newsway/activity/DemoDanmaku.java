package com.youjia.newsway.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.youjia.newsway.R;

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

public class DemoDanmaku extends AppCompatActivity {
    @InjectView(R.id.danmaku_view)
    DanmakuView danmakuView;
    @InjectView(R.id.edit)
    EditText edit;
    @InjectView(R.id.send)
    Button send;
    @InjectView(R.id.operation_layout)
    LinearLayout operationLayout;
    private boolean showDanmaku;
    /*弹幕的解析器*/
    private DanmakuContext danmakuContext;
    private BaseDanmakuParser parser = new BaseDanmakuParser() {
        @Override
        protected IDanmakus parse() {
            return new Danmakus();
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_danmaku);
        ButterKnife.inject(this);
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

        getWindow().getDecorView().setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener(){

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


    @OnClick({R.id.danmaku_view, R.id.edit, R.id.send})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.danmaku_view:
                if (operationLayout.getVisibility()== View.GONE) {
                    operationLayout.setVisibility(View.VISIBLE);
                }else{
                    operationLayout.setVisibility(View.GONE);
                }
                break;
            case R.id.edit:


                break;
            case R.id.send:
                String content = edit.getText().toString();
                if (!TextUtils.isEmpty(content)) {
                    addDanmaku(content,true);
                    edit.setText("");
                }
                break;
        }
    }


}
