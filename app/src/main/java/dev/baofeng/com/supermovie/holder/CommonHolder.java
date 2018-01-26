package dev.baofeng.com.supermovie.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.ButterKnife;
import dev.baofeng.com.supermovie.R;

/**
 * Created by huangyong on 2018/1/26.
 */

public class CommonHolder extends RecyclerView.ViewHolder {


    public ImageView itemimg;
    public TextView itemtitle;

    public CommonHolder(View itemView) {
        super(itemView);
        itemimg = itemView.findViewById(R.id.post_img);
        itemtitle = itemView.findViewById(R.id.post_title);
    }
}
