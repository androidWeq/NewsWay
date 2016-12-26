package com.youjia.newsway.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.youjia.newsway.R;
import com.youjia.newsway.adapter.OnthewayRecycleViewAdapter;
import com.youjia.newsway.application.AppApplication;
import com.youjia.newsway.bean.ChannelItem;
import com.youjia.newsway.bean.ChannelManage;
import com.youjia.newsway.bean.OnthewayModel;
import com.youjia.newsway.review.ColumnHorizontalScrollView;
import com.youjia.newsway.tools.BaseTools;

import java.util.ArrayList;

public class OnthewayFragment extends Fragment {

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

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter myAdapter;


	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		v = inflater.inflate(R.layout.fragment_ontheway, null);
		findViewById();
        init();
        setChangelView();
        initData();
		return v;
	}

    private void initData() {
        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        myAdapter=new OnthewayRecycleViewAdapter(getDatas(),getActivity());
        // 设置布局管理器
        recyclerView.setLayoutManager(mLayoutManager);
        // 设置adapter
        recyclerView.setAdapter(myAdapter);
    }


    private void findViewById() {
		/** 初始化layout控件 */
		mColumnHorizontalScrollView = (ColumnHorizontalScrollView) v
				.findViewById(R.id.mColumnHorizontalScrollView);
		mRadioGroup_content = (LinearLayout) v
				.findViewById(R.id.mRadioGroup_content);
		ll_more_columns = (LinearLayout) v.findViewById(R.id.ll_more_columns);
		rl_column = (RelativeLayout) v.findViewById(R.id.rl_column);
		button_more_columns = (ImageView) v
				.findViewById(R.id.button_more_columns);
		shade_left = (ImageView) v.findViewById(R.id.shade_left);
		shade_right = (ImageView) v.findViewById(R.id.shade_right);
        recyclerView= (RecyclerView) v.findViewById(R.id.ontheway_recycleListview);

	}

    /**
     * 初始化,一个导航item的宽度
     */
    private void init() {
        mScreenWidth = BaseTools.getWindowsWidth(getActivity());
        mItemWidth = mScreenWidth / 7;// 一个Item宽度为屏幕的1/7

    }

    /**
     * 当导航状态栏发生改变时调用
     */
    private void setChangelView() {
        initColumnData();
        initTabColumn();
        //initFragment();

    }

    /** 获取Column栏目 数据 */
    private void initColumnData() {
        // newsClassify = Constants.getData();
        userChannelList = ((ArrayList<ChannelItem>) ChannelManage.getManage(AppApplication.getApp().getSQLHelper()).getUserChannel());
        System.out.println("导航栏个数:"+userChannelList.size());
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
                    mItemWidth, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.leftMargin = 10;
            params.rightMargin = 10;
            // TextView localTextView = (TextView)
            // mInflater.inflate(R.layout.column_radio_item, null);
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
            localTextView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    for (int i = 0; i < mRadioGroup_content.getChildCount(); i++) {
                        View localView = mRadioGroup_content.getChildAt(i);
                        if (localView != v)
                            localView.setSelected(false);
                        else {
                            localView.setSelected(true);
                            //viewpager.setCurrentItem(i);
                        }
                    }
                    Toast.makeText(getActivity(), userChannelList.get(v.getId()).getName(), Toast.LENGTH_SHORT).show();
                }
            });
            mRadioGroup_content.addView(localTextView, i, params);
        }

    }


    /**
     * 碎片的隐藏与显示
     * @param menuVisible
     */
	public void setMenuVisibility(boolean menuVisible) {
		// TODO Auto-generated method stub
		super.setMenuVisibility(menuVisible);
		if (this.getView() != null)
			this.getView()
					.setVisibility(menuVisible ? View.VISIBLE : View.GONE);
	}

    /**
     * 模拟数据的添加
     * @return
     */
    private ArrayList<OnthewayModel> getDatas(){
        ArrayList<OnthewayModel> datas=new ArrayList<OnthewayModel>();
        OnthewayModel onthewayModel;
        for(int i=0;i<5;i++){
            onthewayModel=new OnthewayModel();
            onthewayModel.setUserName("胖胖"+i);
            if(i%2==1){
                onthewayModel.setWantGo(false);
            }else{
                onthewayModel.setWantGo(true);
            }
            onthewayModel.setWantGoNum(i*2+3);

            datas.add(onthewayModel);
        }
        return datas;
    }



}
