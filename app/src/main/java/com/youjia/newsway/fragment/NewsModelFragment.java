package com.youjia.newsway.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.youjia.newsway.R;
import com.youjia.newsway.adapter.NewsXlistviewAdapter;
import com.youjia.newsway.application.AppApplication;
import com.youjia.newsway.bean.BannerModel;
import com.youjia.newsway.bean.NewsEntity;
import com.youjia.newsway.bean.NewsModel;
import com.youjia.newsway.dao.RequestSerives;
import com.youjia.newsway.review.XListView;
import com.youjia.newsway.tools.GlideImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static com.youjia.newsway.tools.Urls.news;


public class NewsModelFragment extends Fragment {
	
	public static final int LOADMORE_OVER = 0;// 上拉加载下一页完成
	public static final int REFISH_OVER = 1;// 下拉刷新完成
	public final static int SET_NEWSLIST = 0;
	View rootView;
	XListView xListView;//新闻列表
	ViewPager xListViewHeader;//新闻列表的头部
	View headerNews, vpNewsOne, vpNewsTwo;	// 头部幻灯片
	RelativeLayout loadingContent;// 加载动画页面
	ImageView loadingImg;// 加载旋转动画图片
	ArrayList<NewsEntity> newsList = new ArrayList<NewsEntity>();//新闻列表的
	NewsXlistviewAdapter adapter;//新闻列表适配器
	/**
	 * 对应栏目的名字
	 */
	String text;
	/**
	 * 对应栏目的id
	 */
	int channel_id;
	/**
	 * 轮播图
	 */
	Banner banner;
	/**
	 * 网络链接
	 */
	private  Retrofit retrofit;
	private RequestSerives requestSerives;
	private ArrayList<NewsModel> newsDatas;//新闻列表数据源
	Call<String> call;
	ArrayList<BannerModel> bannerModels;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView=inflater.inflate(R.layout.fragment_newsmodel,null);
		initRotateAnimation();
		findViewById();
		
