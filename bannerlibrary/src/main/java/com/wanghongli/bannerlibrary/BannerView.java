package com.wanghongli.bannerlibrary;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

public class BannerView extends ConstraintLayout {

    private ViewPager mViewPager;
    private int mId;
    private ImageView mImageView;
    private TextView mTv_title;
    private MyIndicator mMyIndicator;
    private BannerView mBannerView;
    private BannerAdapter mBannerAdapter;
    // 用户显示banner 图片的Banner 对象list，也是可以使 图片url 集合
    private List<?> mDatas;

    // 显示title 的集合，可以为空
    private List<String> mTitles;
    private boolean mIsLoop;


    public BannerView(Context context) {
        super(context);
        initView();
    }


    public BannerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public BannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(this);

        mViewPager = new ViewPager(getContext());
        mViewPager.setId(mId++);
        constraintSet.connect(mViewPager.getId(), ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT);
        constraintSet.connect(mViewPager.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);
        constraintSet.connect(mViewPager.getId(), ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT);
        constraintSet.connect(mViewPager.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM);
        addView(mViewPager);


        mImageView = new ImageView(getContext());
        mImageView.setId(mId++);
        mImageView.setBackgroundColor(Color.parseColor("#cecece"));
        constraintSet.connect(mImageView.getId(), ConstraintSet.BOTTOM, mViewPager.getId(), ConstraintSet.BOTTOM);
        constraintSet.connect(mImageView.getId(), ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT);
        constraintSet.connect(mImageView.getId(), ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT);
        //设置宽高
        constraintSet.constrainWidth(mImageView.getId(), LayoutParams.MATCH_PARENT);
        constraintSet.constrainHeight(mImageView.getId(), BannerUtils.dp2px(getContext(), 33));
        addView(mImageView);

        mMyIndicator = new MyIndicator(getContext());
        mMyIndicator.setId(mId++);
        mTv_title = new TextView(getContext());
        mTv_title.setId(mId++);
        mTv_title.setText("你好啊");
        mTv_title.setTextColor(Color.WHITE);
        constraintSet.connect(mMyIndicator.getId(), ConstraintSet.BOTTOM, mViewPager.getId(), ConstraintSet.BOTTOM);
        constraintSet.connect(mMyIndicator.getId(), ConstraintSet.TOP, mImageView.getId(), ConstraintSet.TOP);

        constraintSet.connect(mMyIndicator.getId(), ConstraintSet.LEFT, mTv_title.getId(), ConstraintSet.RIGHT);
        constraintSet.connect(mMyIndicator.getId(), ConstraintSet.RIGHT, mViewPager.getId(), ConstraintSet.RIGHT);

        //设置宽高
        constraintSet.constrainWidth(mMyIndicator.getId(), LayoutParams.WRAP_CONTENT);
        constraintSet.constrainHeight(mMyIndicator.getId(), LayoutParams.WRAP_CONTENT);
        addView(mMyIndicator);


        constraintSet.connect(mTv_title.getId(), ConstraintSet.BOTTOM, mViewPager.getId(), ConstraintSet.BOTTOM);
        constraintSet.connect(mTv_title.getId(), ConstraintSet.TOP, mImageView.getId(), ConstraintSet.TOP);

        constraintSet.connect(mTv_title.getId(), ConstraintSet.LEFT, mImageView.getId(), ConstraintSet.LEFT);
        constraintSet.connect(mTv_title.getId(), ConstraintSet.RIGHT, mMyIndicator.getId(), ConstraintSet.LEFT);
        //设置宽高
        constraintSet.constrainWidth(mTv_title.getId(), LayoutParams.WRAP_CONTENT);
        constraintSet.constrainHeight(mTv_title.getId(), LayoutParams.WRAP_CONTENT);
        addView(mTv_title);

