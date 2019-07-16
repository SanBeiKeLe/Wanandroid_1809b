package com.wanghongli.myapplication.todaynews;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wanghongli.myapplication.R;
import com.wanghongli.myapplication.wxtwo.TabBean;

import java.util.ArrayList;

public class TodayNewsRlvAdapter extends RecyclerView.Adapter {
    public ArrayList<TabBean> list = new ArrayList<>();
    private setListen mListen;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.a, parent, false);

        return new RlvviewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        RlvviewHolder holder1 = (RlvviewHolder) holder;
        TabBean tabBean = list.get(position);
        holder1.mTv.setText(tabBean.getName());
        tabBean.isLike = false;
        holder1.mTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListen != null) {
                    tabBean.isLike=true;
                    mListen.setOnClickListen(tabBean, position);
                }
            }
        });
        holder1.mTv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mListen != null) {
                    mListen.setOnLongClickListen(tabBean, position);
                }
                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class RlvviewHolder extends RecyclerView.ViewHolder {

        private final TextView mTv;

        public RlvviewHolder(@NonNull View itemView) {
            super(itemView);
            mTv = itemView.findViewById(R.id.tv);

        }
    }

    public interface setListen {
        void setOnClickListen(TabBean tabBean, int position);

        void setOnLongClickListen(TabBean tabBean, int position);
    }

    public void setListener(setListen listen) {

        mListen = listen;
    }
}
