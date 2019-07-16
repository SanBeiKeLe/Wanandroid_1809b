package com.wanghongli.myapplication.home;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.Scroller;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.wanghongli.bannerlibrary.BannerAdapter;
import com.wanghongli.bannerlibrary.BannerUtils;
import com.wanghongli.bannerlibrary.BannerView;
import com.wanghongli.bannerlibrary.FinalPermer;
import com.wanghongli.myapplication.R;
import com.wanghongli.myapplication.base.BaseFragment;
import com.wanghongli.myapplication.data.entity.Banner;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment1 extends BaseFragment {

    private TextView mHome_banner_title;
    private ViewPager mViewPager;
    private float mFactory;
    private boolean isManualScroll;
    private BannerIndicator mHome_banner_indicator;
    private List<Banner> mBanners;
    private BannerView mBannerView;
    private int textColor;


    @Override
    protected boolean isNeedToAddBackStack() {
        return false;
    }

    @Override
    public boolean isNeedAnimation() {
        return false;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_home1, container, false);
//        initView(inflate);
        return inflate;
    }

   /* private void initView(View inflate) {
        mBannerView = inflate.findViewById(R.id.bannerView);


        List<Banner> banners =  Banner.getBanners();
        mBannerView.setLoop(banners.size() > 1);

        ArrayList titles = new ArrayList();

        for(Banner banner : banners){

            titles.add(banner.getTitle());
        }
        mBannerView.setData(banners, titles);

        mBannerView.setAdapter(new BannerAdapter<Banner>() {
            @Override
            public void fillBannerItemData(BannerView banner, ImageView imageView, Banner mode, int position) {
                Glide.with(banner.getContext()).load(mode.getImgUrl()).into(imageView);
            }


        });
        textColor=Color.WHITE;
        mBannerView.setTextColor(FinalPermer.TextColor);
        FinalPermer.TextDefoultSize=16;
        mBannerView.setTextSize( FinalPermer.TextDefoultSize,FinalPermer.TextDefoultSize);
*///不知道啥玩意   todo   理解
       /* mBannerView.setAdapter(new com.wanghongli.bannerlibrary.BannerAdapter<Banner>() {
            @Override
            public void fillBannerItemData(BannerView banner, ImageView imageView, Banner mode, int position) {
                Glide.with(banner.getContext()).load(mode.getImgUrl()).into(imageView);
            }

        });*/



//        setViewPagerScroller();

    }

  /*  private void setViewPagerScroller() {
        try {
            Field scrollerField = ViewPager.class.getDeclaredField("mScroller");

            scrollerField.setAccessible(true);

            Field interpolator = ViewPager.class.getDeclaredField("sInterpolator");


            interpolator.setAccessible(true);

            Scroller scroller = new Scroller(getContext(), (Interpolator) interpolator.get(null)) {
                @Override
                public void startScroll(int startX, int startY, int dx, int dy, int duration) {
                    int newDuration;
                    if (isManualScroll) {
                        newDuration = duration;
                    } else {
                        newDuration = (int) ((duration * 4) * (1 - mFactory));
                    }
                    Log.d("Test", "duration = " + duration + " 修改后的 " + newDuration);
                    super.startScroll(startX, startY, dx, dy, newDuration);    // 这里是关键，将duration变长或变短
                }
            };
            scrollerField.set(mViewPager, scroller);
        } catch (NoSuchFieldException e) {
            // Do nothing.
        } catch (IllegalAccessException e) {
            // Do nothing.
        }
    }
    private Runnable loop = new Runnable() {
        @Override
        public void run() {
            int current  = mViewPager.getCurrentItem();
            mViewPager.setCurrentItem(++current, true);

            mViewPager.postDelayed(this, 3000);
        }
    };

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBanners = Banner.getBanners();

        mHome_banner_indicator.setIndicatorCout(mBanners.size());
        //mViewPager.setAdapter(new BannerAdapter());

        int i = Integer.MAX_VALUE / 2;
        int j = i  % mBanners.size();
        if( j != 0){
            i = (mBanners.size() - j) + i;
        }

        mViewPager.setCurrentItem(i);

        mViewPager.postDelayed(loop, 3000);
        mViewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    mViewPager.getHandler().removeCallbacks(loop);
                }else if(event.getAction() == MotionEvent.ACTION_UP){
                    mViewPager.postDelayed(loop, 3000);
                }else if(event.getAction() == MotionEvent.ACTION_MOVE){
                    isManualScroll = true;
                }
                return false;
            }
        });

    }
    class DepthPageTransformer implements ViewPager.PageTransformer {
        private float MIN_SCALE = 0.75f;

        @Override
        public void transformPage(View view, float position) {
            int pageWidth = view.getWidth();
            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0);
            } else if (position <= 0) { // [-1,0]
                // Use the default slide transition when
                // moving to the left page
                view.setAlpha(1);
                view.setTranslationX(0);
                view.setScaleX(1);
                view.setScaleY(1);
            } else if (position <= 1) { // (0,1]
                // Fade the page out.
                view.setAlpha(1 - position);
                // Counteract the default slide transition
                view.setTranslationX(pageWidth * -position);
                // Scale the page down (between MIN_SCALE and 1)
                float scaleFactor = MIN_SCALE + (1 - MIN_SCALE)
                        * (1 - Math.abs(position));
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);
            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0);

            }
        }

    }
    public class BannerAdapter extends PagerAdapter{


        @Override
        public int getCount() {
            return mBanners == null ? 0 : Integer.MAX_VALUE;
        }


        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            View v  =  LayoutInflater.from(getContext()).inflate(R.layout.layout_banner_item, container, false);

            ImageView imageView = v.findViewById(R.id.home_banner_img);
            Glide.with(container).load(mBanners.get(position % mBanners.size()).getImgUrl()).into(imageView);
            v.setTag(position % mBanners.size());
            container.addView(v);
            return v;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }
    }*/









