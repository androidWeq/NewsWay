package com.youjia.newsway.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.youjia.newsway.R;
import com.youjia.newsway.activity.AddressInfoActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Administrator on 2016/12/20.
 */

public class ExchangeDetailAdapter extends BaseAdapter {
    @Override
    public int getCount() {
        return 6;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        ViewHolder holder=null;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.exchange_details_listview_item, null);
            holder=new ViewHolder(convertView);
            convertView.setTag(holder);
            holder.address.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(parent.getContext(), AddressInfoActivity.class);
                    parent.getContext().startActivity(intent);
                }
            });

        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        return convertView;
    }

    static class ViewHolder {
        @InjectView(R.id.exchange_details_item_img)
        ImageView img;
        @InjectView(R.id.exchange_details_item_title)
        TextView title;
        @InjectView(R.id.exchange_details_item_jifen1)
        TextView jifen1;
        @InjectView(R.id.exchange_details_item_price)
        TextView price;
        @InjectView(R.id.exchange_details_item_kuaidi)
        TextView kaudi;
        @InjectView(R.id.exchange_details_item_address)
        TextView address;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
