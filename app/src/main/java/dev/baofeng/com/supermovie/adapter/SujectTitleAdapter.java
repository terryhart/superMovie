package dev.baofeng.com.supermovie.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.huangyong.downloadlib.model.Params;

import dev.baofeng.com.supermovie.R;
import dev.baofeng.com.supermovie.domain.MovieInfo;
import dev.baofeng.com.supermovie.domain.SubjectInfo;
import dev.baofeng.com.supermovie.domain.SubjectTitleInfo;
import dev.baofeng.com.supermovie.holder.FavorHolder;
import dev.baofeng.com.supermovie.holder.SubjectHolder;
import dev.baofeng.com.supermovie.view.GlobalMsg;
import dev.baofeng.com.supermovie.view.MovieDetailActivity;
import dev.baofeng.com.supermovie.view.SubjectListActivity;

/**
 * Created by huangyong on 2018/1/26.
 */

public class SujectTitleAdapter extends RecyclerView.Adapter {
    private Context context;
    private SubjectTitleInfo info;

    private String[] imgArr = {
        "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1539614157919&di=bc73926ccaa63949802e095b67a20c45&imgtype=0&src=http%3A%2F%2Fpic36.photophoto.cn%2F20150729%2F0008020929291143_b.jpg",
        "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1539614157919&di=2d545ef006736183f788a2dc8b59a8c4&imgtype=0&src=http%3A%2F%2Fimg0.ph.126.net%2FjEfaSloPBy3q2gVsG_0S-w%3D%3D%2F6630259624652424975.jpg",
        "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1539614158201&di=03d044dfecb7dac0e44d1018d9968a26&imgtype=0&src=http%3A%2F%2Fimg.iecity.com%2FUpload%2FFile%2F201403%2F08%2F20140308072540279.jpg",
        "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1539614158198&di=a0d1b38ce8be29c63399890a5532ceae&imgtype=0&src=http%3A%2F%2Fpic35.photophoto.cn%2F20150507%2F0017029555837290_b.jpg",
        "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1539614158194&di=6c856b031837d1df04aff51dc1e936ac&imgtype=0&src=http%3A%2F%2Fs16.sinaimg.cn%2Fmiddle%2F005wu7BJgy6Hb9jPom36f%26690",
        "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1539614158193&di=76b3639b2e4eb3bdf2b602ff54f3db28&imgtype=0&src=http%3A%2F%2Fpic1.16pic.com%2F00%2F03%2F00%2F16pic_300699_b.jpg",
        "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1539615667478&di=3ac7b2b5650c295fbf22593e8935ec89&imgtype=jpg&src=http%3A%2F%2Fimg4.imgtn.bdimg.com%2Fit%2Fu%3D725230539%2C1375616201%26fm%3D214%26gp%3D0.jpg",
        "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1539615667274&di=017af22e751d260bc9f4bb4fc0c88152&imgtype=0&src=http%3A%2F%2Fimg31.mtime.cn%2FCMS%2FNews%2F2015%2F06%2F19%2F142441.19344609_620X620.jpg",
        "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1539615667282&di=986496ed3c5990205500d7047011bfc0&imgtype=0&src=http%3A%2F%2Fdingyue.nosdn.127.net%2Fx7oS5ByTHwcA6B5Tz4nDh70y7cv9OGhl27Kxq9C7f0WnJ1530343249944compressflag.jpg",
        "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1539615608830&di=c819f8f9e88d59cbeaae0dee6ca2ec58&imgtype=0&src=http%3A%2F%2Fwww.ufo110.net%2Fuploads%2Fallimg%2F160910%2F2-16091010244G40.jpg"
    };

    public SujectTitleAdapter(Context context, SubjectTitleInfo info) {
        this.context = context;
        this.info = info;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_suject,parent,false);
        return new SubjectHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        Glide.with(context).load(imgArr[position%9]).into(((SubjectHolder) holder).itemimg);
        ((SubjectHolder) holder).itemtitle.setText(info.getData().get(position).getSujectTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(context, SubjectListActivity.class);
                    intent.putExtra(Params.TASK_TITLE_KEY, info.getData().get(position).getSujectTitle());
                    context.startActivity(intent);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return info.getData().size();
    }


}
