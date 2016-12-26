package com.youjia.newsway.fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.youjia.newsway.R;
import com.youjia.newsway.activity.ChooseLoginActivity;
import com.youjia.newsway.activity.IntegralCenterActivity;
import com.youjia.newsway.activity.IntegralMallActivity;
import com.youjia.newsway.activity.IntegralSettingActiviy;
import com.youjia.newsway.activity.MineNewsActivity;
import com.youjia.newsway.activity.MineSubscriptionActivity;
import com.youjia.newsway.activity.UserinfoActivity;
import com.youjia.newsway.adapter.MineListviewAdapter;
import com.youjia.newsway.review.RoundedImageView;
import com.youjia.newsway.tools.L;
import com.youjia.newsway.tools.SPUtils;

import java.util.ArrayList;
import java.util.List;

public class MineFragment extends Fragment implements View.OnClickListener,AdapterView.OnItemClickListener {


	View rootView;
	TextView integralCenter,//积分中心
			integralMall,//积分商城
			integralSignIn,//积分签到
			integtralSetting;//积分设置
    MineListviewAdapter adapter;
    List<String> datas;
    ListView listView;
    RoundedImageView roundedImageView;//圆形图片

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_mine, null);
		findViewById();
        setListViewInfo();
		return rootView;
	}

    /**
     * 给listview绑定数据
     */
    private void setListViewInfo() {
        if (datas == null) {
            datas=new ArrayList<String>();
            datas.add("我的消息");
            datas.add("我的订阅");
            datas.add("我的收藏");
            datas.add("夜间模式");
            datas.add("邀请好友");
            datas.add("反馈与帮助");
            adapter=new MineListviewAdapter(datas,getActivity());
        }
        listView.setAdapter(adapter);
    }


    /**
	 * 绑定组件
	 */
	private void findViewById() {
		integralCenter= (TextView) rootView.findViewById(R.id.mine_integralCenter);
		integralMall= (TextView) rootView.findViewById(R.id.mine_integralMall);
		integralSignIn= (TextView) rootView.findViewById(R.id.mine_integralSignIn);
		integtralSetting= (TextView) rootView.findViewById(R.id.mine_integralSetting);
        listView= (ListView) rootView.findViewById(R.id.mine_listview);
        roundedImageView= (RoundedImageView) rootView.findViewById(R.id.mine_toUserInfo);

		integralCenter.setOnClickListener(this);
		integralMall.setOnClickListener(this);
		integralSignIn.setOnClickListener(this);
		integtralSetting.setOnClickListener(this);
        roundedImageView.setOnClickListener(this);
        listView.setOnItemClickListener(this);
        roundedImageView.setOnClickListener(this);
	}

    /**
     * 碎片的隐藏与显示
     * @param menuVisible
     */
	public void setMenuVisibility(boolean menuVisible) {
		// TODO Auto-generated method stub
		super.setMenuVisibility(menuVisible);
		if (this.getView() != null)
			this.getView()
					.setVisibility(menuVisible ? View.VISIBLE : View.GONE);
	}

	@Override
	public void onClick(View v) {
        Intent intent=null;
		switch (v.getId()) {
			case R.id.mine_integralCenter:
                intent = new Intent(getActivity(), IntegralCenterActivity.class);
                break;
			case R.id.mine_integralMall:
                intent = new Intent(getActivity(), IntegralMallActivity.class);
				break;
			case R.id.mine_integralSignIn:

				break;
			case R.id.mine_integralSetting:
                intent = new Intent(getActivity(), IntegralSettingActiviy.class);
				break;
            case R.id.mine_toUserInfo:
                int uid= (int) SPUtils.get(getActivity(),"uid",-1);
                L.d("uid:"+uid);
				if (uid!=-1){//已经登陆过
					intent=new Intent(getActivity(), UserinfoActivity.class);

				}else{//未登录过
					intent=new Intent(getActivity(), ChooseLoginActivity.class);
				}

                break;

		}
        if (intent != null) {
            getActivity().startActivity(intent);
        }
    }

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent=null;

		switch (position){
			case 0:
				intent=new Intent(getActivity(), MineNewsActivity.class);
				break;
			case 1:
				intent=new Intent(getActivity(), MineSubscriptionActivity.class);
				break;

		}

        if (intent!=null) {

            getActivity().startActivity(intent);
        }
	}
}
