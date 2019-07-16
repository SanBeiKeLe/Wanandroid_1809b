package com.wanghongli.bannerlibrary;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;


public class MyIndicator extends View {
    private static final int MAX_COUNT=7;
    private int mIndicatorCount;//总共小圆的个数
    private  int mCurrentIndex;//选中的小圆的Index
    private Paint mPaint;//画笔
    private int mSelectedColor;//选中的颜色
    private int mUnSelectedColor;//未选中的颜色
    private int mRadius;//小圆的半径
    private int mSpace;//两个小圆之间的间距
    private int mWidth;
    private int mHeight;
    private BannerView mBanner;
    private int mMaxRadius; // 最大半径


    public MyIndicator(Context context) {
        super(context);
    }

    private MyIndicator(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    private MyIndicator(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mWidth==0||mHeight==0){
            setMeasuredDimension(MeasureSpec.makeMeasureSpec(1,MeasureSpec.EXACTLY),MeasureSpec.makeMeasureSpec(1,MeasureSpec.EXACTLY));
        }else {
            setMeasuredDimension(MeasureSpec.makeMeasureSpec(mWidth,MeasureSpec.EXACTLY),MeasureSpec.makeMeasureSpec(mHeight,MeasureSpec.EXACTLY));
        }
    }
    private int getIndicatorCout(){
        return Math.min(mIndicatorCount,MAX_COUNT);
    }
    public void setIndicatorCout(int indicatorCount){

        mIndicatorCount = indicatorCount;
    }
    public void setCurrentIndex(int mCurrentIndex) {
        this.mCurrentIndex = mCurrentIndex;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(mWidth == 0 || mHeight == 0){
            return;
        }

        for(int i = 0 ; i < getIndicatorCout(); i++){
            /**
             * 第一参数： cx 表示 圆心的x 坐标，
             * 第二参数： cy 表示圆心Y 的坐标，
             * 第三参数： radius 圆半径
             * 第四参数： paint 画笔
             */
            if(mCurrentIndex == i){
                mPaint.setColor(mSelectedColor);
            }else{
                mPaint.setColor(mUnSelectedColor);
            }
            canvas.drawCircle(((i * 2 * mRadius) + (i * mSpace)  + mRadius),mRadius,mRadius, mPaint);
        }

    }






}
