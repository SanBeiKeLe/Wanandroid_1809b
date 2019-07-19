package com.wanghongli.myapplication.navigation;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wanghongli.myapplication.R;
import com.wanghongli.myapplication.navigation.DataBean;

import java.util.ArrayList;
import java.util.OptionalLong;

public class LeftAdapger extends RecyclerView.Adapter {
    private ArrayList<DataBean> mDataList;
    private onClickListen mListen;
    private int oldPosition=-1;

    public LeftAdapger(ArrayList<DataBean> dataList) {
        mDataList = dataList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.left_rlvitem, parent, false);
        return new RlvViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        RlvViewHolder holder1 = (RlvViewHolder) holder;
        DataBean dataBean = mDataList.get(position);
        holder1.mNavigation_left_item_cb_title.setText(dataBean.getName());
        holder1.mNavigation_left_item_cb_title.setChecked(dataBean.isChecked);
        holder1.mNavigation_left_item_cb_title.setTag(position);


    }

    @Override
    public int getItemCount() {
        return mDataList == null ? 0 : mDataList.size();
    }

    public void setData(ArrayList<DataBean> navigations) {
        mDataList = navigations;
    }

    class RlvViewHolder extends RecyclerView.ViewHolder {

        private final CheckBox mNavigation_left_item_cb_title;

        public RlvViewHolder(@NonNull View itemView) {
            super(itemView);
            mNavigation_left_item_cb_title = itemView.findViewById(R.id.navigation_left_item_cb_title);
            mNavigation_left_item_cb_title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = (int) mNavigation_left_item_cb_title.getTag();

                    if (position==oldPosition){
                        //重复一直点AItem
                        mNavigation_left_item_cb_title.setChecked(true);
                        return;
                    }
                    //   DataBean dataBean = mDataList.get(position);
                  /* if (mListen!=null){
                        mListen.onSelect(position,dataBean);
                   }*/

                    if (oldPosition!=-1){
                        DataBean dataBean1 = mDataList.get(oldPosition);
                        dataBean1.setChecked(false);
                        notifyItemChanged(oldPosition);
                    }

                    mDataList.get(position).setChecked(true);
                    notifyItemChanged(position);
//                    select(position, true);

                    oldPosition=position;
                }
            });

        }
    }


    private void select(int position, boolean b) {
        DataBean dataBean = mDataList.get(position);
        dataBean.setChecked(!dataBean.isChecked);
        if (oldPosition!=-1){
            DataBean dataBean1 = mDataList.get(oldPosition);
            dataBean1.setChecked(false);
            //xzxz
            notifyItemChanged(oldPosition);
        }
        oldPosition=position;
        notifyItemChanged(position);
    }

    public interface onClickListen {

        void setOnLongClickListener(View view, int position);

        void onSelect(int position , DataBean navigation);
    }

    public void setonClickListen(onClickListen listen) {

        mListen = listen;
    }


}
