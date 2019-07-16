package com.wanghongli.myapplication.wxtwo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wanghongli.myapplication.R;

import java.util.ArrayList;

public class TabRlvAdapter extends RecyclerView.Adapter {
    public ArrayList<TabBean> mList = new ArrayList<>();

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.a, parent, false);
        return new RlvViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        RlvViewHolder holder1 = (RlvViewHolder) holder;
        holder1.mTv.setText(mList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class RlvViewHolder extends RecyclerView.ViewHolder {

        private final TextView mTv;

        public RlvViewHolder(@NonNull View itemView) {
            super(itemView);
            mTv = itemView.findViewById(R.id.tv);
        }
    }
}
