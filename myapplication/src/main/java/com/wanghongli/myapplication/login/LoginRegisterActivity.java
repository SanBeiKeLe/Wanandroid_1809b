package com.wanghongli.myapplication.login;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.wanghongli.myapplication.R;
import com.wanghongli.myapplication.base.BaseActivity;


public class LoginRegisterActivity extends BaseActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login_register);


        addFragment(getSupportFragmentManager(), LoginFragment.class, R.id.login_fragment_container, null);
    }
}
