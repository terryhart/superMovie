package dev.baofeng.com.supermovie.view;
/**
 * Created by HuangYong on 2018/9/29.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import dev.baofeng.com.supermovie.R;

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
public class FavorActivity extends AppCompatActivity {

    @BindView(R.id.favor_clear)
    Button favorClear;
    @BindView(R.id.rv_favor_list)
    RecyclerView rvFavorList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favor_layout);
        ButterKnife.bind(this);

    }
}
