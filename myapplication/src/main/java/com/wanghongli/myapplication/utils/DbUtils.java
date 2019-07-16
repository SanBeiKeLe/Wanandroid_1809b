package com.wanghongli.myapplication.utils;

import android.util.Log;

import com.com.wanghongli.myapplication.dao.DaoSession;
import com.com.wanghongli.myapplication.dao.TabBeanDao;
import com.wanghongli.myapplication.WAApplication;
import com.wanghongli.myapplication.wxtwo.MyApplication;
import com.wanghongli.myapplication.wxtwo.TabBean;

import java.util.List;

public class DbUtils {
    private static  DaoSession daoSession = WAApplication.getmDaoSession();
    private static final String TAG = "DbUtils";
    //增删改查
    public static void insertItem(TabBean tabBean){
        if (selectItem(tabBean)==null){
            TabBeanDao tabBeanDao = daoSession.getTabBeanDao();
            tabBeanDao.insert(tabBean);
        }else {
            Log.d(TAG, "insertItem:数据已经存在 ");
        }
    }

    public static void deleteItem(TabBean tabBean){

        if (selectItem(tabBean)!=null){
            TabBeanDao tabBeanDao = daoSession.getTabBeanDao();
            tabBeanDao.delete(tabBean);
        }else {
            Log.d(TAG, "deleteItem:数据不存在 ");
        }
    }
    public static TabBean selectItem(TabBean tabBean){
        TabBeanDao tabBeanDao = daoSession.getTabBeanDao();
        TabBean unique = tabBeanDao.queryBuilder()
                .where(TabBeanDao.Properties.Id.eq(tabBean.getId()))
                .build()
                .unique();
        return unique;
    }
    public static List<TabBean> selectAll(){
        TabBeanDao tabBeanDao = daoSession.getTabBeanDao();
        List<TabBean> tabBeans = tabBeanDao.loadAll();
        return tabBeans;
    }
}
