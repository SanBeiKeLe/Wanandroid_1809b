package com.wanghongli.myapplication.Wx.model;

import android.database.sqlite.SQLiteDoneException;

import androidx.annotation.IntDef;

import com.trello.rxlifecycle2.LifecycleProvider;
import com.wanghongli.myapplication.R;
import com.wanghongli.myapplication.Wx.contract.WxContract;
import com.wanghongli.myapplication.base.BaseRepository;
import com.wanghongli.myapplication.base.IBaseCallBack;
import com.wanghongli.myapplication.base.ServerException;
import com.wanghongli.myapplication.data.entity.ArticleData;
import com.wanghongli.myapplication.data.entity.Banner;
import com.wanghongli.myapplication.data.entity.HttpResult;
import com.wanghongli.myapplication.data.entity.WxArticleBean;
import com.wanghongli.myapplication.data.entity.WxTabBean;
import com.wanghongli.myapplication.data.okhttp.WADataService;
import com.wanghongli.myapplication.home.contract.HomeContract;
import com.wanghongli.myapplication.home.model.HomeModel;
import com.wanghongli.myapplication.home.model.HomeModel2;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class WxModel2 extends BaseRepository implements WxContract.Model {
    private WxContract.DataSource mRemote;
    private WxContract.DataSource mLocal;

    public WxModel2(WxContract.DataSource remote, WxContract.DataSource local) {
        mRemote = remote;
        mLocal = local;
    }

    @Override
    public void getTabData(LifecycleProvider provider, int type, IBaseCallBack<List<WxTabBean>> callBack) {
        Observable<List<WxTabBean>> localObservable = null;
        Observable<List<WxTabBean>> remoteObservable = mRemote.getTab();

        //如果是第一次需要去缓存
        if (type == HomeModel.HomeLoadType.LOAD_TYPE_LOAD && mLocal != null) {
            localObservable = mLocal.getTab();
        }


        process(provider, localObservable, remoteObservable, new Consumer<List<WxTabBean>>() {
            @Override
            public void accept(List<WxTabBean> wxTabBean) throws Exception {
                if (mLocal != null) {
                    mLocal.saveTab(wxTabBean);
                }
            }
        }, callBack);

    }

    @Override
    public void getWxArticleData(LifecycleProvider provider, int id, int type, int page, IBaseCallBack<WxArticleBean> callBack) {

        Observable<WxArticleBean> localObservable = null;
        Observable<WxArticleBean> remoteObservable = mRemote.getWxArticles(id,page);
        if (type == HomeModel.HomeLoadType.LOAD_TYPE_LOAD && mLocal != null) {
            localObservable = mLocal.getWxArticles(id,page);
        }
        process(provider, localObservable, remoteObservable, new Consumer<WxArticleBean>() {
            @Override
            public void accept(WxArticleBean wxArticleBean) throws Exception {
                if (mLocal != null) {
                    mLocal.saveNormalArticles(wxArticleBean);
                }
            }
        }, callBack);

    }

    @Override
    public void loadMoreArticles(LifecycleProvider provider, int type, int page, IBaseCallBack<WxArticleBean> callBack) {
        process(provider, null, mRemote.loadMoreArticles(page), null, callBack);
    }

    private <R> void process(LifecycleProvider provider, Observable<R> localObservable, Observable<R> remoteObservable, Consumer<R> doOnNext, IBaseCallBack<R> callBack) {

        if (mLocal != null && doOnNext != null) {
            // 服务器数据如果加载成功，不管是第一次还是刷新，再回调各 presenter 时先保存到本地，

            remoteObservable = remoteObservable.doOnNext(doOnNext);

        }

        //通过 concat 连接操作符，实现 本地有数据先读本地，等本地读取完成后，再读取服务器，包装本地一定发发再服务器之前。
        //Observable<R> resultObservable = localObservable == null ? remoteObservable : Observable.concat(localObservable, remoteObservable);

        Observable<R> resultObservable = null;

        if (localObservable == null) {
            resultObservable = remoteObservable;
        } else {
            resultObservable = Observable.concat(localObservable, remoteObservable).firstOrError().toObservable();
        }
        observerNoMap(provider, resultObservable, callBack);

    }
}
