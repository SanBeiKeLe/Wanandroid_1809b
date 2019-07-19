package com.wanghongli.myapplication.navigation.model;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import com.trello.rxlifecycle2.LifecycleProvider;
import com.wanghongli.myapplication.WAApplication;
import com.wanghongli.myapplication.base.BaseRepository;
import com.wanghongli.myapplication.base.IBaseCallBackWithCache;
import com.wanghongli.myapplication.base.ServerException;
import com.wanghongli.myapplication.data.entity.HttpResult;
import com.wanghongli.myapplication.data.okhttp.WADataService;
import com.wanghongli.myapplication.navigation.DataBean;
import com.wanghongli.myapplication.navigation.Navigation;
import com.wanghongli.myapplication.navigation.contract.INavigationContract;
import com.wanghongli.myapplication.utils.DataCacheUtils;
import com.wanghongli.myapplication.utils.SystemFacade;
import com.wanghongli.myapplication.wxtwo.MyApplication;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

@SuppressLint("StaticFieldLeak")
public class INavigationModel extends BaseRepository implements INavigationContract.Model {
    public static final String CACHE_FILE_NAME = "NAVIGATION";

    @Override
    public void getNavigationData(LifecycleProvider provider, IBaseCallBackWithCache<ArrayList<DataBean>> callback) {
        //异步请求
        new AsyncTask<Void, Void, ArrayList<DataBean>>() {
            @Override
            protected ArrayList<DataBean> doInBackground(Void... voids) {
                File cacheFile = SystemFacade.getExternalCacheDir(WAApplication.mApplicationContext, CACHE_FILE_NAME);
                if (cacheFile == null) {
                    return null;
                }
                if (!cacheFile.exists()) {
                    cacheFile.mkdir();
                }
                List<DataBean> navigationList = DataCacheUtils.getDataListFromFile(DataBean.class, cacheFile);
                if (!SystemFacade.isListEmpty(navigationList)) {
                    return (ArrayList<DataBean>) navigationList;
                }


                return null;
            }

            @Override
            protected void onPostExecute(ArrayList<DataBean> navigations) {
                super.onPostExecute(navigations);
                if (!SystemFacade.isListEmpty(navigations)) {
                    callback.onCacheBack(navigations, IBaseCallBackWithCache.TYPE_CACHE_PERSISTENT);
                }
                requestServer(provider, callback);
            }
        }.execute();
    }

    private void requestServer(LifecycleProvider provider, IBaseCallBackWithCache<ArrayList<DataBean>> callback) {
        callback.onStartRequestServer();
        observer(provider, WADataService.getService().getNavigation(), new Function<HttpResult<ArrayList<DataBean>>, ObservableSource<ArrayList<DataBean>>>() {
            @Override
            public ObservableSource<ArrayList<DataBean>> apply(HttpResult<ArrayList<DataBean>> arrayListHttpResult) throws Exception {

                if(arrayListHttpResult.errorCode == 0 && !SystemFacade.isListEmpty(arrayListHttpResult.data)) {
                    // 保存到到文件
                    File cacheFile = SystemFacade.getExternalCacheDir(WAApplication.mApplicationContext, CACHE_FILE_NAME);

                    ArrayList<Navigation> cacheData = (ArrayList<Navigation>) DataCacheUtils.getDataListFromFile(Navigation.class, cacheFile);

                    if (SystemFacade.isListEmpty(cacheData)) { // 如果之前没有缓存，所有直接缓存这次请求回来的数据
                        DataCacheUtils.saveDataToFile(arrayListHttpResult.data, cacheFile);
                    } else {// 之前有缓存，那么比较下，服务器和缓存数据是否还保持一致，如果没变化就不需要保持，有变化则需要
                        // 第一步先判断size 是否一样
                        if (cacheData.size() != arrayListHttpResult.data.size()) {
                            DataCacheUtils.saveDataToFile(arrayListHttpResult.data, cacheFile);
                        } else {
                            int index = -1;
                            for (DataBean navigation : arrayListHttpResult.data) {
                                index = cacheData.indexOf(navigation);
                                if (index == -1) { // 在本地找不到一个和服务器一样的 navigation，说明服务器和本地数据不一致
                                    DataCacheUtils.saveDataToFile(arrayListHttpResult.data, cacheFile);
                                    break;
                                }
                            }

                            if (index != -1) { // 说明本地和服务器一样。一次不需要要保持到本地，也不要让界面刷新
                                arrayListHttpResult.data.clear();
                            }

                        }
                    }

                    return Observable.just(arrayListHttpResult.data);
                }
                return Observable.error(new ServerException(arrayListHttpResult.errorMsg));
            }
        }, callback);
    }
}
