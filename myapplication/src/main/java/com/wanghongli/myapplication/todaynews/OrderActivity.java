package com.wanghongli.myapplication.todaynews;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.wanghongli.myapplication.R;
import com.wanghongli.myapplication.WAApplication;
import com.wanghongli.myapplication.wxtwo.MyApplication;
import com.wanghongli.myapplication.wxtwo.TabBean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OrderActivity extends AppCompatActivity {

    private RecyclerView mRlv;
    private RecyclerView mRlv2;
    private TodayNewsRlvAdapter mAdapter;
    private ItemTouchHelper mItemTouchHelper;
    private List<TabBean> mTabBeans;
    private int mPosition;
    private ArrayList<String> mData;
    private static final String TAG = "OrderActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        Intent intent = getIntent();
        mData = intent.getStringArrayListExtra("data");
        Log.d(TAG, "onCreate: "+mData);
        initView();


    }

    private void initView() {
        mRlv = findViewById(R.id.rlv);
        mRlv2 = findViewById(R.id.rlv2);
        mRlv.setLayoutManager(new GridLayoutManager(this, 4));
        mAdapter = new TodayNewsRlvAdapter();
        for (int i = 0; i < mData.size(); i++) {
            mAdapter.list.get(i).setName(mData.get(i));
        }
        mRlv.setAdapter(mAdapter);
        ArrayList<TabBean> disLike = new ArrayList<>();
        ArrayList<TabBean> like = new ArrayList<>();

        mAdapter.setListener(new TodayNewsRlvAdapter.setListen() {

            @Override
            public void setOnClickListen(TabBean tabBean, int position) {
                mPosition = position;
                //点击下面+1
                // 再点击下面-1  （选择用集合做）
                boolean isLike = tabBean.isLike;
                if (tabBean.isLike) {
                    tabBean.isLike = !isLike;
                }
                mAdapter.list.remove(position);
                like.add(tabBean);

                mTabBeans.remove(tabBean);
                //haha
                Log.d(TAG, "setOnClickListen:嘻嘻嘻 ");
            }

            @Override
            public void setOnLongClickListen(TabBean tabBean, int position) {
              /*  //修改数据库
                mTabBeans = MyApplication.getmDaoSession().getTabBeanDao().loadAll();
                mTabBeans.set(position, tabBean);


                ArrayList<TabBean> list1 = mAdapter.list;
                list1.set(position, tabBean);
                if (mTabBeans != list1) {
                    //传回去
                }
                mAdapter.notifyDataSetChanged();*/
            }
        });
        mItemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
//                这个方法是设置是否滑动时间，以及拖拽的方向，所以在这里需要判断一下是列表布局还是网格布局，如果是列表布局的话则拖拽方向为DOWN和UP，如果是网格布局的话则是DOWN和UP和LEFT和RIGHT，对应这个方法的代码如下
                if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
                    final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN |
                            ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
                    final int swipeFlags = 0;
                    return makeMovementFlags(dragFlags, swipeFlags);
                } else {
                    final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                    final int swipeFlags = 0;
                    return makeMovementFlags(dragFlags, swipeFlags);
                }
            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
//                我们在拖动的时候不断回调的方法，在这里我们需要将正在拖拽的item和集合的item进行交换元素，然后在通知适配器更新数据
                //得到当拖拽的viewHolder的Position
               /*  int fromPosition = viewHolder.getAdapterPosition();
                //拿到当前拖拽到的item的viewHolder
               int toPosition = target.getAdapterPosition();
                if (fromPosition < toPosition) {
                    for (int i = fromPosition; i < toPosition; i++) {
                        Collections.swap(datas, i, i + 1);
                    }
                } else {
                    for (int i = fromPosition; i > toPosition; i--) {
                        Collections.swap(datas, i, i - 1);
                    }
                }
                myAdapter.notifyItemMoved(fromPosition, toPosition);

                return true;*/
                int fromPosition = viewHolder.getAdapterPosition();
                int toPosition = viewHolder1.getAdapterPosition();
                if (fromPosition < toPosition) {
                    for (int i = fromPosition; i < toPosition; i++) {
                        Collections.swap(mTabBeans, i, i - 1);
                    }
                } else {
                    for (int i = fromPosition; i > toPosition; i--) {
                        Collections.swap(mTabBeans, i, i - 1);
                    }
                }
                //直接  查询数据库
                List<TabBean> tabBeans = WAApplication.getmDaoSession().getTabBeanDao().loadAll();
                mAdapter.list.addAll(tabBeans);

                mAdapter.notifyDataSetChanged();
                //传过去  todo

                return true;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

            }
        });
        mItemTouchHelper.attachToRecyclerView(mRlv);


        mRlv2.setLayoutManager(new GridLayoutManager(this, 4));
        mAdapter = new TodayNewsRlvAdapter();
        mAdapter.list.addAll(disLike);
        mRlv2.setAdapter(mAdapter);
        mAdapter.setListener(new TodayNewsRlvAdapter.setListen() {
            @Override
            public void setOnClickListen(TabBean tabBean, int position) {
                //点击下面+1
                // 再点击下面-1  （选择用集合做）
//                再点击的话上面+1  下面-1  如果下面==0就Gone  如果大于0 就显示
                mTabBeans.set(mPosition,tabBean);
                like.remove(tabBean);
                tabBean.isLike = true;

            }

            @Override
            public void setOnLongClickListen(TabBean tabBean, int position) {

            }
        });


    }
}
