package com.youjia.newsway.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.youjia.newsway.BuildConfig;
import com.youjia.newsway.R;
import com.youjia.newsway.base.BaseActivity;
import com.youjia.newsway.bean.BusLineItem;
import com.youjia.newsway.dao.RequestSerives;
import com.youjia.newsway.review.BusLineView;
import com.youjia.newsway.tools.Urls;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by Administrator on 2016/12/3.
 */

public class CitySubwayDetailActivity extends BaseActivity implements BusLineView.OnBusStationClickListener {

    @InjectView(R.id.city_subway_detail_back)
    ImageView citySubwayDetailBack;
    @InjectView(R.id.city_subway_detail_lineName)
    TextView citySubwayDetailLineName;
    /**
     * 自定义地铁路线
     */
    private BusLineView subLine;
    GridView gridView;
    ArrayList<HashMap<String, Object>> datas;
    String[] citys = {"附近", "热门", "休闲", "排行", "附近", "热门", "休闲", "排行"};
    int [] images={R.drawable.g1,R.drawable.g2,R.drawable.g3,R.drawable.g4,
            R.drawable.g1,R.drawable.g2,R.drawable.g3,R.drawable.g4};
    ArrayList<BusLineItem> list;
    /* 城市名字*/
    private String cityName;
    /*线路名字*/
    private String lineKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cityName = getIntent().getStringExtra("cityName");
        lineKey = getIntent().getStringExtra("lineKey");
        setContentView(R.layout.city_subway_detail);
        ButterKnife.inject(this);

