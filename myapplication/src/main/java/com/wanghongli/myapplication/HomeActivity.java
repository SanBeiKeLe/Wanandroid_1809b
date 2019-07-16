package com.wanghongli.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.RadioButton;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.wanghongli.myapplication.base.BaseActivity;
import com.wanghongli.myapplication.home.HomeFragment;
import com.wanghongli.myapplication.home.HomeFragment1;

import java.util.zip.Inflater;

public class HomeActivity extends BaseActivity {

    private Toolbar mToolbar;
    private FloatingActionButton mFab;
    /**
     * 首页
     */
    private RadioButton mMainButtonTabHome;
    /**
     * 知识体系
     */
    private RadioButton mMainButtonTabKnowledge;
    /**
     * 公众号
     */
    private RadioButton mMainButtonTabWechat;
    /**
     * 导航
     */
    private RadioButton mMainButtonTabNavigation;
    /**
     * 项目
     */
    private RadioButton mMainButtonTabProject;
    private NavigationView mNavView;
    private ButtonNavigationView mHomeButtonNavigationView;
    private DrawerLayout mDl;
    private FrameLayout mFragment_continer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

//        Handler handler = new Handler();

        initView();
    }


    private void initView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mFab = (FloatingActionButton) findViewById(R.id.fab);
        mMainButtonTabHome = (RadioButton) findViewById(R.id.main_button_tab_home);
        mMainButtonTabKnowledge = (RadioButton) findViewById(R.id.main_button_tab_knowledge);
        mMainButtonTabWechat = (RadioButton) findViewById(R.id.main_button_tab_wechat);
        mMainButtonTabNavigation = (RadioButton) findViewById(R.id.main_button_tab_navigation);
        mMainButtonTabProject = (RadioButton) findViewById(R.id.main_button_tab_project);
        mNavView = (NavigationView) findViewById(R.id.nav_view);
        mHomeButtonNavigationView = (ButtonNavigationView) findViewById(R.id.home_button_navigation_view);
        mFragment_continer = findViewById(R.id.fragment_continer);

        mDl = (DrawerLayout) findViewById(R.id.dl);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDl, mToolbar, R.string.app_name, R.string.app_name);
        mDl.addDrawerListener(toggle);
        toggle.syncState();

        addFragment(getSupportFragmentManager(), HomeFragment1.class, R.id.fragment_continer, null);

        mHomeButtonNavigationView.setonCheckChangeListener(new ButtonNavigationView.onCheckChangeListener() {
            @Override
            public void setOnCheckChangeListen(CompoundButton buttonView, boolean isChecked) {
                switch (buttonView.getId()) {
                    case R.id.main_button_tab_home:
                        break;
                    case R.id.main_button_tab_knowledge:
                        break;
                    case R.id.main_button_tab_wechat:
                        break;
                    case R.id.main_button_tab_navigation:
                        break;
                    case R.id.main_button_tab_project:
                        break;
                }
            }
        });
       /* mHomeButtonNavigationView.setonCheckChangeListener(new ButtonNavigationView.onCheckChangeListener() {
            @Override
            public void setOnCheckChangeListen(CompoundButton buttonView, boolean isChecked) {

            }
        });
*/

//        mToolbar.onInterceptTouchEvent()
//        Handler handler = new Handler();
//        handler.sendEmptyMessage(0);


    }


}
