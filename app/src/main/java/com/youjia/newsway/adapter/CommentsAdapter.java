package com.youjia.newsway.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.youjia.newsway.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2016/12/6.
 */

public class CommentsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int TYPE_HEADER = 0;  //说明是带有Header的
    public static final int TYPE_FOOTER = 1;  //说明是带有Footer的
    public static final int TYPE_NORMAL = 2;  //说明是不带有header和footer的

    ArrayList<HashMap<String,String>> datas;
    Context context;

    public View mFooterView;//脚布局
    private OnItemClickListener clickListener;

    public void setClickListener(OnItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public CommentsAdapter(ArrayList<HashMap<String, String>> datas, Context context) {
        this.datas = datas;
        this.context = context;
    }
    public View getmFooterView() {
        return mFooterView;
    }

    public void setmFooterView(View mFooterView) {
        this.mFooterView = mFooterView;
        notifyItemInserted(datas.size()-1);
    }
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (mFooterView != null && viewType == TYPE_FOOTER) {
            return new CommentsAdapter.MyViewHolder(mFooterView);
        }
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.comments_activity_recycleview_item, parent, false);
        return new CommentsAdapter.MyViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_NORMAL) {
            if (holder instanceof MyViewHolder) {

            }
        } else {
            return;
        }
    }

    @Override
    public int getItemCount() {
        if (mFooterView == null) {
            return datas.size();
        } else{
            return datas.size()+1;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if ( mFooterView == null) {
            return TYPE_NORMAL;
        }

        if(position == getItemCount()-1){
            //最后一个,应该加载Footer
            return  TYPE_FOOTER;
        }
        return TYPE_NORMAL;
    }



    public  class MyViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView img;
        CheckBox checkbox;
        TextView username,time,comment;
        public MyViewHolder(View itemView) {

            super(itemView);
            if(itemView == mFooterView){
                return;
            }

            img= (ImageView) itemView.findViewById(R.id.comments_activity_recycleview_item_img);
            checkbox= (CheckBox) itemView.findViewById(R.id.comments_activity_recycleview_item_check);
            username= (TextView) itemView.findViewById(R.id.comments_activity_recycleview_item_username);
            time= (TextView) itemView.findViewById(R.id.comments_activity_recycleview_item_time);
            comment= (TextView) itemView.findViewById(R.id.comments_activity_recycleview_item_comment);
            username.setOnClickListener(this);
        }


        public void onClick(View view) {
            if (clickListener!=null) {
                clickListener.onClick(itemView,getAdapterPosition());
            }
        }
    }

    public static  interface OnItemClickListener{
        void onClick(View view,int position);
    }
}
