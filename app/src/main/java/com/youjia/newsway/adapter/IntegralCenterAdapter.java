package com.youjia.newsway.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.youjia.newsway.R;
import com.youjia.newsway.bean.IntegralCenterModel;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/12/8.
 */

public class IntegralCenterAdapter extends BaseAdapter {
    Context context;
    ArrayList<IntegralCenterModel> datas;
    private int[] imgs={R.drawable.open_xiao,
            R.drawable.reading1,
            R.drawable.danmaku,
            R.drawable.reading2,
            R.drawable.open_xiao,
            R.drawable.singin,
            R.drawable.settings,
            R.drawable.person,
            R.drawable.guess};

    public IntegralCenterAdapter(Context context, ArrayList<IntegralCenterModel> datas) {
        this.context = context;
        this.datas = datas;
    }

    public int getCount() {
        return datas.size();
    }

    @Override
    public IntegralCenterModel getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView==null) {
            holder=new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.integral_center_listview_item, null);
            holder.title = (TextView) convertView.findViewById(R.id.integral_center_listview_item_title);
            holder.style = (TextView) convertView.findViewById(R.id.integral_center_listview_item_style);
            holder.schedule = (TextView) convertView.findViewById(R.id.integral_center_listview_item_schedule);
            holder.reward = (TextView) convertView.findViewById(R.id.integral_center_listview_item_reward);
            holder.success = (TextView) convertView.findViewById(R.id.integral_center_listview_item_success);
            holder.img = (ImageView) convertView.findViewById(R.id.integral_center_listview_item_img);
            holder.horizontalLine = (View) convertView.findViewById(R.id.integral_center_listview_item_view);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        //控制积分类型的隐藏与显示
        if (position==0) {
            holder.style.setVisibility(View.VISIBLE);
            holder.style.setText(datas.get(position).getStyle());
            holder.horizontalLine.setVisibility(View.VISIBLE);
        }else if(getItem(position).getStyle().equals(getItem(position-1).getStyle())){
            holder.style.setVisibility(View.GONE);
            holder.horizontalLine.setVisibility(View.GONE);
        }else {
            holder.style.setVisibility(View.VISIBLE);
            holder.style.setText(getItem(position).getStyle());
            holder.horizontalLine.setVisibility(View.VISIBLE);
        }
        int CurrentProgress=getItem(position).getCurrentProgress();
        int Target=getItem(position).getTarget();
        holder.title.setText(getItem(position).getTitle());
        holder.schedule.setText("("+CurrentProgress+"/"+Target+")");
        holder.img.setImageResource(imgs[position]);

        //判断是否已经完成,控制显示与隐藏页面
        if (CurrentProgress < Target) {
            holder.success.setVisibility(View.GONE);
        }else{
            holder.success.setVisibility(View.VISIBLE);
        }


        return convertView;
    }

    class ViewHolder{
        TextView style,title,schedule,reward,success;
        View horizontalLine;
        ImageView img;
    }
}
