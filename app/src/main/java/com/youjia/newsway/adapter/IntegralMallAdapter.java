package com.youjia.newsway.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.youjia.newsway.R;
import com.youjia.newsway.activity.ExchangeDetailActivity;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2016/12/14.
 */



public class IntegralMallAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<HashMap<String,String>> datas;
    Context context;
    public View mHeaderView; //头布局
    public static final int TYPE_HEADER = 0;  //说明是带有Header的
    public static final int TYPE_NORMAL = 2;  //说明是不带有header和footer的

    public View getmHeaderView() {
        return mHeaderView;
    }

    public void setmHeaderView(View mHeaderView) {
        this.mHeaderView = mHeaderView;
        notifyItemInserted(0);
    }


    @Override
    public int getItemViewType(int position) {
        if(position ==0){
            //第一个item应该加载Header
            return  TYPE_HEADER;
        }
        return TYPE_NORMAL;
    }

    public IntegralMallAdapter(ArrayList<HashMap<String, String>> datas, Context context) {
        this.datas = datas;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (mHeaderView != null && viewType == TYPE_HEADER) {
            return new MyViewHolder(mHeaderView);
        }

        View layout= LayoutInflater.from(context).inflate(R.layout.integral_mall_recycleview_item,null);

        return new MyViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {


        if (position != 0) {
            Log.d("王恩强--position:",position+"");
            ((MyViewHolder)holder).title.setText(datas.get(position-1).get("title"));
            ((MyViewHolder)holder).jifen.setText(datas.get(position-1).get("jifen")+"积分");
            ((MyViewHolder)holder).img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context, ExchangeDetailActivity.class);
                    context.startActivity(intent);
                }
            });
        }



    }

    @Override
    public int getItemCount() {
        return datas.size()+1;
    }


    /*
    *设置SpanSizeLookup，它将决定view会横跨多少列。这个方法是为RecyclerView添加Header和Footer的关键。
    *当判断position指向的View为Header或者Footer时候，返回总列数（ lm.getSpanCount()）,即可让其独占一行。
    */
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager=recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager= (GridLayoutManager) manager;
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return getItemViewType(position)==TYPE_HEADER?gridManager.getSpanCount():1;
                }
            });
        }
    }

    public boolean isHeader(int position) {
        return position == 0;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView img;
        TextView title,jifen;

        public MyViewHolder(View itemView) {
            super(itemView);
            if(itemView!=mHeaderView){

                img= (ImageView) itemView.findViewById(R.id.integral_mall_recycleview_item_img);
                title= (TextView) itemView.findViewById(R.id.integral_mall_recycleview_item_title);
                jifen= (TextView) itemView.findViewById(R.id.integral_mall_recycleview_item_jifen);
            }


        }
    }
}