		return rootView;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		Bundle args = getArguments();
		text = args != null ? args.getString("text") : "";
		channel_id = args != null ? args.getInt("id", 0) : 0;
		super.onCreate(savedInstanceState);
	}
	
	/**
	 * 绑定将要展示的数据
	 */
	private void initData() {
		//newsList= Constants.getNewsList();
		if (adapter == null) {
			adapter=new NewsXlistviewAdapter(getActivity(),newsDatas);
		}
		xListView.setAdapter(adapter);

		xListView.setVisibility(View.VISIBLE);// 显示列表
		loadingImg.clearAnimation();// 取消动画
		loadingContent.setVisibility(View.INVISIBLE);// 隐藏动画
        Log.d("weq","进入取消动画");
	}

	/**
	 * 绑定组件
	 */
	private void findViewById() {
		xListView=(XListView) rootView.findViewById(R.id.news_model_xListView);
		headerNews=getActivity().getLayoutInflater().inflate(R.layout.header_news,null);
		banner= (Banner) headerNews.findViewById(R.id.news_model_header_banner);

		initRetrofit();

        xListView.addHeaderView(headerNews);
        // 添加数据



	}

	private void initRetrofit() {
		 retrofit = new Retrofit.Builder()
				.baseUrl(news)
				//增加返回值为String的支持
				.addConverterFactory(ScalarsConverterFactory.create())
				//增加返回值为Gson的支持(以实体类返回)
				.addConverterFactory(GsonConverterFactory.create())
				//增加返回值为Oservable<T>的支持
				.addCallAdapterFactory(RxJavaCallAdapterFactory.create())
				.build();
		 requestSerives=retrofit.create(RequestSerives.class);
	}

	/**
     * 添加数据
     * @param isNextPage
     */
    private void getDatas(boolean isNextPage) {
		// 获取当前资讯页viewpager的当前页码
		int newspage = AppApplication.getApp().getNewspage();
		NewsModel modle = new NewsModel();
		System.out.println("newspage" + newspage);


		if (newsDatas == null) {
			newsDatas = new ArrayList<NewsModel>();
			bannerModels=new ArrayList<BannerModel>();
		}
        newsDatas.clear();
		/*if (isNextPage == false) {// isNextPage为false时加载第一页
			newsDatas.clear();
			if (newspage % 2 == 1) {
				modle.setPageIndex(1);
				modle.setTypeId("2");
			} else {
				modle.setPageIndex(1);
				modle.setTypeId("1");
			}
		} else {// isNextPage为true时加载第二页
			if (newspage % 2 == 1) {
				modle.setPageIndex(2);
				modle.setTypeId("2");
			} else {
				modle.setPageIndex(2);
				modle.setTypeId("1");
			}
		}*/
        Log.d("weq",text);
		/*if("最新".equals(text)){
            call=requestSerives.getPlayNews();
		} else if ("排行".equals(text)) {
			call=requestSerives.getGameNews();
		}else{
            call=requestSerives.getHotNews();
		}*/
        call=requestSerives.getHotNews();

		call.enqueue(new Callback<String>() {
			@Override
			public void onResponse(Call<String> call, Response<String> response) {
				JSONObject jsonObject=null;
				JSONArray jsonArray=null;
				try {
					Log.d("weq",response.body().toString());
					 jsonObject=new JSONObject(response.body().toString());
					/*if("最新".equals(text)){
                        jsonArray=jsonObject.getJSONArray("T1348648517839");

					} else if ("排行".equals(text)) {
						jsonArray=jsonObject.getJSONArray("T1348654151579");
					}else{
                        jsonArray=jsonObject.getJSONArray("T1348647853363");
					}*/
                    jsonArray=jsonObject.getJSONArray("T1348647853363");
					int lenght=jsonArray.length();
					JSONObject newsObject;
					NewsModel newsModel;
					for (int i=0;i<lenght;i++){
						newsObject=jsonArray.getJSONObject(i);
						if (i == 0) {
							JSONArray bannerArray=newsObject.getJSONArray("ads");
							for(int j=0;j<bannerArray.length();j++){
								JSONObject bannerObject=bannerArray.getJSONObject(j);
								BannerModel bannerModel=new BannerModel();
								bannerModel.setTitel(bannerObject.getString("title"));
								bannerModel.setImgPath(bannerObject.getString("imgsrc"));
								bannerModels.add(bannerModel);
							}
							initBanner();
						}else{
                            newsModel=new NewsModel();
                            newsModel.setTitle(newsObject.getString("title"));
                            newsModel.setImg(newsObject.getString("imgsrc"));
                            newsModel.setDigest(newsObject.getString("digest"));
                            //newsModel.setLink(newsObject.getString("url"));
                            //Log.d("成功",newsObject.getString("url"));
                            newsDatas.add(newsModel);

						}
					}
                    initData();

				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(Call<String> call, Throwable t) {
				Log.d("weq","获取失败");
			}
		});
    }



    /**
	 * 初始化轮播图
	 */
	private void initBanner() {
//		String url0 = "http://childmusic.qiniudn.com/huandeng/1.png";
//		String url1 = "http://childmusic.qiniudn.com/huandeng/2.png";
//		String url2 = "http://childmusic.qiniudn.com/huandeng/3.png";
//		String url3 = "http://childmusic.qiniudn.com/huandeng/4.png";
//		String url4 = "http://childmusic.qiniudn.com/huandeng/5.png";
//		String url5 = "http://childmusic.qiniudn.com/huandeng/6.png";
//
//		String t1="第一条信息";
//		String t2="第er条信息";
//		String t3="第san条信息";
//		String t4="第si条信息";
//		String t5="第wu条信息";
//		String t6="第liu条信息";

		List<String> titles=new ArrayList<String>();
		List<String> images=new ArrayList<String>();
		for (BannerModel i : bannerModels) {
			titles.add(i.getTitel());
			images.add(i.getImgPath());
		}

//		titles.add(t1);
//		titles.add(t2);
//		titles.add(t3);
//		titles.add(t4);
//		titles.add(t5);
//		titles.add(t6);
//
//
//		images.add(url0);
//		images.add(url1);
//		images.add(url2);
//		images.add(url3);
//		images.add(url4);
//		images.add(url5);
		//轮播图的样式   设置为指示器+标题
		banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
		//设置指示器的位置
		banner.setIndicatorGravity(BannerConfig.RIGHT);
		//设置标题的文字
		banner.setBannerTitles(titles);
		//设置图片
		banner.setImages(images).setImageLoader(new GlideImageLoader()).start();
	}

	
	/**
	 * 加载动画的初始化
	 */
	public void initRotateAnimation() {
		loadingContent = (RelativeLayout) rootView
				.findViewById(R.id.loadingContent);
		loadingImg = (ImageView) rootView
				.findViewById(R.id.loadingContent_rotatingImg);
		RotateAnimation rotateAnimation = (RotateAnimation) AnimationUtils
				.loadAnimation(getActivity(), R.anim.rotating);
		LinearInterpolator lin = new LinearInterpolator();// 设置为匀速转动
		rotateAnimation.setInterpolator(lin);
		loadingImg.startAnimation(rotateAnimation);

	}

    /** 此方法意思为fragment是否可见 ,可见时候加载数据 */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser) {
            Log.d("---------weq","可见"+text);
            //fragment可见时加载数据
            if(newsDatas !=null && newsDatas.size() !=0){
                handler.obtainMessage(SET_NEWSLIST).sendToTarget();
            }else{
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        try {
                            Thread.sleep(2);
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        handler.obtainMessage(SET_NEWSLIST).sendToTarget();
                    }
                }).start();
            }
        }else{
            Log.d("---------weq","不可见"+text);
            //fragment不可见时不执行操作
        }
        super.setUserVisibleHint(isVisibleToUser);
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            switch (msg.what) {
                case SET_NEWSLIST:
                    getDatas(false);
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };
	
	

}
