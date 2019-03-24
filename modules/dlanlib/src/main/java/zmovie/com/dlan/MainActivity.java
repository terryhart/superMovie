package zmovie.com.dlan;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.yanbo.lib_screen.entity.RemoteItem;

public class MainActivity extends AppCompatActivity {
    Button button;
    Button button1;
    RecyclerView recyclerView;
//    String  url1="http://hc.yinyuetai.com/uploads/videos/common/44E4016521C693F23F7E9344AEBF5AF0.mp4?sc=5c4d956adf76a722&br=781&vid=3266995&aid=35&area=ML&vst=0";
    String  url1="http://fuli.zuida-youku-le.com/20170915/sPuONPaP/index.m3u8";
    private DlanPresenter dlanPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button=findViewById(R.id.button);
        button1=findViewById(R.id.button1);
        recyclerView=findViewById(R.id.recycler_view);

        dlanPresenter = new DlanPresenter(this);
        dlanPresenter.initService();


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeviceListActivityPage.startSelf(MainActivity.this);
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RemoteItem itemurl1 = new RemoteItem("一路之下", "425703", "张杰",
                        107362668, "00:04:33", "1280x720", url1);

                dlanPresenter.startPlay(itemurl1);
            }
        });



        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ClingDeviceAdapter adapter = new ClingDeviceAdapter(this);
        recyclerView.setAdapter(adapter);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dlanPresenter.stopService();
    }
}
