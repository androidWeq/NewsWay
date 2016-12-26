package com.youjia.newsway.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.youjia.newsway.R;
import com.youjia.newsway.tools.GlideImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.zcw.togglebutton.ToggleButton;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/29.
 */

public class DemoBanner extends AppCompatActivity {

    ToggleButton toggleBtn;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_banner);
        Banner banner = (Banner) findViewById(R.id.banner);


        String t1="第一条信息";
        String t2="第er条信息";
        String t3="第san条信息";
        String t4="第si条信息";
        String t5="第wu条信息";
        String t6="第liu条信息";



        //String[] titles= new String[] {t1,t2,t3,t4,t5};
        List<String> titles=new ArrayList<String>();
        titles.add(t1);
        titles.add(t2);
        titles.add(t3);
        titles.add(t4);
        titles.add(t5);
        titles.add(t6);

        String url0 = "http://childmusic.qiniudn.com/huandeng/1.png";
        String url1 = "http://childmusic.qiniudn.com/huandeng/2.png";
        String url2 = "http://childmusic.qiniudn.com/huandeng/3.png";
        String url3 = "http://childmusic.qiniudn.com/huandeng/4.png";
        String url4 = "http://childmusic.qiniudn.com/huandeng/5.png";
        String url5 = "http://childmusic.qiniudn.com/huandeng/6.png";
        List<String> images=new ArrayList<String>();
        images.add(url0);
        images.add(url1);
        images.add(url2);
        images.add(url3);
        images.add(url4);
        images.add(url5);
        //轮播图的样式   设置为指示器+标题
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        //设置指示器的位置
        banner.setIndicatorGravity(BannerConfig.RIGHT);
        //设置标题的文字
        banner.setBannerTitles(titles);
        //设置图片
        banner.setImages(images).setImageLoader(new GlideImageLoader()).start();

        toggleBtn= (ToggleButton) findViewById(R.id.btn);
        //切换开关
       // toggleBtn.toggle();
        //切换无动画
        //toggleBtn.toggle(false);
        //开关切换事件
        toggleBtn.setOnToggleChanged(new ToggleButton.OnToggleChanged(){
            @Override
            public void onToggle(boolean on) {
                Log.d("王恩强",on+"");
            }
        });


        toggleBtn.setToggleOn();
       // toggleBtn.setToggleOff();
        //无动画切换
       // toggleBtn.setToggleOn(false);
       // toggleBtn.setToggleOff(false);

        //禁用动画
        //toggleBtn.setAnimate(false);

    }
}
