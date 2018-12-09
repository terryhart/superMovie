package dev.baofeng.com.supermovie.view;

import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import dev.baofeng.com.supermovie.R;
import dev.baofeng.com.supermovie.view.widget.AlipayZeroSdk;

public class PayMeActivity extends AppCompatActivity {

    private ConstraintLayout root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_me);

        root = findViewById(R.id.root);
    }

    public void toAlipay(View view) {
        if (AlipayZeroSdk.hasInstalledAlipayClient(PayMeActivity.this)) {
            AlipayZeroSdk.startAlipayClient(PayMeActivity.this, "FKX05183IOKBWDUKV9OF03");
        } else {
            Snackbar.make(root, "谢谢，您没有安装支付宝客户端", Snackbar.LENGTH_LONG).show();
        }
    }
}
