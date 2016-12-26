package com.youjia.newsway.activity;

import android.content.Intent;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.youjia.newsway.R;
import com.youjia.newsway.adapter.ChooseCityAdapter;
import com.youjia.newsway.base.BaseActivity;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import static com.youjia.newsway.R.xml.cities;
import static com.youjia.newsway.R.xml.districts;
import static com.youjia.newsway.R.xml.provinces;

/**
 * Created by Administrator on 2016/12/26.
 */

public class ChooseCityActivity extends BaseActivity implements AdapterView.OnItemClickListener{

    @InjectView(R.id.choose_city_back)
    ImageView imgBack;
    @InjectView(R.id.choose_city_city)
    TextView textCity;
    @InjectView(R.id.choose_city_textlocation)
    TextView textlocation;
    @InjectView(R.id.choose_city_progressbar)
    ProgressBar progressbar;
    @InjectView(R.id.choose_city_location)
    LinearLayout linlayoutLocation;
    @InjectView(R.id.choose_city_locationContent)
    LinearLayout linlayoutContent;
    @InjectView(R.id.choose_city_listview_province)
    ListView listviewProvince;
    @InjectView(R.id.choose_city_listview_city)
    ListView listviewCity;

    ArrayList<String> citys=new ArrayList<>();//城市列表数据源
    ChooseCityAdapter adapter;

    public LocationClient mLocationClient = null;
    public BDLocationListener myListener = new ChooseCityActivity.MyLocationListener();

