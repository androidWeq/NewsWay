package com.youjia.newsway.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.youjia.newsway.R;
import com.youjia.newsway.bean.SubscriptionModel;
import com.youjia.newsway.tools.GlideImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/5.
 */

public class SubscriptionRecycleviewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    public static final int TYPE_HEADER = 0;  //说明是带有Header的
    public static final int TYPE_FOOTER = 1;  //说明是带有Footer的
    public static final int TYPE_NORMAL = 2;  //说明是不带有header和footer的
    ArrayList<SubscriptionModel> datas;  //获取从Activity中传递过来每个item的数据集合
    Context context;
    public View mHeaderView; //头布局
    public View mFooterView;//脚布局


    String url0 = "http://childmusic.qiniudn.com/huandeng/1.png";
    String url1 = "http://childmusic.qiniudn.com/huandeng/2.png";
    String url2 = "http://childmusic.qiniudn.com/huandeng/3.png";
    String url3 = "http://childmusic.qiniudn.com/huandeng/4.png";
    String url4 = "http://childmusic.qiniudn.com/huandeng/5.png";
    String url5 = "http://childmusic.qiniudn.com/huandeng/6.png";

    String t1="第一条信息";
    String t2="第er条信息";
    String t3="第san条信息";
    String t4="第si条信息";
    String t5="第wu条信息";
    String t6="第liu条信息";
    List<String> titles;
    List<String> images;

    public SubscriptionRecycleviewAdapter(ArrayList<SubscriptionModel> datas, Context context) {
        this.datas = datas;
        this.context = context;
        titles=new ArrayList<String>();
        titles.add(t1);
        titles.add(t2);
        titles.add(t3);
        titles.add(t4);
        titles.add(t5);
        titles.add(t6);
        images=new ArrayList<String>();
        images.add(url0);
        images.add(url1);
        images.add(url2);
        images.add(url3);
        images.add(url4);
        images.add(url5);
    }
    //HeaderView和FooterView的get和set函数
    public View getmFooterView() {
        return mFooterView;
    }

    public void setmFooterView(View mFooterView) {
        this.mFooterView = mFooterView;
        notifyItemInserted(datas.size()-1);
    }

    public View getmHeaderView() {
        return mHeaderView;
    }

    public void setmHeaderView(View mHeaderView) {
        this.mHeaderView = mHeaderView;
        notifyItemInserted(0);
    }

    /**
     * 重写这个方法，很重要，是加入Header和Footer的关键，我们通过判断item的类型，从而绑定不同的view
     * @param position
     * @return
     */
    public int getItemViewType(int position) {
        if (mHeaderView == null && mFooterView == null) {
            return TYPE_NORMAL;
        }
        if(position ==0){
            //第一个item应该加载Header
            return  TYPE_HEADER;
        }
        if(position == getItemCount()-1){
            //最后一个,应该加载Footer
            return  TYPE_FOOTER;
        }
        return TYPE_NORMAL;
    }
    //创建View，如果是HeaderView或者是FooterView，直接在Holder中返回
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mHeaderView != null && viewType == TYPE_HEADER) {
            return new ViewHolder(mHeaderView);
        }
        if (mFooterView != null && viewType == TYPE_FOOTER) {
            return new ViewHolder(mFooterView);
        }
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.subscription_recycleview_item, parent, false);
        return new ViewHolder(layout);
    }

    /**
     * //绑定View，这里是根据返回的这个position的类型，从而进行绑定的，   HeaderView和FooterView, 就不同绑定了
     * @param holder
     * @param position
     */
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_NORMAL) {
            if (holder instanceof ViewHolder) {
                ((ViewHolder) holder).title.setText(datas.get(position-1).getTitle());
                ((ViewHolder) holder).digest.setText(datas.get(position-1).getDigest());
                ((ViewHolder) holder).num.setText(datas.get(position-1).getNum()+" 订阅");
                ((ViewHolder) holder).checkBox.setChecked(datas.get(position-1).isSubscription());
                return;
            }
        } else if (getItemViewType(position) == TYPE_HEADER) {
            if (holder instanceof ViewHolder) {

                ((ViewHolder) holder).banner.setBannerTitles(titles);
                ((ViewHolder) holder).banner.setImages(images).setImageLoader(new GlideImageLoader()).start();
                return;
            }
        }else{
            return;
        }
    }

    /**
     * 返回View中Item的个数，这个时候，总的个数应该是ListView中Item的个数加上HeaderView和FooterView
     * @return
     */
    public int getItemCount() {
        if (mHeaderView == null && mFooterView == null) {
            return datas.size();
        } else if (mHeaderView == null && mFooterView != null) {
            return  datas.size()+1;
        } else if (mHeaderView != null && mFooterView == null) {
            return datas.size()+1;
        }else{
            return datas.size()+2;
        }
    }



    public  class ViewHolder extends RecyclerView.ViewHolder  {
        ImageView img;//图片
        TextView title,digest,num;//标题,描述,订阅量
        CheckBox checkBox;//订阅按钮
        Banner banner;//头部轮播图
        public ViewHolder(View itemView) {
            super(itemView);
            //如果是headerview或者是footerview,直接返回
             if(itemView == mHeaderView){
                 banner= (Banner) itemView.findViewById(R.id.news_model_header_banner);
                 Log.d("weq","banner---:"+banner);
                 //轮播图的样式   设置为指示器+标题
                 banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
                 //设置指示器的位置
                 banner.setIndicatorGravity(BannerConfig.RIGHT);
                 return;
             }
            if (itemView == mFooterView) {
                return;
            }
            img= (ImageView) itemView.findViewById(R.id.subscription_recycleview_item_img);
            title= (TextView) itemView.findViewById(R.id.subscription_recycleview_item_title);
            digest= (TextView) itemView.findViewById(R.id.subscription_recycleview_item_digest);
            num= (TextView) itemView.findViewById(R.id.subscription_recycleview_item_num);
            checkBox= (CheckBox) itemView.findViewById(R.id.subscription_recycleview_item_checkbox);

        }


    }

}
