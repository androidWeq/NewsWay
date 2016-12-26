package com.youjia.newsway.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.youjia.newsway.R;

/**
 * Created by Administrator on 2016/12/7.
 */

public class MineNoticeFragment extends Fragment {

    View rootView;
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        rootView=inflater.inflate(R.layout.fragment_mine_notice,null);
        return rootView;
    }
}
