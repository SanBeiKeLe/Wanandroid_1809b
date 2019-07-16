package com.wanghongli.myapplication.wxtwo.model;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import com.trello.rxlifecycle2.LifecycleProvider;
import com.wanghongli.myapplication.base.BaseRepository;
import com.wanghongli.myapplication.base.IBaseCallBack;
import com.wanghongli.myapplication.base.ServerException;
import com.wanghongli.myapplication.data.entity.Banner;
import com.wanghongli.myapplication.data.entity.HttpResult;
import com.wanghongli.myapplication.data.entity.WxArticleBean;
import com.wanghongli.myapplication.data.entity.WxTabBean;
import com.wanghongli.myapplication.data.okhttp.WADataService;
import com.wanghongli.myapplication.home.contract.HomeContract;
import com.wanghongli.myapplication.home.model.HomeModel;
import com.wanghongli.myapplication.home.model.HomeModel2;
import com.wanghongli.myapplication.utils.DbUtils;
import com.wanghongli.myapplication.wxtwo.DataBean;
import com.wanghongli.myapplication.wxtwo.TabBean;
import com.wanghongli.myapplication.wxtwo.contract.WxTwoContract;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import io.reactivex.internal.operators.observable.ObservablePublish;

public class WxTwoModel extends BaseRepository implements WxTwoContract.Model {

    @Override
    public void getTabData(LifecycleProvider provider, int type, IBaseCallBack<List<TabBean>> callBack) {
        /**/
        //判断数据缓存里有没有  如果没有请求网络，如果有则使用
        //一上来查询数据库  数据库有展示回调  同时网络请求  与之比较   相同不做修改  不同则返回数据
//                            数据库没有    请求网络   网络数据回来添加数据库做回调

        if (type== HomeModel.HomeLoadType.LOAD_TYPE_LOAD){
            setSync(provider, callBack);
        }else {
            observer(provider, WADataService.getService().getTab(), new Function<HttpResult<List<TabBean>>, ObservableSource<List<TabBean>>>() {
                @Override
                public ObservableSource<List<TabBean>> apply(HttpResult<List<TabBean>> tabBeanHttpResult) throws Exception {
                    if (tabBeanHttpResult!=null&&tabBeanHttpResult.data!=null){
                        return Observable.just(tabBeanHttpResult.data);
                    }
                    return Observable.error(new ServerException(tabBeanHttpResult.errorMsg));
                }
            }, callBack);
        }
    }

    @SuppressLint("StaticFieldLeak")
    private void setSync(LifecycleProvider provider, IBaseCallBack<List<TabBean>> callBack) {
        new AsyncTask<String,Integer,List<TabBean>>(){
            @Override
            protected List<TabBean> doInBackground(String... strings) {
                List<TabBean> tabBeans = DbUtils.selectAll();
                if (tabBeans!=null){
                    //不是第一次  有数据
                    return tabBeans;
                }
                return null;
            }

            @Override
            protected void onPostExecute(List<TabBean> tabBeans) {
                super.onPostExecute(tabBeans);
                if (tabBeans!=null&&tabBeans.size()>0){

                    callBack.onSuccess(tabBeans);
                }else {
                    observer(provider, WADataService.getService().getTab(), new Function<HttpResult<List<TabBean>>, ObservableSource<List<TabBean>>>() {
                        @Override
                        public ObservableSource<List<TabBean>> apply(HttpResult<List<TabBean>> tabBeanHttpResult) throws Exception {
                            if (tabBeanHttpResult!=null&&tabBeanHttpResult.data!=null){
                                return Observable.just(tabBeanHttpResult.data);
                            }
                            return Observable.error(new ServerException(tabBeanHttpResult.errorMsg));
                        }
                    }, callBack);
                }
            }
        };
    }

    @Override
    public void getWxArticleData(LifecycleProvider provider, int type, int id, int page, IBaseCallBack<WxArticleBean> callBack) {

    }

    @Override
    public void loadMoreArticles(LifecycleProvider provider, int type, int page, IBaseCallBack<WxArticleBean> callBack) {

    }
}
