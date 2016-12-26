package com.youjia.newsway.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.youjia.newsway.R;
import com.youjia.newsway.adapter.SubscriptionRecycleviewAdapter;
import com.youjia.newsway.bean.SubscriptionModel;
import com.youjia.newsway.review.DividerItemDecoration;

import java.util.ArrayList;

public class SubscriptionFragment extends Fragment {

	View rootView;
    RecyclerView recyclerView;
    ArrayList<SubscriptionModel> datas;
    SubscriptionRecycleviewAdapter adapter;
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_subscription, null);
        findViewById();
		return rootView;
	}

    /**
     * 绑定组件
     */
    private void findViewById() {
        recyclerView= (RecyclerView) rootView.findViewById(R.id.subscription_recycleview);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        //设置分割线
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL_LIST));
        initData();
        adapter=new SubscriptionRecycleviewAdapter(datas,getActivity());
        setHeaderView(recyclerView);
        setFooterView(recyclerView);
        recyclerView.setAdapter(adapter);


    }

    /**
     * 设置头布局
     * @param view
     */
    private void setHeaderView(RecyclerView view){
        View header = LayoutInflater.from(getActivity()).inflate(R.layout.header_news, view, false);
        adapter.setmHeaderView(header);
    }

    /**
     * 设置教布局
     * @param view
     */
    private void setFooterView(RecyclerView view){
        View footer = LayoutInflater.from(getActivity()).inflate(R.layout.demo_footer, view, false);
        adapter.setmFooterView(footer);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        datas=new ArrayList<SubscriptionModel>();
        SubscriptionModel model;
        for(int i=0;i<10;i++){
            model=new SubscriptionModel();
            model.setTitle("我是第"+i+"条数据");
            model.setDigest("那可怜的数据分类地方啦水电费降落伞放假丢三落四的");
            model.setNum(i*123456+963);
            datas.add(model);
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

}
