package dev.baofeng.com.supermovie.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import dev.baofeng.com.supermovie.R;

public class OnlineDetailPageActivity extends AppCompatActivity {

    private String movName;
    private String movDesc;
    private String movPlayClass;
    private String playUrlList;
    private String imgUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_detail_page);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        movName = intent.getStringExtra(GlobalMsg.KEY_MOVIE_TITLE);
        movDesc = intent.getStringExtra(GlobalMsg.KEY_MOVIE_DETAIL);
        movPlayClass = intent.getStringExtra(GlobalMsg.KEY_PLAY_TITLE);
        playUrlList = intent.getStringExtra(GlobalMsg.KEY_PLAY_URL);
        imgUrl = intent.getStringExtra(GlobalMsg.KEY_POST_IMG);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
}
