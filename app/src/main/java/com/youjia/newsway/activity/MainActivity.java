package com.youjia.newsway.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

import com.youjia.newsway.R;
import com.youjia.newsway.fragment.HomePageFragment;
import com.youjia.newsway.fragment.InquireFragment;
import com.youjia.newsway.fragment.MineFragment;
import com.youjia.newsway.fragment.OnthewayFragment;
import com.youjia.newsway.fragment.SubscriptionFragment;


public class MainActivity extends FragmentActivity {

    RadioGroup radioGroup;
    FrameLayout frameLayout;
    FragmentStatePagerAdapter statePagerAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        initFragmentAdapter();
        initFragment();
    }

    private void initFragmentAdapter() {
        frameLayout = (FrameLayout) findViewById(R.id.main_fragHome);
        statePagerAdapter = new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            public int getCount() {
                return 4;
            }

            public Fragment getItem(int arg0) {
                switch (arg0) {
                    case 0:
                        return new HomePageFragment();
                    case 1:
                        return new SubscriptionFragment();
                    case 2:
                        return new OnthewayFragment();
                    case 3:
                        return new InquireFragment();
                    case 4:
                        return new MineFragment();
                    default:
                        return null;
                }
            }
        };

    }


    private void initFragment() {

        Fragment fragment = (Fragment) statePagerAdapter.instantiateItem(frameLayout, 0);
        statePagerAdapter.setPrimaryItem(frameLayout, 0, fragment);
        statePagerAdapter.finishUpdate(frameLayout);


        radioGroup = (RadioGroup) findViewById(R.id.main_raidoGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int index = 0;
                switch (checkedId) {
                    case R.id.main_rb1:
                        index = 0;
                        break;
                    case R.id.main_rb2:
                        index = 1;
                        break;
                    case R.id.main_rb3:
                        index = 2;
                        break;
                    case R.id.main_rb4:
                        index = 3;
                        break;
                    case R.id.main_rb5:
                        index = 4;
                        break;
                }
                Fragment fragment = (Fragment) statePagerAdapter.instantiateItem(frameLayout, index);
                statePagerAdapter.setPrimaryItem(frameLayout, 0, fragment);
                statePagerAdapter.finishUpdate(frameLayout);
            }
        });


    }


//    public void onWindowFocusChanged(boolean hasFocus) {
//        super.onWindowFocusChanged(hasFocus);
//        if (hasFocus && Build.VERSION.SDK_INT >= 19) {
//            View decorView = getWindow().getDecorView();
//            decorView.setSystemUiVisibility(
//                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                            | View.SYSTEM_UI_FLAG_FULLSCREEN
//                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
//        }
//    }
}

