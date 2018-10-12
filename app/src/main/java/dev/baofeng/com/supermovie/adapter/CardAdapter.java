package dev.baofeng.com.supermovie.adapter;
/**
 * Created by HuangYong on 2018/10/11.
 */

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

import dev.baofeng.com.supermovie.R;
import dev.baofeng.com.supermovie.view.PosterItemView;

/**
 * @author Huangyong
 * @version 1.0
 * @title
 * @description
 * @company 北京奔流网络信息技术有线公司
 * @created 2018/10/11
 * @changeRecord [修改记录] <br/>
 * 2018/10/11 ：created
 */
public class CardAdapter extends BaseAdapter {

    private ArrayList<String> urlList;
    private Context context;

    public CardAdapter(ArrayList<String> urlList, Context context) {
        this.urlList = urlList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return urlList.size();
    }

    @Override
    public Object getItem(int position) {
        return urlList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_item_layout,parent, false);
        PosterItemView img = view.findViewById(R.id.imgtest);
        img.setScaleType(ImageView.ScaleType.CENTER_CROP);
        img.setBackgroundColor(Color.parseColor("#ffffff"));
        return view;
    }
}
