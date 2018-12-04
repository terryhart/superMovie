package dev.baofeng.com.supermovie.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import dev.baofeng.com.supermovie.R;
import dev.baofeng.com.supermovie.adapter.DownListAdapter;

public class DownLoadListDialog extends Dialog {

    private DownListAdapter detailAdapter;

    public DownLoadListDialog(MovieDetailActivity movieDetailActivity, int i, DownListAdapter detailAdapter) {
        super(movieDetailActivity,  R.style.Dialog_Fullscreen);
        this.detailAdapter = detailAdapter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.down_list_layout);
        RecyclerView dorvlist =findViewById(R.id.downloadList);
        Button closeList =findViewById(R.id.close_list);
        dorvlist.setLayoutManager(new LinearLayoutManager(getContext()));
        setCanceledOnTouchOutside(true);
        dorvlist.setAdapter(detailAdapter);
        closeList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }
}
