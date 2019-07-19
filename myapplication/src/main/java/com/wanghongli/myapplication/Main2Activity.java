package com.wanghongli.myapplication;

import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.wanghongli.myapplication.Wx.WxFragment;
import com.wanghongli.myapplication.base.BaseActivity;
import com.wanghongli.myapplication.home.HomeFragment;
import com.wanghongli.myapplication.home.HomeFragment1;
import com.wanghongli.myapplication.home.model.HomeModel;
import com.wanghongli.myapplication.navigation.NavigationFragment;
import com.wanghongli.myapplication.todaynews.TodayNews;
import com.wanghongli.myapplication.wxtwo.WxTwoFragment;

public class Main2Activity extends BaseActivity {

    private FrameLayout mFragmentContiner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        initView();
    }

    private void initView() {
        mFragmentContiner = (FrameLayout) findViewById(R.id.fragment_continer);
//        addFragment(getSupportFragmentManager(), HomeFragment.class,R.id.fragment_continer,null);
//        addFragment(getSupportFragmentManager(), WxFragment.class,R.id.fragment_continer,null);
        addFragment(getSupportFragmentManager(), NavigationFragment.class,R.id.fragment_continer,null);


    }
}
