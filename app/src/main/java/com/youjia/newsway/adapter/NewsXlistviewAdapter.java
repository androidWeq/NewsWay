package com.youjia.newsway.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.youjia.newsway.R;
import com.youjia.newsway.bean.NewsModel;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/11/30.
 */

public class NewsXlistviewAdapter extends BaseAdapter {

    Context context;
    ArrayList<NewsModel> datas;

    public NewsXlistviewAdapter(Context context, ArrayList<NewsModel> datas) {
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
        Holder holder=null;
        if (convertView == null) {
            holder=new Holder();
            convertView= LayoutInflater.from(context).inflate(R.layout.xlistview_item,null);
            holder.img= (ImageView) convertView.findViewById(R.id.news_xlistview_item_img);
            holder.title= (TextView) convertView.findViewById(R.id.news_xlistview_item_title);
            holder.digest= (TextView) convertView.findViewById(R.id.news_xlistview_item_digest);
            convertView.setTag(holder);
        }else{
            holder= (Holder) convertView.getTag();
        }
        holder.title.setText(datas.get(position).getTitle());
        holder.digest.setText(datas.get(position).getDigest());
        Glide.with(context).load(datas.get(position).getImg()).into(holder.img);

        return convertView;
    }

    class Holder{
        TextView title;
        ImageView img;
        TextView digest;
    }
}
