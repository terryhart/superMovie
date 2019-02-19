package com.bftv.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    String[] urls = {
            "http://v1-tt.ixigua.com/f2ab1b1a26ad4f376f6b52cce304310b/5b51e7a6/video/m/2203b93c61229544433a04367f082223d5111596444000065b13e96be82/",
            "http://v6-tt.ixigua.com/video/m/2203b93c61229544433a04367f082223d5111596444000065b13e96be82/?Expires=1532052233&AWSAccessKeyId=qh0h9TdcEMoS2oPj7aKX&Signature=rofER1hTUlFONgWJZwUcSoGII7o%3D",
            "http://v1-tt.ixigua.com/5ead96e3a8f5e2729b6b7eba098aaa08/5b51439f/video/m/2203b93c61229544433a04367f082223d5111596444000065b13e96be82/",
            "http://v11-tt.ixigua.com/6ae69854be4814d200d8173d6ca0c85e/5b5143c8/video/m/2203b93c61229544433a04367f082223d5111596444000065b13e96be82/",
            "http://v3-tt.ixigua.com/f27e813471a72cd1e918352e8213a278/5b5143e0/video/m/2203b93c61229544433a04367f082223d5111596444000065b13e96be82/",
            "http://v6-tt.ixigua.com/video/m/2203b93c61229544433a04367f082223d5111596444000065b13e96be82/?Expires=1532052469&AWSAccessKeyId=qh0h9TdcEMoS2oPj7aKX&Signature=RQCcXw%2FNNFxSlU7baKiOsQnNCXU%3D",
            "http://v11-tt.ixigua.com/92763572cb66ca383532375b7aff8240/5b51440a/video/m/2203b93c61229544433a04367f082223d5111596444000065b13e96be82/",
            "http://v9-tt.ixigua.com/860170cd5baab247a60150d87e719ef7/5b514422/video/m/2203b93c61229544433a04367f082223d5111596444000065b13e96be82/",
            "http://v3-tt.ixigua.com/a86241ff21d57bde83304e7be2872695/5b514439/video/m/2203b93c61229544433a04367f082223d5111596444000065b13e96be82/",
            "http://v11-tt.ixigua.com/cf87f06fe1802e896ce696e20b1baddc/5b51444b/video/m/2203b93c61229544433a04367f082223d5111596444000065b13e96be82/",
            "http://v1-tt.ixigua.com/c2cfb0423cef9d079474383fa985de3c/5b514479/video/m/2203b93c61229544433a04367f082223d5111596444000065b13e96be82/",
            "http://v11-tt.ixigua.com/d78df11775d5cf23b345bb2c3df12828/5b51448d/video/m/2203b93c61229544433a04367f082223d5111596444000065b13e96be82/"
    };
    String[] num = {
            "第44集",
            "第45集",
            "第46集",
            "第47集",
            "第48集",
            "第49集",
            "第50集",
            "第52集",
            "第53集",
            "第54集",
            "第55集"
    };
    private GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);


        List<DateInfo> list = new ArrayList<>();

        for (int i = 0; i < urls.length-1; i++) {
            DateInfo info = new DateInfo();
            info.setNum(num[i]);
            info.setUrl(urls[i]);
            list.add(info);
        }
        gridView = findViewById(R.id.grid);

        MyAdapter adapter = new MyAdapter(list);

        gridView.setAdapter(adapter);



    }
}
