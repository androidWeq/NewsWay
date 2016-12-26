package com.youjia.newsway.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.youjia.newsway.R;
import com.youjia.newsway.base.BaseActivity;
import com.youjia.newsway.dao.RequestSerives;
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
 * Created by Administrator on 2016/12/2.
 */

public class CitySubwayActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    ListView listview;
    ArrayList<HashMap<String, String>> datas;
    @InjectView(R.id.city_subway_bigCityName)
    TextView citySubwayBigCityName;
    @InjectView(R.id.city_subway_changCity)
    TextView citySubwayChangCity;
    @InjectView(R.id.city_subway_smallCityName)
    TextView citySubwaySmallCityName;
    private String cityName;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.city_subway);
        ButterKnife.inject(this);
        getIntentInfo();
        findViewById();
        getCitySubwayLines(cityName);

    }

    /**
     * 获得上一页面传递的城市名字
     */
    private void getIntentInfo() {
        cityName = getIntent().getStringExtra("cityName");
    }

    /**
     * 绑定组件
     */
    protected void findViewById() {
        listview = (ListView) findViewById(R.id.city_subway_listview);
        listview.setOnItemClickListener(this);
        citySubwayBigCityName.setText("欢迎您乘坐"+cityName+"地铁");
        citySubwaySmallCityName.setText(cityName);
        datas = new ArrayList<HashMap<String, String>>();


    }

    public void getCitySubwayLines(String cityName) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Urls.subwayLine)
                //增加返回值为String的支持
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        RequestSerives requestSerives = retrofit.create(RequestSerives.class);
        Call<String> call = requestSerives.getSubwayLine("否", cityName, "地铁", Urls.subawyKey);
        call.enqueue(new Callback<String>() {

            JSONObject jsonObject = null;

            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    //Log.d("王恩强--subways", response.body().toString());
                    jsonObject = new JSONObject(response.body().toString());
                    JSONArray cityLins = jsonObject.getJSONArray("result");
                    int length = cityLins.length();
                    for (int i = 0; i < length; i++) {
                        JSONObject line = cityLins.getJSONObject(i);
                        HashMap<String, String> hs = new HashMap<String, String>();
                        //线路名称
                        hs.put("lineName", line.getString("name"));
                        //线路key
                        hs.put("keyName", line.getString("key_name"));
                        datas.add(hs);
                    }
                    SimpleAdapter adapter = new SimpleAdapter(CitySubwayActivity.this,
                            datas,
                            R.layout.city_subway_listview_item,
                            new String[]{"lineName"},
                            new int[]{R.id.city_subway_listview_item_lineNum});
                    listview.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent toSubwayDetail = new Intent(CitySubwayActivity.this, CitySubwayDetailActivity.class);
        //线路名称
        toSubwayDetail.putExtra("lineKey",datas.get(position).get("keyName"));
        //城市名称
        toSubwayDetail.putExtra("cityName",cityName);
        startActivity(toSubwayDetail);
    }


    @OnClick(R.id.city_subway_changCity)
    public void onClick() {
        Toast.makeText(this, "更换城市", Toast.LENGTH_SHORT).show();
    }
}
