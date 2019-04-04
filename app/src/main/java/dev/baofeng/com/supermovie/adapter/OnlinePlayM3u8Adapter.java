package dev.baofeng.com.supermovie.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import dev.baofeng.com.supermovie.R;
import dev.baofeng.com.supermovie.domain.PlayUrlBean;
import dev.baofeng.com.supermovie.holder.OnlinePlayHolder;

import com.huangyong.playerlib.Params;
import com.huangyong.playerlib.PlayerActivity;
import com.huangyong.playerlib.model.M3u8Bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class OnlinePlayM3u8Adapter extends RecyclerView.Adapter<OnlinePlayHolder> {

    private PlayUrlBean playList;

    private Context context;

    private String poster;
    private String title;
    private Activity activity;

    public OnlinePlayM3u8Adapter(Activity activity, PlayUrlBean playList, String posterUrl, String title) {
        this.playList = playList;
        this.poster = posterUrl;
        this.title = title;
        this.activity = activity;
    }

    @NonNull
    @Override
    public OnlinePlayHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.online_play_item, null);
        return new OnlinePlayHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OnlinePlayHolder holder, int position) {

        if (playList.getM3u8().size() == 1) {
            holder.btPlayText.setText("1");
        } else {
            holder.btPlayText.setText(position + 1 + "");
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showListDialog(playList.getM3u8().get(position).getUrl(),position);

            }
        });
    }

    @Override
    public int getItemCount() {
        if (playList != null) {
            return playList.getM3u8().size();
        } else {
            return 0;
        }
    }


    /**
     * 普通列表dialog
     */
    private void showListDialog(String url, int position) {
        final String listItems[] = new String[]{"智能播放器(可投屏、小窗)", "王卡专用免流播放(须选择QQ浏览器)"};

        AlertDialog.Builder listDialog = new AlertDialog.Builder(context);
        listDialog.setTitle("选择播放方式");
        listDialog.setIcon(R.mipmap.icon);

        listDialog.setItems(listItems, (dialog, which) -> {

            if (which == 0) {

//                for (int i = 0; i < playList.getM3u8().size(); i++) {
//                    playUrlList.put(i,playList.getM3u8().get(i).getUrl());
//
//                }
                List<M3u8Bean> list = new ArrayList<>();
                for (int i = 0; i < playList.getM3u8().size(); i++) {
                    M3u8Bean m3u8Bean = new M3u8Bean();
                    m3u8Bean.setTitle(playList.getM3u8().get(i).getTitle());
                    m3u8Bean.setUrl(playList.getM3u8().get(i).getUrl());
                    list.add(m3u8Bean);
                }

                Bundle bundle = new Bundle();
                bundle.putInt(Params.CURRENT_INDEX,position);
                bundle.putSerializable(Params.PALY_LIST_URL, (Serializable) list);

                Intent intent = new Intent(context, PlayerActivity.class);
                intent.putExtra(com.huangyong.downloadlib.model.Params.PROXY_PALY_URL, url);
                intent.putExtra(Params.PALY_LIST_URL,bundle);
                intent.putExtra(com.huangyong.downloadlib.model.Params.POST_IMG_KEY, poster);
                intent.putExtra(com.huangyong.downloadlib.model.Params.TASK_TITLE_KEY, title);
                context.startActivity(intent);
            }
            if (which == 1) {
                openBrowser(context, url);
            }
        });

        //设置按钮
        listDialog.setPositiveButton("取消"
                , (dialog, which) -> dialog.dismiss());

        listDialog.create().show();
    }


    /**
     * 调用第三方浏览器打开
     *
     * @param context
     * @param url     要浏览的资源地址
     */
    public static void openBrowser(Context context, String url) {
        final Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        // 注意此处的判断intent.resolveActivity()可以返回显示该Intent的Activity对应的组件名
        // 官方解释 : Name of the component implementing an activity that can display the intent
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            final ComponentName componentName = intent.resolveActivity(context.getPackageManager());
            // 打印Log   ComponentName到底是什么
            context.startActivity(Intent.createChooser(intent, "请选择浏览器"));
        } else {
            Toast.makeText(context.getApplicationContext(), "请下载浏览器", Toast.LENGTH_SHORT).show();
        }
    }


}
