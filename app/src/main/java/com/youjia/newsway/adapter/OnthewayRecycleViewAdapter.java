package com.youjia.newsway.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.youjia.newsway.R;
import com.youjia.newsway.activity.ConmmentsActivity;
import com.youjia.newsway.bean.OnthewayModel;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/12/3.
 */

public class OnthewayRecycleViewAdapter extends RecyclerView.Adapter<OnthewayRecycleViewAdapter.MyViewHolder> {

    private ArrayList<OnthewayModel> datas;
    private Context context;
    public OnthewayRecycleViewAdapter(ArrayList<OnthewayModel> datas,Context context) {
        this.datas = datas;
        this.context=context;
    }

    /**
     * 刷新适配器
     * @param datas
     */
    public void updateData(ArrayList<OnthewayModel> datas){
        this.datas = datas;
        notifyDataSetChanged();
    }

    //创建新View，被LayoutManager所调用
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 实例化展示的view
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.ontheway_relistview_item,parent,false);
        // 实例化viewholder
        MyViewHolder vh=new MyViewHolder(view);
        return vh;
    }

    //绑定数据
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.userName.setText(datas.get(position).getUserName());
        holder.wantgo.setText("我想去 "+datas.get(position).getWantGoNum());
        holder.wantgo.setChecked(datas.get(position).isWantGo());
        holder.comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ConmmentsActivity.class);
                context.startActivity(intent);
            }
        });
        holder.wantgo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){

                    holder.wantgo.setText("我想去 "+(datas.get(position).getWantGoNum()+1));
                }else{
                    holder.wantgo.setText("我想去 "+(datas.get(position).getWantGoNum()-1));
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        Log.d("王恩强",datas.size()+"");
        return datas ==null ?0:datas.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public ImageView userImg,userGrade;
        public TextView userName,location,time,recommend,phone;
        public ImageView img1,img2,img3,img4,img5,img6,img7,img8,img9;
        public TextView comment,share;
        public CheckBox wantgo;

        public MyViewHolder(View itemView){
            super(itemView);
            userImg= (ImageView) itemView.findViewById(R.id.ontheway_relistview_item_userimg);
            userGrade= (ImageView) itemView.findViewById(R.id.ontheway_relistview_item_grade);
            userName= (TextView) itemView.findViewById(R.id.ontheway_relistview_item_name);
            location= (TextView) itemView.findViewById(R.id.ontheway_relistview_item_location);
            time= (TextView) itemView.findViewById(R.id.ontheway_relistview_item_time);
            recommend= (TextView) itemView.findViewById(R.id.ontheway_relistview_item_recommend);
            phone= (TextView) itemView.findViewById(R.id.ontheway_relistview_item_phone);

            img1= (ImageView) itemView.findViewById(R.id.ontheway_relistview_item_img1);
            img2= (ImageView) itemView.findViewById(R.id.ontheway_relistview_item_img2);
            img3= (ImageView) itemView.findViewById(R.id.ontheway_relistview_item_img3);
            img4= (ImageView) itemView.findViewById(R.id.ontheway_relistview_item_img4);
            img5= (ImageView) itemView.findViewById(R.id.ontheway_relistview_item_img5);
            img6= (ImageView) itemView.findViewById(R.id.ontheway_relistview_item_img6);
            img7= (ImageView) itemView.findViewById(R.id.ontheway_relistview_item_img7);
            img8= (ImageView) itemView.findViewById(R.id.ontheway_relistview_item_img8);
            img9= (ImageView) itemView.findViewById(R.id.ontheway_relistview_item_img9);

            wantgo= (CheckBox) itemView.findViewById(R.id.ontheway_relistview_item_checkbox);
            comment= (TextView) itemView.findViewById(R.id.ontheway_relistview_item_comment);
            share= (TextView) itemView.findViewById(R.id.ontheway_relistview_item_share);

        }



    }
}
