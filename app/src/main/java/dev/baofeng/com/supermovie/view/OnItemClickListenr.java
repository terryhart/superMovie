package dev.baofeng.com.supermovie.view;

import android.widget.ImageView;

public interface OnItemClickListenr {
    void clicked(String url,String imgUrl);

    void clickedPic(ImageView screenShot, String screenShotImagUrl);
}
