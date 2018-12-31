package dev.baofeng.com.supermovie;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Toast;

import dev.baofeng.com.supermovie.view.GlobalMsg;

public class PreActivity extends AppCompatActivity {

    private String id = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initOutsetData();

        finish();
    }

    private void initOutsetData() {
        Uri data = getIntent().getData();
        if (data != null) {
            id = data.getQueryParameter("data");
            if (!TextUtils.isEmpty(id)) {
                Intent intent = new Intent(this, SplashActivity.class);
                intent.putExtra(GlobalMsg.KEY_MV_ID, id);
                startActivity(intent);
            }
        } else {
            Intent intent = new Intent(this, SplashActivity.class);
            intent.putExtra(GlobalMsg.KEY_MV_ID, id);
            startActivity(intent);
        }


    }
}
