package com.youjia.newsway.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.youjia.newsway.R;

import java.util.List;

/**
 * Created by Administrator on 2016/12/2.
 */

public class MineListviewAdapter extends BaseAdapter {
    List<String> datas;
    int []images={R.drawable.mine1,
            R.drawable.mine2,
            R.drawable.mine3,
            R.drawable.mine4,
            R.drawable.mine5,
            R.drawable.mine6};
    Context context;

    public MineListviewAdapter(List<String> datas, Context context) {
        this.datas = datas;
        this.context = context;
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
            convertView= LayoutInflater.from(context).inflate(R.layout.mine_listview_item,null);
            holder.leftImg= (ImageView) convertView.findViewById(R.id.mine_listview_item_img);
            holder.toImg= (ImageView) convertView.findViewById(R.id.mine_listview_item_to);
            holder.text= (TextView) convertView.findViewById(R.id.mine_listview_item_text);
            convertView.setTag(holder);
        }else{
            holder= (Holder) convertView.getTag();
        }
        holder.leftImg.setImageResource(images[position]);
        holder.toImg.setImageResource(R.drawable.to);
        holder.text.setText(datas.get(position));

        return convertView;
    }

    class Holder{
        TextView text;
        ImageView leftImg;
        ImageView toImg;
    }
}
