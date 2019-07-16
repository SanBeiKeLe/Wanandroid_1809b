package com.wanghongli.bannerlibrary;

import android.widget.ImageView;

public interface BannerAdapter<M> {

    void fillBannerItemData(BannerView banner, ImageView imageView, M mode, int position);
}
