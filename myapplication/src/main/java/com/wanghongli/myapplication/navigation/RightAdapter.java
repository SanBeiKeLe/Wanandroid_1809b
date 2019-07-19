package com.wanghongli.myapplication.navigation;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.trello.rxlifecycle2.LifecycleProvider;
import com.wanghongli.myapplication.R;
import com.wanghongli.myapplication.utils.SystemFacade;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.http.POST;

public class RightAdapter extends RecyclerView.Adapter {
    private ArrayList<DataBean> mList;
    private int mPosition;

    public RightAdapter(ArrayList<DataBean> list) {
        mList = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.right_fragmentitem, parent, false);
        return new RlvViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        mPosition = position;
    }

    @Override
    public int getItemCount() {
        return mList==null?0:mList.size();
    }

    public void setData(ArrayList<DataBean> list) {

        mList = list;
    }
    class RlvViewHolder extends RecyclerView.ViewHolder {

        private final TextView mTvTitle;
        private final TagFlowLayout mFlowLayout;

        public RlvViewHolder(@NonNull View itemView) {
            super(itemView);
            mTvTitle = itemView.findViewById(R.id.navigation_tv_right_title);
            mFlowLayout = itemView.findViewById(R.id.navigation_flow_layout_right_content);
            DataBean dataBean = mList.get(mPosition);
            if(TextUtils.isEmpty(dataBean.getName())){
                mTvTitle.setText("未知");
            }
            else{
                mTvTitle.setText( dataBean.getName());
            }


            if(SystemFacade.isListEmpty(dataBean.getArticles())){
                mFlowLayout.setVisibility(View.GONE);
            }else{
                mFlowLayout.setVisibility(View.VISIBLE);
            }

            mFlowLayout.setAdapter(new TagAdapter<DataBean.ArticlesBean>(dataBean.getArticles()) {

                @Override
                public View getView(FlowLayout parent, int position, DataBean.ArticlesBean articlesBean) {
                    TextView textView = new TextView(parent.getContext());
                    textView.setBackgroundResource(R.drawable.navigation_right_child_item_tag_bg);
                    int h = SystemFacade.dp2px(parent.getContext(), 10);
                    int w = SystemFacade.dp2px(parent.getContext(), 6);
                    textView.setPadding(w, h, w, h);

                    ViewGroup.MarginLayoutParams layoutParams = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    layoutParams.setMargins(w,w,w,w);

                    textView.setTextColor(SystemFacade.randomColor());

                    textView.setLayoutParams(layoutParams);

                    textView.setText(dataBean.getArticles().get(position).getTitle());

                    return textView;
                }
            });

        }
    }
}
