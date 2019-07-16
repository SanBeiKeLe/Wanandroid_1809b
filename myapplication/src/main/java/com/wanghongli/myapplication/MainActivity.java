package com.wanghongli.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.wanghongli.myapplication.login.LoginRegisterActivity;
import com.wanghongli.myapplication.utils.SPUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        String cookie = SPUtils.getValue(AppConstant.LoginParamsKey.SET_COOKIE_KEY);

        if (!TextUtils.isEmpty(cookie)) {
            WAApplication.mIsLogin = true;
        } else {
            WAApplication.mIsLogin = false;
        }
        startActivity(new Intent(this, LoginRegisterActivity.class));
    }
}
