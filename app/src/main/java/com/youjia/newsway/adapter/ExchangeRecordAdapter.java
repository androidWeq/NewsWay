package com.youjia.newsway.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.youjia.newsway.R;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Administrator on 2016/12/20.
 */

public class ExchangeRecordAdapter extends BaseAdapter {

    Context context;
    ArrayList<HashMap<String, String>> datas;

    public ExchangeRecordAdapter(Context context, ArrayList<HashMap<String, String>> datas) {
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

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.exchange_record_listview_item, null);
            holder=new ViewHolder(convertView);
            convertView.setTag(holder);

        }else{
            holder= (ViewHolder) convertView.getTag();
        }

        if (position==1) {
            holder.duiwan.setVisibility(View.VISIBLE);
            holder.checkbox.setChecked(true);
        }else{
            holder.duiwan.setVisibility(View.INVISIBLE);
            holder.checkbox.setChecked(false);
        }


        return convertView;
    }


    static class ViewHolder {
        @InjectView(R.id.exchange_record_item_duiwan)
        TextView duiwan;
        @InjectView(R.id.exchange_record_item_img)
        ImageView img;
        @InjectView(R.id.exchange_record_item_name)
        TextView name;
        @InjectView(R.id.exchange_record_item_jifen)
        TextView jifen;
        @InjectView(R.id.exchange_record_item_checkbox)
        CheckBox checkbox;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
