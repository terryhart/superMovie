package dev.baofeng.com.supermovie.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;

import dev.baofeng.com.supermovie.R;
import dev.baofeng.com.supermovie.domain.online.OnlinePlayInfo;
import dev.baofeng.com.supermovie.view.GlobalMsg;
import dev.baofeng.com.supermovie.view.online.detail.OnlineDetailPageActivity;
import dev.baofeng.com.supermovie.view.widget.PileLayout;

/**
 * creator huangyong
 * createTime 2019/3/30 下午2:33
 * path dev.baofeng.com.supermovie.adapter
 * description:
 */
public class PieRandomAdapter extends PileLayout.Adapter {

    private Context context;
    private OnlinePlayInfo info;

    private int isMV;
    private String mvType;
    public PieRandomAdapter(Context context, OnlinePlayInfo info, int isMovie, String mvType) {
        this.context = context;
        this.info  = info;
        this.isMV = isMovie;
        this.mvType = mvType;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_pie_main;
    }

    @Override
    public int getItemCount() {
        return info.getData().size();
    }

    @Override
    public void bindView(View view, int index) {
        ViewHolder viewHolder = (ViewHolder) view.getTag();
        if (viewHolder == null) {
            viewHolder = new ViewHolder();
            viewHolder.itemimg =  view.findViewById(R.id.post_img);
            viewHolder.itemtitle =  view.findViewById(R.id.post_title);
            viewHolder.itemTag = view.findViewById(R.id.item_update_tag);
            view.setTag(viewHolder);
        }
        String imgUrl = info.getData().get(index).getDownimgurl();
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.place_holder);
        Glide.with(context).load(imgUrl).transition(DrawableTransitionOptions.withCrossFade(300)).apply(requestOptions).into((viewHolder.itemimg));


        viewHolder.itemtitle.setText(info.getData().get(index).getDownLoadName());

    }

    @Override
    public void displaying(int position) {
    }

    @Override
    public void onItemClick(View view, int position) {
        String imgUrl = info.getData().get(position).getDownimgurl();
        toDetailPage(imgUrl,position,info.getData().get(position).getDownLoadName());
    }

    class ViewHolder {
        public ImageView itemimg;
        public TextView itemtitle;
        public TextView itemTag;
    }



    private void toDetailPage(String imgUrl, int position, String name) {
        Intent intent = new Intent(context, OnlineDetailPageActivity.class);
        intent.putExtra(GlobalMsg.KEY_POST_IMG, imgUrl);
        intent.putExtra(GlobalMsg.KEY_DOWN_URL, info.getData().get(position).getDownLoadUrl());
        intent.putExtra(GlobalMsg.KEY_MOVIE_TITLE, name);
        intent.putExtra(GlobalMsg.KEY_MV_TYPE, mvType);

        intent.putExtra(GlobalMsg.KEY_MV_ID,info.getData().get(position).getMv_md5_id());

        intent.putExtra(GlobalMsg.KEY_IS_MOVIE, isMV);
        //简介
        intent.putExtra(GlobalMsg.KEY_MOVIE_DETAIL, info.getData().get(position).getMvdesc());

        intent.putExtra(GlobalMsg.KEY_BLUR_COLOR, "");
        //地址类型 m3u8/kuyun
        intent.putExtra(GlobalMsg.KEY_MOVIE_DOWN_ITEM_TITLE, info.getData().get(position).getDowndtitle());
        //地址列表，title & url
        intent.putExtra(GlobalMsg.KEY_PLAY_URL, info.getData().get(position).getDownLoadUrl());
        context.startActivity(intent);
    }
}
