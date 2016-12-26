package com.youjia.newsway.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.youjia.newsway.R;
import com.youjia.newsway.adapter.NewsFragmentPagerAdapter;
import com.youjia.newsway.application.AppApplication;
import com.youjia.newsway.bean.ChannelItem;
import com.youjia.newsway.bean.ChannelManage;
import com.youjia.newsway.review.ColumnHorizontalScrollView;
import com.youjia.newsway.tools.BaseTools;

import java.util.ArrayList;

public class HomePageFragment extends Fragment {
	/**
	 * 根布局
	 */
	View v;
	/** 频道管理 自定义HorizontalScrollView */
	private ColumnHorizontalScrollView mColumnHorizontalScrollView;
	LinearLayout mRadioGroup_content;
	LinearLayout ll_more_columns;
	RelativeLayout rl_column;
	// 频道管理
	ImageView button_more_columns;
	/** 新闻分类列表 */
	// private ArrayList<NewsClassify> newsClassify = new
	// ArrayList<NewsClassify>();
	private ArrayList<ChannelItem> userChannelList = new ArrayList<ChannelItem>();
	/** 当前选中的栏目 */
	private int columnSelectIndex = 0;
	/** 左阴影部分 */
	public ImageView shade_left;
	/** 右阴影部分 */
	public ImageView shade_right;
	/** 屏幕宽度 */
	private int mScreenWidth = 0;
	/** Item宽度 */
	private int mItemWidth = 0;
    ViewPager viewpager;