        constraintSet.applyTo(this);
    }

    public void setAdapter(BannerAdapter bannerAdapter) {
        mBannerAdapter = bannerAdapter;
        mViewPager.setAdapter(new InnerAdapter());

        if (mDatas != null && mDatas.size() > 1) {
            int i = Integer.MAX_VALUE / 2; // 取最大值的中间值
            int j = i % mDatas.size(); // 用中间值取余数
            if (j != 0) { // 如果余数不等于0
                i = (mDatas.size() - j) + i; // 用size - 余数，求出还是多少才能除size 取余等于0 ，然后再加到中间值，目的是为了让中间值除size 取余等于0
            }
            mViewPager.setCurrentItem(i); // 设置让viewpager 显示中间值，并且这个中间值除以 size 余数 为0
        }


        // 如果自动轮播
        if (mIsLoop) {
            mViewPager.postDelayed(mLooper, 1000);
        }

    }


//    int getIndicatorRadius() {
//        return mIndicatorRadius;
//    }
//
//    int getIndicatorSelectColor() {
//        return mIndicatorSelectColor;
//    }
//
//    int getIndicatorUnSelectColor() {
//        return mIndicatorUnSelectColor;
//    }
//
//    int getMaskMaxHeight() {
//        return (int) (mMaxTitleSize * MASK_HEIGHT_FACTOR);
//    }


    private Runnable mLooper = new Runnable() {
        @Override
        public void run() {
            int cr = mViewPager.getCurrentItem();
            mViewPager.setCurrentItem(++cr); // 每次切换一页page

            mViewPager.postDelayed(this, 1000); //继续下一次轮播
        }
    };

    public void setLoop(boolean b) {
        mIsLoop = b;
    }

    public void setData(List<?> banners, ArrayList<String> titles) {
        if (banners != null && titles != null) {

            //在两个list 都有值的情况下，如果他们的size 不相等，那么就抛异常。因为图片个数和title 个数对不上。
            if (banners.size() != titles.size()) {
                throw new IllegalArgumentException(" data size not equals title size");
            }
        }
        //设置指示器的个数
        mMyIndicator.setIndicatorCout(banners == null ? 0 : banners.size());
//        mMyIndicator.setIndicatorCout(banners == null ? 0 : banners.size());

        mDatas = banners;
        mTitles = titles;

    }

    public void setTextColor(int textColor) {
        mTv_title.setTextColor(textColor);


    }

    public void setTextSize(int unit,int textDefoultSize) {
        mTv_title.setTextSize(textDefoultSize);
        //重新计算
        switch (unit) {
            case TypedValue.COMPLEX_UNIT_PX: {
                FinalPermer.TextDefoultSize = Math.min(16, textDefoultSize);
                break;
            }
            case TypedValue.COMPLEX_UNIT_DIP: {
                FinalPermer.TextDefoultSize = Math.min(BannerUtils.dp2px(getContext(), FinalPermer.TextSize), textDefoultSize);
                break;
            }

            case TypedValue.COMPLEX_UNIT_SP: {
                FinalPermer.TextDefoultSize = Math.min(BannerUtils.dp2px(getContext(), FinalPermer.TextSize), FinalPermer.TextDefoultSize);
                break;
            }
            default: {
                FinalPermer.TextDefoultSize = Math.min(FinalPermer.TextSize,  FinalPermer.TextDefoultSize);
                break;
            }
        }

    }

    private class InnerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mDatas == null ? 0 : Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }
//        new ThreadPoolExecutor


        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {

            ImageView imageView = new ImageView(container.getContext());
            imageView.setLayoutParams(new ViewPager.LayoutParams());
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

            // 相当于代理模式，让开发者设置的adapter 来处理对banner 的数据填充
            if (mBannerAdapter != null) {
                // banner 里面不对图片加载做处理，所以通过回调让使用banner的开发者自己去加载图片，此处指需要把 imageView 和 数据等传给开发者就行
                mBannerAdapter.fillBannerItemData(BannerView.this, imageView, mDatas.get(position % mDatas.size()), position % mDatas.size());
            }


            container.addView(imageView);

            return imageView;

        }


        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

            container.removeView((View) object);
        }
    }

}
