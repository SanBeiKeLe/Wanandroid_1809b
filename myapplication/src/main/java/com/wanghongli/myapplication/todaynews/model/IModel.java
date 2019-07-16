package com.wanghongli.myapplication.todaynews.model;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.util.Log;

import androidx.fragment.app.Fragment;

import com.trello.rxlifecycle2.LifecycleProvider;
import com.wanghongli.myapplication.WAApplication;
import com.wanghongli.myapplication.base.BaseRepository;
import com.wanghongli.myapplication.base.IBaseCallBack;
import com.wanghongli.myapplication.base.ServerException;
import com.wanghongli.myapplication.data.entity.HttpResult;
import com.wanghongli.myapplication.data.okhttp.WADataService;
import com.wanghongli.myapplication.todaynews.ArticlesData;
import com.wanghongli.myapplication.todaynews.DataBean;
import com.wanghongli.myapplication.todaynews.contract.IContract;
import com.wanghongli.myapplication.utils.Logger;
import com.wanghongli.myapplication.wxtwo.MyApplication;
import com.wanghongli.myapplication.wxtwo.TabBean;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

public class IModel extends BaseRepository implements IContract.Model {
    private static final String TAG = "IModel";
    @SuppressLint("StaticFieldLeak")
    @Override
    public void getTabData(LifecycleProvider provider, int type, IBaseCallBack<List<TabBean>> callBack) {
        //判断数据库有没  有 返回  请求网络做比较
        //               没有   请求网络  插入数据库
        List<TabBean> tabBeans = WAApplication.getmDaoSession().getTabBeanDao().loadAll();
        if (tabBeans != null&&tabBeans.size()>0) {
            callBack.onSuccess(tabBeans);
            //todo  异步请求网络

            new AsyncTask() {
                @Override
                protected Object doInBackground(Object[] objects) {
                    //比较
                    requestNetData(provider, callBack);

                    return null;
                }

                @Override
                protected void onPostExecute(Object o) {
                    super.onPostExecute(o);

                }
            }.execute();
//            Log.d(TAG, "getTabData: "+tabBeans1);

        } else {
            requestNetData(provider, callBack);

            //插入数据库


        }
    }

    private void requestNetData(LifecycleProvider provider, IBaseCallBack<List<TabBean>> callBack) {
        observer(provider, WADataService.getService().getTab(), new Function<HttpResult<List<TabBean>>, ObservableSource<List<TabBean>>>() {
            @Override
            public ObservableSource<List<TabBean>> apply(HttpResult<List<TabBean>> tabBeanHttpResult) throws Exception {
                if (tabBeanHttpResult != null && tabBeanHttpResult.data.size()>0&&tabBeanHttpResult.errorCode==0) {
                    WAApplication.getmDaoSession().getTabBeanDao().insertInTx(tabBeanHttpResult.data);
                    Logger.d("% 插入数据库  %  数据");
                    Log.d(TAG, "getTabData: 插入数据库OK");
                    return Observable.just(tabBeanHttpResult.data);
                }
                return Observable.error(new ServerException(tabBeanHttpResult.errorMsg));
            }
        }, callBack);

    }

    @Override
    public void getArticleData(LifecycleProvider provider, int type, int id, int page, IBaseCallBack<ArticlesData> callBack) {

    }

    @Override
    public void getLoadMoreArticleData(LifecycleProvider provider, int type, int page, IBaseCallBack<ArticlesData> callBack) {

    }
}
