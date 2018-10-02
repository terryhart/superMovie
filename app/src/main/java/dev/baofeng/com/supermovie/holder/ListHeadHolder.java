package dev.baofeng.com.supermovie.holder;

import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.widget.ImageView;

import com.ctetin.expandabletextviewlibrary.ExpandableTextView;

import dev.baofeng.com.supermovie.R;

/**
 * Created by huangyong on 2018/2/11.
 */

public class ListHeadHolder extends ViewHolder {

    public ImageView screenShot;

    public ListHeadHolder(View itemView) {
        super(itemView);
        screenShot = itemView.findViewById(R.id.list_banner);
    }
}
