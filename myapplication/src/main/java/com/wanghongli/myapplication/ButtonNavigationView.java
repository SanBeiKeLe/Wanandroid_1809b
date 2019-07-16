package com.wanghongli.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

public class ButtonNavigationView extends ConstraintLayout implements CompoundButton.OnCheckedChangeListener {


    private onCheckChangeListener mListener;

    public ButtonNavigationView(Context context) {
        super(context);
    }

    public ButtonNavigationView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ButtonNavigationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        View button;
        for (int i = 0; i < getChildCount(); i++) {
            button = getChildAt(i);
            if (button instanceof RadioButton) {
                ((RadioButton) getChildAt(i)).setOnCheckedChangeListener(this);
            }
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
       if (isChecked){
           unCheckOther(buttonView);
            if (mListener!=null){
                mListener.setOnCheckChangeListen(buttonView,isChecked);
            }
       }
    }

    public void unCheckOther(CompoundButton checkButton) {
        View button;
        for (int i = 0; i < getChildCount(); i++) {
            button = getChildAt(i);
            if (button instanceof RadioButton &&button !=checkButton){
                ((RadioButton)button).setChecked(false);
            }
        }
    }


    public interface onCheckChangeListener {
        void setOnCheckChangeListen(CompoundButton buttonView, boolean isChecked);
    }

    public void setonCheckChangeListener(onCheckChangeListener listener) {
        mListener = listener;
    }
}
