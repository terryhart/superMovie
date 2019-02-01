package dev.baofeng.com.supermovie.adapter;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bftv.myapplication.config.KeyParam;
import com.bftv.myapplication.view.WebviewPlayer;
import com.huangyong.downloadlib.model.Params;
import com.huangyong.downloadlib.utils.MD5Utils;
import com.huangyong.playerlib.PlayerActivity;

import java.util.ArrayList;

import dev.baofeng.com.supermovie.MyApp;
import dev.baofeng.com.supermovie.R;
import dev.baofeng.com.supermovie.domain.BtPlayInfo;
import dev.baofeng.com.supermovie.domain.DetailInfo;
import dev.baofeng.com.supermovie.domain.MovieInfo;
import dev.baofeng.com.supermovie.domain.PlayUrlBean;
import dev.baofeng.com.supermovie.holder.OnlinePlayHolder;
import dev.baofeng.com.supermovie.holder.PlayListHolder;

public class OnlinePlayWebAdapter extends RecyclerView.Adapter<OnlinePlayHolder> {

    private PlayUrlBean playList;

    private Context context;

    public OnlinePlayWebAdapter(PlayUrlBean playList) {
        this.playList = playList;
    }

    @NonNull
    @Override
    public OnlinePlayHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.context= parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.online_play_item,null);
        return new OnlinePlayHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OnlinePlayHolder holder, int position) {
        if (playList.getNormal().size() == 1) {
            holder.btPlayText.setText("播放");
        } else {
            holder.btPlayText.setText("第" + (position + 1) + "集");
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                showListDialog(playList.getNormal().get(position).getUrl());
            }
        });
    }

    @Override
    public int getItemCount() {
        if (playList!=null){
            return playList.getNormal().size();
        }else {
            return 0;
        }
    }

    /**
     * 普通列表dialog
     */
    private void showListDialog(String url) {
        final String listItems[] = new String[]{"应用内播放", "王卡专用免流播放(须选择QQ浏览器)"};

        AlertDialog.Builder listDialog = new AlertDialog.Builder(context);
        listDialog.setTitle("选择播放路径");
        listDialog.setIcon(R.mipmap.icon);

        listDialog.setItems(listItems, (dialog, which) -> {

            if (which == 0) {
                Intent intent = new Intent(context, WebviewPlayer.class);
                intent.putExtra(KeyParam.PLAYURL, url);
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
