package com.wanghongli.myapplication.wxtwo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wanghongli.myapplication.R;
import com.wanghongli.myapplication.base.BaseActivity;

import java.util.ArrayList;
import java.util.Collections;

public class TabActivity extends BaseActivity {

    private RecyclerView mRlv;
    private ItemTouchHelper mItemTouchHelper;
    private TabRlvAdapter mAdapter;
    private ArrayList<TabBean> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        mData = intent.getParcelableArrayListExtra("data");
        setContentView(R.layout.activity_tab);
        initView();
        //判断修改之前的集合和修改后的集合，有变动的话修改数据库，fragment 里  发送消息EventBus  查询数据库，刷新）
//        todo


    }

    private void initView() {
        mRlv = (RecyclerView) findViewById(R.id.rlv);
        mRlv.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new TabRlvAdapter();
        mAdapter.mList.addAll(mData);

        mRlv.setAdapter(mAdapter);
        mRlv.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));

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
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
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
                int toPosition = target.getAdapterPosition();
                if (fromPosition < toPosition) {
                    for (int i = fromPosition; i < toPosition; i++) {
                        Collections.swap(mAdapter.mList, i, i - 1);
                    }
                } else {
                    for (int i = fromPosition; i > toPosition; i--) {
                        Collections.swap(mAdapter.mList, i, i - 1);
                    }
                }
                mAdapter.notifyDataSetChanged();

                return true;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            }
        });

        mItemTouchHelper.attachToRecyclerView(mRlv);
    }
}
