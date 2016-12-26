package com.youjia.newsway.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.youjia.newsway.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/12/26.
 */

public class ChooseCityAdapter extends BaseAdapter {
    Context context;
    ArrayList<String> datas;

    public ChooseCityAdapter(Context context, ArrayList<String> datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        if (convertView==null) {
            convertView= LayoutInflater.from(context).inflate(R.layout.choose_city_item,null);
            holder=new ViewHolder();
            holder.name= (TextView) convertView.findViewById(R.id.choose_city_item_name);
            convertView.setTag(holder);

        }else{
            holder= (ViewHolder) convertView.getTag();
        }

        holder.name.setText(datas.get(position));
        return convertView;
    }

    class ViewHolder{
        TextView name;
    }
}
