package com.bftv.myapplication;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Helloworld on 2018/7/20.
 */

public class MyAdapter extends BaseAdapter {
    private int[] src = {
            R.drawable.aqylogo,
            R.drawable.youkulogo,
            R.drawable.qqlogo,
            R.drawable.sohulogo,
            R.drawable.a56logo,
            R.drawable.a163logo,
            R.drawable.letvlogo,
            R.drawable.tudoulogo,
            R.drawable.hunantvlogo
    };
    private List<DateInfo> infos;

    public MyAdapter(List<DateInfo> infos) {
        this.infos = infos;
    }

    @Override
    public int getCount() {
        return src.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, final ViewGroup viewGroup) {

        View views = View.inflate(viewGroup.getContext(),R.layout.program_item,null);
        ImageView img = views.findViewById(R.id.icons);
        img.setImageResource(src[i]);
        return views;
    }
}