    String province="";
    String city="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_city);
        ButterKnife.inject(this);
        findViewById();
    }

    @Override
    protected void findViewById() {

        mLocationClient = new LocationClient(getApplicationContext());     //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);    //注册监听函数
        initLocation();
        initListview();
        mLocationClient.start();//开始定位
        listviewProvince.setOnItemClickListener(this);
        listviewCity.setOnItemClickListener(this);


    }


    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span = 1000;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
        mLocationClient.setLocOption(option);
    }

    //初次进入页面初始化话城市列表
    public void initListview(){
        getProvincesInfo();
        adapter=new ChooseCityAdapter(this,citys);
        listviewProvince.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        if (parent.getId()==R.id.choose_city_listview_province){

            province=citys.get(position);
            textCity.setText(province);
            citys.clear();
            int cid=-1;
            if (position==0){
                cid=1;
                getDistrictInfo(cid);
            }else if (position==1){
                cid=2;
                getDistrictInfo(cid);
            } else if (position == 8) {
                cid=73;
                getDistrictInfo(cid);
            }else if(position==21){
                cid=234;
                getDistrictInfo(cid);
            }else{
                getCitiesInfo(position+1);
            }
            listviewCity.setAdapter(adapter);
            listviewProvince.setVisibility(View.GONE);
            linlayoutContent.setVisibility(View.GONE);
            listviewCity.setVisibility(View.VISIBLE);
        }else{
            city=citys.get(position);
            Intent intent=new Intent();
            intent.putExtra("address",province+city);
            setResult(1,intent);
            finish();
        }





    }


    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            //Receive Location
            StringBuffer sb = new StringBuffer(256);
            sb.append("time : ");
            sb.append(location.getTime());
            sb.append("\nerror code : ");
            sb.append(location.getLocType());
            sb.append("\nlatitude : ");
            sb.append(location.getLatitude());
            sb.append("\nlontitude : ");
            sb.append(location.getLongitude());
            sb.append("\nradius : ");
            sb.append(location.getRadius());
            sb.append("\nProvince : ");
            sb.append(location.getProvince());
            sb.append("\nCity : ");
            sb.append(location.getCity());

            if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
                sb.append("\nspeed : ");
                sb.append(location.getSpeed());// 单位：公里每小时
                sb.append("\nsatellite : ");
                sb.append(location.getSatelliteNumber());
                sb.append("\nheight : ");
                sb.append(location.getAltitude());// 单位：米
                sb.append("\ndirection : ");
                sb.append(location.getDirection());// 单位度
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                sb.append("\ndescribe : ");
                sb.append("gps定位成功");

            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                //运营商信息
                sb.append("\noperationers : ");
                sb.append(location.getOperators());
                sb.append("\ndescribe : ");
                sb.append("网络定位成功");
            } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
                sb.append("\ndescribe : ");
                sb.append("离线定位成功，离线定位结果也是有效的");
            } else if (location.getLocType() == BDLocation.TypeServerError) {
                sb.append("\ndescribe : ");
                sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
            } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                sb.append("\ndescribe : ");
                sb.append("网络不同导致定位失败，请检查网络是否通畅");
            } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                sb.append("\ndescribe : ");
                sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
            }
            sb.append("\nlocationdescribe : ");
            sb.append(location.getLocationDescribe());// 位置语义化信息
            List<Poi> list = location.getPoiList();// POI数据
            if (list != null) {
                sb.append("\npoilist size = : ");
                sb.append(list.size());
                for (Poi p : list) {
                    sb.append("\npoi= : ");
                    sb.append(p.getId() + " " + p.getName() + " " + p.getRank());
                }
            }
            Log.i("BaiduLocationApiDem", sb.toString());
            mLocationClient.stop();
            textlocation.setText(location.getProvince()+" "+location.getCity());
            progressbar.setVisibility(View.GONE);
        }


    }





    /**
     * @description 从文件中获取省份信息
     * @return 返回省份的ArrayList对象
     */
    public ArrayList<String> getProvincesInfo()
    {
        //设置xml解析器，为读取的数据创建ArrayList对象
        XmlResourceParser xrp = getResources().getXml(provinces);
        try
        {
            int eventType = xrp.getEventType();
            while (eventType != XmlResourceParser.END_DOCUMENT)
            {
                //当内容标签开始时，读取相应标签的属性
                if (eventType == XmlResourceParser.START_TAG && xrp.getName().equals("Province"))
                {
                    String id = xrp.getAttributeValue(null, "ID");
                    String name = xrp.getAttributeValue(null, "ProvinceName");
                    citys.add(name);
                }
                eventType = xrp.next();
            }
        } catch (XmlPullParserException | IOException e)
        {
            e.printStackTrace();
        }
        xrp.close();
        citys.remove(0);
        return citys;
    }


    /**
     * @description 输入省份ID，获取对应的城市
     * @param provinceId 省份ID，用于在选择其对应的城市
     * @return 返回所包含城市的ArrayList对象
     */
    public ArrayList<String> getCitiesInfo(int provinceId)
    {
        //设置xml解析器，为读取的数据创建ArrayList对象
        XmlResourceParser xrp = getResources().getXml(cities);
        try
        {
            int eventType = xrp.getEventType();
            while (eventType != XmlResourceParser.END_DOCUMENT)
            {
                //当内容标签开始时，读取相应标签的属性
                if (eventType == XmlResourceParser.START_TAG && xrp.getName().equals("City"))
                {
                    String pid = xrp.getAttributeValue(null, "PID");
                    if (provinceId == Integer.parseInt(pid))
                    {
                        String id = xrp.getAttributeValue(null, "ID");
                        String zipCode = xrp.getAttributeValue(null, "ZipCode");
                        String name = xrp.getAttributeValue(null, "CityName");
                        citys.add(name);
                    }
                }
                eventType = xrp.next();
            }
        } catch (XmlPullParserException | IOException e)
        {
            e.printStackTrace();
        }
        xrp.close();
        return citys;
    }


    /**
     * @description 输入城市ID，获取对应的区域
     * @param cityId 城市ID，用于在选择其对应的区域
     * @return 返回所包含区域的ArrayList对象
     */
    public ArrayList<String> getDistrictInfo(int cityId)
    {
        //设置xml解析器，为读取的数据创建ArrayList对象
        XmlResourceParser xrp = getResources().getXml(districts);
        try
        {
            int eventType = xrp.getEventType();
            while (eventType != XmlResourceParser.END_DOCUMENT)
            {
                //当内容标签开始时，读取相应标签的属性
                if (eventType == XmlResourceParser.START_TAG && xrp.getName().equals("District"))
                {
                    String cid = xrp.getAttributeValue(null, "CID");
                    if (cityId == Integer.parseInt(cid))
                    {
                        String id = xrp.getAttributeValue(null, "ID");
                        String name = xrp.getAttributeValue(null, "DistrictName");
                        citys.add(name);
                    }
                }
                eventType = xrp.next();
            }
        } catch (XmlPullParserException | IOException e)
        {
            e.printStackTrace();
        }
        xrp.close();
        return citys;
    }

    @OnClick({R.id.choose_city_back, R.id.choose_city_location})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.choose_city_back:
            case R.id.choose_city_location:
                Intent intent=new Intent();
                intent.putExtra("address",textlocation.getText().toString());
                setResult(1,intent);
                finish();
                break;
        }
    }
}
