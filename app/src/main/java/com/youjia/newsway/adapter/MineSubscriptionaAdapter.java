package com.youjia.newsway.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.youjia.newsway.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2016/12/8.
 */

public class MineSubscriptionaAdapter extends BaseAdapter {
    Context context;
    ArrayList<HashMap<String,String>> datas;

    public MineSubscriptionaAdapter(Context context, ArrayList<HashMap<String, String>> datas) {
        this.context = context;
        this.datas = datas;
    }

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
        ViewHolder viewHolder=null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.mine_subscription_listview_item, null);
            viewHolder.title = (TextView) convertView.findViewById(R.id.mine_subscription_listview_item_title);
            viewHolder.num = (TextView) convertView.findViewById(R.id.mine_subscription_listview_item_num);
            viewHolder.cancel = (TextView) convertView.findViewById(R.id.mine_subscription_listview_item_cancel);
            viewHolder.img = (ImageView) convertView.findViewById(R.id.mine_subscription_listview_item_img);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.title.setText(datas.get(position).get("title"));
        viewHolder.num.setText(datas.get(position).get("num")+"订阅");

        return convertView;
    }

    class ViewHolder {
        TextView title,num,cancel;
        ImageView img;
    }
}
