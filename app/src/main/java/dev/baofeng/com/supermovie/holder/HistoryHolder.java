package dev.baofeng.com.supermovie.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import dev.baofeng.com.supermovie.R;

/**
 * Created by huangyong on 2018/3/1.
 */

public class HistoryHolder extends RecyclerView.ViewHolder {

    public   TextView hissize;
    public  TextView hisname;
    public  ImageView play;

    public HistoryHolder(View itemView) {
        super(itemView);

        hissize = (TextView) itemView.findViewById(R.id.his_size);
        hisname = (TextView) itemView.findViewById(R.id.his_taskname);
        play = (ImageView) itemView.findViewById(R.id.play);
    }
}