        findViewById();
    }

    @Override
    protected void findViewById() {
        subLine = (BusLineView) findViewById(R.id.city_subway_detail_bus_line);
        gridView = (GridView) findViewById(R.id.city_subway_detail_gridview);
        citySubwayDetailLineName.setText(lineKey);
        setGridViewInfo();
        initData();
        //subLine.setBusLineData(aaa());

        subLine.setOnBusStationClickListener(this); // 设置公交站点的点击事件
    }

    /**
     * 设置gridview的数据
     */
    private void setGridViewInfo() {
        datas = new ArrayList<HashMap<String, Object>>();
        HashMap<String, Object> hs=null;
        String []from={"name","img"};
        int []to={R.id.citysubway_detail_gridviewitem_txt, R.id.citysubway_detail_gridviewitem_img};
        for (int i = 0; i < citys.length; i++) {
            hs = new HashMap<String, Object>();
            hs.put("name", citys[i]);
            hs.put("img", images[i]);
            datas.add(hs);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(this,
                datas,
                R.layout.city_subway_detail_gridview_item, from,to);
        gridView.setAdapter(simpleAdapter);
    }

    /**
     * 填充线路数据
     *
     * @return
     */
    private void initData() {

        if (list == null) {
            list = new ArrayList<>();
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Urls.subwayLine)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        RequestSerives requestSerives = retrofit.create(RequestSerives.class);
        Call<String> call = requestSerives.getSubwayLine("否", cityName, lineKey, Urls.subawyKey);
        call.enqueue(new Callback<String>() {

            JSONObject rootJsonObject = null;

            public void onResponse(Call<String> call, Response<String> response) {
                Log.d("subway",response.body().toString());
                try {
                    rootJsonObject = new JSONObject(response.body().toString());
                    JSONArray jsonArray = rootJsonObject.getJSONArray("result");
                    JSONArray stationdes = jsonArray.getJSONObject(0).getJSONArray("stationdes");
                    int size = stationdes.length();
                    BusLineItem item;
                    for (int i = 0; i < size; i++) {
                        item = new BusLineItem();
                        item.name = stationdes.getJSONObject(i).getString("name");
                        //Log.d("王恩强station名字", item.name);
                        list.add(item);
                    }
                    subLine.setBusLineData(list); // 设置数据

//                    Android UI中和重绘相关的方法主要是两个：invalidate和requestLayout。
//                    invalidate是重新画，只会调用view的draw方法，不会调用measure和layout。
//                    requestLayout是重新布局，会调用view所在布局树（也就是整个页面）重新布局整个页面。

                    subLine.requestLayout();



                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("subway","获取想起地铁信息错误");
            }
        });


    }

    public ArrayList<BusLineItem> aaa(){

        list = new ArrayList<>();
        BusLineItem item = new BusLineItem();
        item.name = "紫薇阁";
        item.trafficState = initRoadState();
        list.add(item);

        item = new BusLineItem();
        item.name = "景蜜村";
        item.busPosition = 0;
        item.trafficState = initRoadState();
        list.add(item);
//
        item = new BusLineItem();
        item.name = "福田外语学校东";
        item.trafficState = initRoadState();
        item.isRailwayStation = true;
        item.isCurrentPosition = true;
        list.add(item);

        item = new BusLineItem();
        item.name = "景田沃尔玛";
        item.trafficState = initRoadState();
        item.isRailwayStation = true;
        item.busPosition = 30;
        list.add(item);

        item = new BusLineItem();
        item.name = "特发小区";
        item.trafficState = initRoadState();
        list.add(item);

        item = new BusLineItem();
        item.name = "枫丹雅苑";
        item.isRailwayStation = false;
        list.add(item);

        item = new BusLineItem();
        item.name = "市委党校";
        item.trafficState = initRoadState();
        list.add(item);

        item = new BusLineItem();
        item.name = "香蜜红荔立交";
        item.busPosition = 50;
        list.add(item);

        item = new BusLineItem();
        item.name = "景蜜村";
        item.trafficState = initRoadState();
        list.add(item);

        item = new BusLineItem();
        item.name = "天安数码城";
        item.trafficState = initRoadState();
        list.add(item);

        item = new BusLineItem();
        item.name = "上沙村";
        list.add(item);

        item = new BusLineItem();
        item.name = "沙嘴路口";
        item.trafficState = initRoadState();
        list.add(item);

        item = new BusLineItem();
        item.name = "新洲中学";
        item.busPosition = 80;
        list.add(item);

        item = new BusLineItem();
        item.name = "新洲三街";
        item.trafficState = initRoadState();
        list.add(item);

        item = new BusLineItem();
        item.name = "新洲村";
        list.add(item);

        item = new BusLineItem();
        item.name = "众孚小学";
        item.trafficState = initRoadState();
        list.add(item);

        item = new BusLineItem();
        item.name = "石厦学校";
        list.add(item);

        item = new BusLineItem();
        item.name = "福田区委";
        item.trafficState = initRoadState();
        item.isRailwayStation = true;
        list.add(item);

        item = new BusLineItem();
        item.name = "皇岗村";
        item.busPosition = 50;
        item.isRailwayStation = true;
        list.add(item);

        item = new BusLineItem();
        item.name = "福田保健院";
        item.trafficState = initRoadState();
        item.isRailwayStation = true;
        list.add(item);

        item = new BusLineItem();
        item.name = "福民地铁站";
        item.isRailwayStation = true;
        list.add(item);

        item = new BusLineItem();
        item.name = "福强路口";
        item.trafficState = initRoadState();
        item.busPosition = 80;
        list.add(item);

        item = new BusLineItem();
        item.name = "福田口岸总站";
        item.isRailwayStation = true;
        list.add(item);

        return list;
    }

    /**
     * @return
     */
    private BusLineItem.RoadState[] initRoadState() {
        BusLineItem.RoadState[] rss = new BusLineItem.RoadState[5];
        BusLineItem.RoadState rs = new BusLineItem.RoadState();
        rs.idx = 0;
        rs.start = 0;
        rs.ratio = 0.1f;
        rs.state = BusLineItem.RoadState.ROAD_BUSY;
        rss[0] = rs;

        rs = new BusLineItem.RoadState();
        rs.idx = 1;
        rs.start = 0.1f;
        rs.ratio = 0.2f;
        rs.state = BusLineItem.RoadState.ROAD_NORMAL;
        rss[1] = rs;

        rs = new BusLineItem.RoadState();
        rs.idx = 2;
        rs.start = 0.3f;
        rs.ratio = 0.2f;
        rs.state = BusLineItem.RoadState.ROAD_BLOCK;
        rss[2] = rs;

        rs = new BusLineItem.RoadState();
        rs.idx = 3;
        rs.start = 0.5f;
        rs.ratio = 0.3f;
        rs.state = BusLineItem.RoadState.ROAD_BUSY;
        rss[3] = rs;

        rs = new BusLineItem.RoadState();
        rs.idx = 4;
        rs.start = 0.8f;
        rs.ratio = 0.2f;
        rs.state = BusLineItem.RoadState.ROAD_BLOCK;
        rss[4] = rs;
        return rss;
    }

    /**
     * 地铁站点的点击事件
     *
     * @param view
     * @param item
     */
    public void onBusStationClick(View view, BusLineItem item) {
        if (BuildConfig.DEBUG) {
            Log.d("王恩强", "name: " + item.name);
        }
    }

    /**
     * 返回按钮
     */
    @OnClick(R.id.city_subway_detail_back)
    public void onClick() {
        finish();
    }
}
