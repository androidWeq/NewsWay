package com.youjia.newsway.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.youjia.newsway.R;
import com.youjia.newsway.fragment.MineDanmkuFragment;
import com.youjia.newsway.fragment.MineNoticeFragment;
import com.youjia.newsway.fragment.MineReplyFragment;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/12/7.
 */

public class MineNewsActivity extends FragmentActivity {
    @InjectView(R.id.mine_news_activity_back)
    ImageView imgBack;
    @InjectView(R.id.mine_news_activity_radiobtn1)
    RadioButton radioBtn1;
    @InjectView(R.id.mine_news_activity_radiobtn2)
    RadioButton radioBtn2;
    @InjectView(R.id.mine_news_activity_radiobtn3)
    RadioButton radioBtn3;
    @InjectView(R.id.mine_news_activity_radiogroup)
    RadioGroup radioGroup;
    @InjectView(R.id.mine_news_activity_huadong)
    ImageView imgHuadong;
    @InjectView(R.id.mine_news_activity_viewpager)
    ViewPager viewPager;
    /** 进度条宽度 **/
    private Integer imageViewW = 0;
    /** 当官视图宽度 **/
    private Integer viewPagerW = 0;
    /** 布局属性 **/
    private RelativeLayout.LayoutParams layoutParams;
    /** 进度条移动值 **/
    private Integer moveI;
    /** 移动图片刚开始的偏移量 **/
    int maginLeft =0;

    ArrayList<Fragment> pagerdatas = new ArrayList<Fragment>();

    private boolean isFirst=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mine_news_activity);
        ButterKnife.inject(this);
        initViewPager();
    }

    /**
     * 初始化viewpager
     */
    private void initViewPager() {
        layoutParams=(RelativeLayout.LayoutParams)imgHuadong.getLayoutParams();
        MineReplyFragment replyFragment=new MineReplyFragment();
        MineDanmkuFragment danmkuFragment=new MineDanmkuFragment();
        MineNoticeFragment noticeFragment=new MineNoticeFragment();
        pagerdatas.add(replyFragment);
        pagerdatas.add(danmkuFragment);
        pagerdatas.add(noticeFragment);
        MyFragmentPagerAdapter fragmentPagerAdapter = new MyFragmentPagerAdapter(
                getSupportFragmentManager(), pagerdatas);
        viewPager.setAdapter(fragmentPagerAdapter);
        fragmentPagerAdapter.setFragments(pagerdatas);
        viewPager.setOnPageChangeListener(new MyOnPageChangeListener());
        // 第一次启动时选中第0个tab
        viewPager.setCurrentItem(0);

    }

    private class MyFragmentPagerAdapter extends FragmentStatePagerAdapter {
        private ArrayList<Fragment> fragments;
        private FragmentManager fm;

        public MyFragmentPagerAdapter(FragmentManager fm,
                                      ArrayList<Fragment> fragments) {
            super(fm);
            this.fm = fm;
            this.fragments = fragments;
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        public void setFragments(ArrayList<Fragment> fragments) {
            if (this.fragments != null) {
                FragmentTransaction ft = fm.beginTransaction();
                for (Fragment f : this.fragments) {
                    ft.remove(f);
                }
                ft.commit();
                ft = null;
                fm.executePendingTransactions();
            }
            this.fragments = fragments;
            notifyDataSetChanged();
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            Object obj = super.instantiateItem(container, position);
            return obj;
        }

    }

    /**
     * viewpager的滑动监听事件
     *
     * @author Administrator
     *
     */
    public class MyOnPageChangeListener implements
            ViewPager.OnPageChangeListener {

        @Override
        public void onPageSelected(int position) {
            //Toast.makeText(NewOrderDetail.this, position + "", 1).show();
            //单页面改变时,改变radiobutton的样式
            RadioButton btn=(RadioButton)radioGroup.getChildAt(position);
            btn.setChecked(true);
            Toast.makeText(MineNewsActivity.this, "position:" + position, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }

        @Override
        public void onPageScrolled(int position, float positionOffset,
                                   int positionOffsetPixels) {
            tabMove(position,positionOffsetPixels);
        }
    }

    /**
     * 视图加载完成之后执行：值得注意的是PopupWindow每次显示和销毁都会执行一次
     */
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        // 只执行一次
        if (isFirst) {
            // 取标签宽度付给进度条，获得单个界面的宽度
            isFirst = false;
            int wh = imgHuadong.getWidth();
            int radioDroupWidth=radioGroup.getWidth();
            viewPagerW = viewPager.getWidth() + viewPager.getPageMargin();
            imageViewW = wh;
            layoutParams.width = wh;
            maginLeft=(radioDroupWidth/3-imageViewW)/2;
            layoutParams.leftMargin=maginLeft;
            System.out.println("layoutParams.leftMargin:"+layoutParams.leftMargin);
            imgHuadong.setLayoutParams(layoutParams);
        }
    }

    /**
     * 进度条移动 :核心的移动算法
     *
     * @param position
     *            当前页位置
     * @param positionOffsetPixels
     *            移动像素值
     */
    private void tabMove(int position, int positionOffsetPixels) {
        System.out.println("imageViewW"+imageViewW+"-----viewPagerW:"+viewPagerW);
        //moveI = (int) ((int) (viewPagerW * position) + (((double) positionOffsetPixels / viewPagerW) * imageViewW));
        moveI=(int) (viewPagerW/3*position+positionOffsetPixels/3);
        System.out.println("moveI:"+moveI);
        if (position == 0) {

            layoutParams.leftMargin = moveI + maginLeft;
        } else {
            layoutParams.leftMargin = moveI;
        }
        imgHuadong.setLayoutParams(layoutParams);
    }


    /*返回按钮的点击事件*/
    @OnClick(R.id.mine_news_activity_back)
    public void onClick() {
        finish();
    }



}
