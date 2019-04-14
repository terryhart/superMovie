package dev.baofeng.com.supermovie.view;
/**
 * Created by HuangYong on 2018/9/29.
 */

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.huangyong.downloadlib.db.FavorDao;
import com.huangyong.downloadlib.domain.FavorInfo;

import java.util.List;

import app.huangyong.com.common.widget.timeline.itemdecoration.DotItemDecoration;
import app.huangyong.com.common.widget.timeline.itemdecoration.SpanIndexListener;
import butterknife.BindView;
import butterknife.ButterKnife;
import dev.baofeng.com.supermovie.R;
import dev.baofeng.com.supermovie.adapter.FavorAdapter;
import dev.baofeng.com.supermovie.adapter.SearchAdapter;

/**
 * @author Huangyong
 * @version 1.0
 * @title
 * @description
 * @company 北京奔流网络信息技术有线公司
 * @created 2018/9/29
 * @changeRecord [修改记录] <br/>
 * 2018/9/29 ：created
 */
public class FavorActivity extends AppCompatActivity implements SearchAdapter.onLongClickedListener, FavorAdapter.onLongClickedListener {

    @BindView(R.id.rv_favor_list)
    RecyclerView rvFavorList;
    private FavorAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favor_layout);
        ButterKnife.bind(this);

    }

    private void initFavordata() {
        FavorDao dao = FavorDao.getInstance(getApplicationContext());
        List<FavorInfo> favorInfos = dao.queryAll();
        if (favorInfos != null && favorInfos.size() > 0) {

            rvFavorList.setLayoutManager(new GridLayoutManager(this,3));



            adapter = new FavorAdapter(FavorActivity.this, favorInfos, this);
            rvFavorList.setAdapter(adapter);

        } else {
            Toast.makeText(this, "暂无收藏记录哦", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        initFavordata();
    }

    @Override
    public void onLongClick(String id) {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setIcon(R.mipmap.icon)//设置标题的图片
                .setTitle("提示！")//设置对话框的标题
                .setMessage("是否删除本条记录")//设置对话框的内容
                //设置对话框的按钮
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FavorDao dao = FavorDao.getInstance(getApplicationContext());
                        dao.delete(Integer.parseInt(id));
                        dialog.dismiss();
                        adapter.notifyDataSetChanged();
                    }
                }).create();
        dialog.show();
    }

}
