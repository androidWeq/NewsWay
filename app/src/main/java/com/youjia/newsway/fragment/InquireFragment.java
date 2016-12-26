package com.youjia.newsway.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.youjia.newsway.R;
import com.youjia.newsway.activity.CitySubwayActivity;

import java.util.ArrayList;
import java.util.HashMap;


public class InquireFragment extends Fragment implements AdapterView.OnItemClickListener {

	View rootView;
	GridView gridView;
	String []citys={"北京","上海","广州",
			"深圳","香港","重庆",
			"武汉","南京","杭州",
			"天津","苏州","成都",
			"郑州","长沙","哈尔滨",
			"西安","大连","广州"};
	int [] images={R.drawable.ic_launcher,
			R.drawable.ic_launcher,
			R.drawable.ic_launcher,
			R.drawable.ic_launcher,
			R.drawable.ic_launcher,
			R.drawable.ic_launcher,
			R.drawable.ic_launcher,
			R.drawable.ic_launcher,
			R.drawable.ic_launcher,
			R.drawable.ic_launcher,
			R.drawable.ic_launcher,
			R.drawable.ic_launcher,
			R.drawable.ic_launcher,
			R.drawable.ic_launcher,
			R.drawable.ic_launcher,
			R.drawable.ic_launcher,
			R.drawable.ic_launcher,
			R.drawable.ic_launcher,};

	ArrayList<HashMap<String,Object>> datas;
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_inquire, null);
		findViewById();
		setGridViewInfo();
		return rootView;
	}

	/**
	 * 设置gridview的数据
	 */
	private void setGridViewInfo() {
		datas=new ArrayList<HashMap<String,Object>>();
		HashMap<String,Object> hs;
		for(int i=0;i<images.length;i++){
			hs=new HashMap<String,Object>();
			hs.put("cityName",citys[i]);
			hs.put("cityImg",images[i]);
            datas.add(hs);
		}
        // 参数一是当前上下文Context对象
        // 参数二是图片数据列表，要显示数据都在其中
        // 参数三是界面的XML文件，注意，不是整体界面，而是要显示在GridView中的单个Item的界面XML
        // 参数四是动态数组中与map中图片对应的项，也就是map中存储进去的相对应于图片value的key
        // 参数五是单个Item界面XML中的图片ID
        SimpleAdapter simpleAdapter=new SimpleAdapter(getActivity(),
                datas,
                R.layout.inquire_gridview_item,
                new String[]{"cityName","cityImg"},
                new int[]{R.id.inquire_gridview_item_text,R.id.inquire_gridview_item_img});
        gridView.setAdapter(simpleAdapter);
	}

	private void findViewById() {
		gridView= (GridView) rootView.findViewById(R.id.inquire_gridview);
        gridView.setOnItemClickListener(this);
	}


	public void setMenuVisibility(boolean menuVisible) {
		// TODO Auto-generated method stub
		super.setMenuVisibility(menuVisible);
		if (this.getView() != null)
			this.getView()
					.setVisibility(menuVisible ? View.VISIBLE : View.GONE);
	}

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent toCitySubway=new Intent(getActivity(), CitySubwayActivity.class);
		if(position==4 || position ==5 ){
			Toast.makeText(getActivity(),"暂未开通地铁~~",Toast.LENGTH_SHORT).show();
		}else{
            toCitySubway.putExtra("cityName",citys[position]);
			startActivity(toCitySubway);
		}
    }
}