    private ArrayList<Fragment> fragments = new ArrayList<Fragment>();
    private NewsModelFragment newsFragment;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		v = inflater.inflate(R.layout.fragment_homepage, null);
		findViewById();
		init();
		setChangelView();
		return v;
	}

	/**
	 * 绑定组件
	 */
	private void findViewById() {
		/** 初始化layout控件 */
		mColumnHorizontalScrollView = (ColumnHorizontalScrollView) v
				.findViewById(R.id.mColumnHorizontalScrollView);
		mRadioGroup_content = (LinearLayout) v
				.findViewById(R.id.mRadioGroup_content);
        viewpager= (ViewPager) v.findViewById(R.id.channle_viewPager);
		ll_more_columns = (LinearLayout) v.findViewById(R.id.ll_more_columns);
		rl_column = (RelativeLayout) v.findViewById(R.id.rl_column);
		button_more_columns = (ImageView) v
				.findViewById(R.id.button_more_columns);
		shade_left = (ImageView) v.findViewById(R.id.shade_left);
		shade_right = (ImageView) v.findViewById(R.id.shade_right);
		// rgNews = (RadioGroup) v.findViewById(R.id.rgNews);
		

	}

	/**
	 * 当导航状态栏发生改变时调用
	 */
	private void setChangelView() {
		initColumnData();
		initTabColumn();
		initFragment();

	}

	private void initFragment() {
        fragments.clear();//清空
        int count = userChannelList.size();
        for (int i = 0; i < count; i++) {
            Bundle data = new Bundle();
            data.putString("text", userChannelList.get(i).getName());
            data.putInt("id", userChannelList.get(i).getId());
            newsFragment = new NewsModelFragment();
            newsFragment.setArguments(data);
            fragments.add(newsFragment);
        }
        NewsFragmentPagerAdapter mAdapetr = new NewsFragmentPagerAdapter(
                getActivity().getSupportFragmentManager(), fragments);
        viewpager.setAdapter(mAdapetr);
		viewpager.setCurrentItem(0);
        viewpager.setOnPageChangeListener(pageListener);

	}

	/**
	 * 初始化Column栏目项
	 * */
	private void initTabColumn() {
		mRadioGroup_content.removeAllViews();
		int count = userChannelList.size();
		mColumnHorizontalScrollView.setParam(getActivity(), mScreenWidth,
				mRadioGroup_content, shade_left, shade_right, ll_more_columns,
				rl_column);
		for (int i = 0; i < count; i++) {
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					mItemWidth, LayoutParams.WRAP_CONTENT);
			params.leftMargin = 10;
			params.rightMargin = 10;

			TextView localTextView = new TextView(getActivity());
			localTextView.setTextAppearance(getActivity(),
					R.style.top_category_scroll_view_item_text);
			// localTextView.setBackground(getResources().getDrawable(R.drawable.top_category_scroll_text_view_bg));
			localTextView.setBackgroundResource(R.drawable.radio_buttong_bg);
			localTextView.setGravity(Gravity.CENTER);
			localTextView.setPadding(8, 0, 8, 0);
			localTextView.setId(i);
			localTextView.setText(userChannelList.get(i).getName());
			localTextView.setTextColor(getResources().getColorStateList(
					R.color.top_category_scroll_text_color_day));
			if (columnSelectIndex == i) {
				localTextView.setSelected(true);
			}
			localTextView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					for (int i = 0; i < mRadioGroup_content.getChildCount(); i++) {
						View localView = mRadioGroup_content.getChildAt(i);
						if (localView != v)
							localView.setSelected(false);
						else {
							localView.setSelected(true);
							viewpager.setCurrentItem(i);
						}
					}
					 Toast.makeText(getActivity(), userChannelList.get(v.getId()).getName(), Toast.LENGTH_SHORT).show();
				}
			});
			mRadioGroup_content.addView(localTextView, i, params);
		}

	}

	/** 获取Column栏目 数据 */
	private void initColumnData() {
		// newsClassify = Constants.getData();
		userChannelList = ((ArrayList<ChannelItem>) ChannelManage.getManage(AppApplication.getApp().getSQLHelper()).getUserChannel());
		System.out.println("导航栏个数:"+userChannelList.size());
	}
	
	/**
	 * 初始化,一个导航item的宽度
	 */
	private void init() {
		mScreenWidth = BaseTools.getWindowsWidth(getActivity());
		mItemWidth = mScreenWidth / 7;// 一个Item宽度为屏幕的1/7

	}

	/**
	 * 随便的显示与隐藏
	 */
	public void setMenuVisibility(boolean menuVisible) {
		// TODO Auto-generated method stub
		super.setMenuVisibility(menuVisible);
		if (this.getView() != null)
			this.getView()
					.setVisibility(menuVisible ? View.VISIBLE : View.GONE);
	}

    /**
     * ViewPager切换监听方法
     * */
    public ViewPager.OnPageChangeListener pageListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageSelected(int position) {
            // TODO Auto-generated method stub
            viewpager.setCurrentItem(position);
            System.out.println("position========"+position);
            //存储当前资讯页viewpager的滑动页数
            AppApplication.getApp().setNewspage(position);
            selectTab(position);
        }
    };

    /**
     * 选择的Column里面的Tab
     * */
    private void selectTab(int tab_postion) {
        columnSelectIndex = tab_postion;
        for (int i = 0; i < mRadioGroup_content.getChildCount(); i++) {
            View checkView = mRadioGroup_content.getChildAt(tab_postion);
            int k = checkView.getMeasuredWidth();
            int l = checkView.getLeft();
            int i2 = l + k / 2 - mScreenWidth / 2;
            // rg_nav_content.getParent()).smoothScrollTo(i2, 0);
            mColumnHorizontalScrollView.smoothScrollTo(i2, 0);
            // mColumnHorizontalScrollView.smoothScrollTo((position - 2) *
            // mItemWidth , 0);
        }
        // 判断是否选中
        for (int j = 0; j < mRadioGroup_content.getChildCount(); j++) {
            View checkView = mRadioGroup_content.getChildAt(j);
            boolean ischeck;
            if (j == tab_postion) {
                ischeck = true;
            } else {
                ischeck = false;
            }
            checkView.setSelected(ischeck);
        }

    }

}
