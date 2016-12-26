package com.youjia.newsway.activity;

import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.youjia.newsway.R;
import com.youjia.newsway.base.BaseActivity;
import com.youjia.newsway.bean.City;
import com.youjia.newsway.bean.District;
import com.youjia.newsway.bean.Province;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

public class AddressInfoActivity extends BaseActivity {

    public static final String ADDRESS_TITLE = "您选择的地址是：";
    TextView tvAddress;
    String strProvince = "";
    String strCity = "";
    String strDistrict = "";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.address );
        //绑定地址显示TextView
        tvAddress = (TextView)findViewById(R.id.tvAddress);

        //初始化spinner控件
        initSpnProvince();
    }

    //初始化spnProvince控件，并为其绑定数据，设置监听
    public void initSpnProvince()
    {
        //为控件绑定视图
        Spinner spnProvince = (Spinner)findViewById(R.id.spnProvince);

        //读取并绑定province数据
        final ArrayList<Province> provinces = getProvincesInfo();
        final ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,provinces);
        spnProvince.setAdapter(adapter);

        //为Province设置监听
        spnProvince.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                //初始化城市列表
                initSpnCity((int) id);

                //选中“请选择”时不设置地址显示
                if (id == 0)
                {
                    //清空City、District选项
                    strProvince = "";
                    strCity = "";
                    strDistrict = "";
                } else
                {
                    strProvince = provinces.get((int) id).getName();
                }

                //显示选中的地址
                tvAddress.setText(ADDRESS_TITLE + "  " + strProvince + "  " + strCity + "  " + strDistrict);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });
    }

    //初始化spnCity，并为其绑定数据，设置监听
    public void initSpnCity(int provinceId)
    {
        //为控件绑定视图
        final Spinner spnCity = (Spinner)findViewById(R.id.spnCity);

        //读取并绑定province数据
        final ArrayList<City> cities = getCitiesInfo(provinceId);
        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,cities);
        spnCity.setAdapter(adapter);

        //为Province设置监听
        spnCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                //获取城市ID，并初始化区域列表
                int cid = cities.get((int) id).getId();
                initSpnDistrict(cid);

                //选中“请选择”时不设置地址显示
                if(id == 0)
                {
                    strCity = "";
                    strDistrict = "";
                }
                else
                {
                    strCity = cities.get((int) id).getName();
                }

                //显示选中的地址
                tvAddress.setText(ADDRESS_TITLE + "  " + strProvince + "  " + strCity + "  " + strDistrict);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });
    }

    //初始化spnDistrict控件，绑定数据，设置监听
    public void initSpnDistrict(int cityId)
    {
        //为控件绑定视图
        final Spinner spnDistrict = (Spinner)findViewById(R.id.spnDistrict);

        //读取并绑定province数据
        final ArrayList<District> districts = getDistrictInfo(cityId);
        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,districts);
        spnDistrict.setAdapter(adapter);

        spnDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                //选中“请选择”时不设置地址显示
                if (id == 0)
                {
                    strDistrict = "";
                } else
                {
                    strDistrict = districts.get((int) id).getName();

                }

                //显示选中的地址
                tvAddress.setText(ADDRESS_TITLE + "  " + strProvince + "  " + strCity + "  " + strDistrict);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });
    }

    /**
     * @description 从文件中获取省份信息
     * @return 返回省份的ArrayList对象
     */
    public ArrayList<Province> getProvincesInfo()
    {
        //设置xml解析器，为读取的数据创建ArrayList对象
        XmlResourceParser xrp = getResources().getXml(R.xml.provinces);
        ArrayList<Province> provinces = new ArrayList<>();
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
                    provinces.add(new Province(Integer.parseInt(id), name));
                }
                eventType = xrp.next();
            }
        } catch (XmlPullParserException | IOException e)
        {
            e.printStackTrace();
        }
        xrp.close();
        return provinces;
    }

    /**
     * @description 输入省份ID，获取对应的城市
     * @param provinceId 省份ID，用于在选择其对应的城市
     * @return 返回所包含城市的ArrayList对象
     */
    public ArrayList<City> getCitiesInfo(int provinceId)
    {
        //设置xml解析器，为读取的数据创建ArrayList对象
        XmlResourceParser xrp = getResources().getXml(R.xml.cities);
        ArrayList<City> cities = new ArrayList<>();
        cities.add(new City(0,0,"请选择",""));
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
                        cities.add(new City(Integer.parseInt(id), Integer.parseInt(pid), name, zipCode));
                    }
                }
                eventType = xrp.next();
            }
        } catch (XmlPullParserException | IOException e)
        {
            e.printStackTrace();
        }
        xrp.close();
        return cities;
    }

    /**
     * @description 输入城市ID，获取对应的区域
     * @param cityId 城市ID，用于在选择其对应的区域
     * @return 返回所包含区域的ArrayList对象
     */
    public ArrayList<District> getDistrictInfo(int cityId)
    {
        //设置xml解析器，为读取的数据创建ArrayList对象
        XmlResourceParser xrp = getResources().getXml(R.xml.districts);
        ArrayList<District> districts = new ArrayList<>();
        districts.add(new District(0,0,"请选择"));
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
                        districts.add(new District(Integer.parseInt(id), cityId, name));
                    }
                }
                eventType = xrp.next();
            }
        } catch (XmlPullParserException | IOException e)
        {
            e.printStackTrace();
        }
        xrp.close();
        return districts;
    }

    @Override
    protected void findViewById() {

    }
}
