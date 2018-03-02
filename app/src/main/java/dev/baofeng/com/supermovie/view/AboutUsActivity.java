package dev.baofeng.com.supermovie.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import dev.baofeng.com.supermovie.R;

/**
 * Created by huangyong on 2018/2/23.
 */

public class AboutUsActivity extends AppCompatActivity {

    @BindView(R.id.tasktitle)
    TextView tasktitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_layout);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {

    }
}
