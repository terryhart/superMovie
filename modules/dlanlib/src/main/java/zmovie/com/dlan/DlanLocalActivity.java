package zmovie.com.dlan;

import android.animation.Animator;
import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yanbo.lib_screen.entity.ClingDevice;
import com.yanbo.lib_screen.manager.ClingManager;
import com.yanbo.lib_screen.manager.DeviceManager;

import per.goweii.anylayer.AnimHelper;
import per.goweii.anylayer.AnyLayer;
import per.goweii.anylayer.LayerManager;
import zmovie.com.dlan.view.LocalContentFragment;


public class DlanLocalActivity extends AppCompatActivity implements View.OnClickListener {


    private TextView title;
    private TextView smallTitle;
    private DlanPresenter presenter;
    private RelativeLayout headContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dlan_local);


        title = findViewById(R.id.local_title);
        smallTitle = findViewById(R.id.small_title);
        headContent = findViewById(R.id.head_content);


        LocalContentFragment fragment = LocalContentFragment.newInstance();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        fragmentTransaction.add(R.id.local_content, fragment);
        fragmentTransaction.commitAllowingStateLoss();


        title.setOnClickListener(this);
        smallTitle.setOnClickListener(this);

        presenter = new DlanPresenter(this);
        presenter.initService();
        //先扫描本地文件
        ClingManager.getInstance().searchLocalContent("0");
        refreshStatu();
    }

    private void refreshStatu() {

        if (presenter == null) {
            return;
        }
        if (presenter.hasDeviceConnect()) {
            ClingDevice clingDevice = DeviceManager.getInstance().getCurrClingDevice();
            title.setText(clingDevice.getDevice().getDetails().getFriendlyName());
            smallTitle.setText("已连接");
            smallTitle.setTextColor(Color.GREEN);
        } else {
            title.setText("未连接设备");
            smallTitle.setText("点击连接设备");
            smallTitle.setTextColor(Color.GRAY);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        refreshStatu();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.local_title || v.getId() == R.id.small_title) {


            final AnyLayer anyLayer =   AnyLayer.with(DlanLocalActivity.this)
                    .contentView(R.layout.dlan_tip_layout)
                    .backgroundColorRes(R.color.dialog_bg)
                    .gravity(Gravity.TOP)
                    .contentAnim(new LayerManager.IAnim() {
                        @Override
                        public Animator inAnim(View content) {
                            return AnimHelper.createTopInAnim(content);
                        }

                        @Override
                        public Animator outAnim(View content) {
                            return AnimHelper.createTopOutAnim(content);
                        }
                    })
                    .onClickToDismiss(R.id.confirm);

            anyLayer.getView(R.id.confirm).setVisibility(View.INVISIBLE);
            anyLayer.show();

            final RecyclerView deviceList = anyLayer.getView(R.id.dlan_device_list);
            deviceList.setLayoutManager(new LinearLayoutManager(DlanLocalActivity.this));
            final ClingDeviceAdapter adapter = new ClingDeviceAdapter(DlanLocalActivity.this);
            deviceList.setAdapter(adapter);
            final View loading = anyLayer.getView(R.id.search_loading);
            anyLayer.getView(R.id.dlan_refresh).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (deviceList!=null){
                        adapter.refresh();
                        if (!loading.isShown()){
                            loading.setVisibility(View.VISIBLE);

                            deviceList.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    loading.setVisibility(View.INVISIBLE);
                                }
                            },3000);
                        }
                    }
                }
            });
            final TextView tips =  anyLayer.getView(R.id.dlan_statu_text);
            final Button confirm =  anyLayer.getView(R.id.confirm);

            adapter.setItemClickListener(new ClingDeviceAdapter.OnDeviceCheckedListener() {
                @Override
                public void onItemChecked(int position, Object object) {
                    ClingDevice device = (ClingDevice) object;
                    ClingDevice currClingDevice = DeviceManager.getInstance().getCurrClingDevice();
                    if (currClingDevice==device){
                        return;
                    }
                    DeviceManager.getInstance().setCurrClingDevice(device);
                    Toast.makeText(DlanLocalActivity.this,"选择了设备 " + device.getDevice().getDetails().getFriendlyName(),Toast.LENGTH_LONG).show();
                    refresh(adapter,deviceList);
                    refreshStatu();
                }

                @Override
                public void onItemCancelChose(int position, Object device) {
                    refresh(adapter, deviceList);
                }

                @Override
                public void onRefreshed() {
                    if (presenter.hasDeviceConnect()){
                        tips.setText("已连接设备，点击确定按钮开始投屏");
                        confirm.setEnabled(true);
                    }else {
                        tips.setText("当前没有设备连接，请点击刷新按钮搜索设备");
                        confirm.setEnabled(false);
                    }
                }

            });

            anyLayer.onLayerDismissListener(new LayerManager.OnLayerDismissListener() {
                @Override
                public void onDismissing(AnyLayer anyLayer) {

                }

                @Override
                public void onDismissed(AnyLayer anyLayer) {
                    refreshStatu();
                }
            });
        }

    }

    public void refresh(final ClingDeviceAdapter adapter, RecyclerView deviceList) {
        deviceList.postDelayed(new Runnable() {
            @Override
            public void run() {
                adapter.refresh();
            }
        },300);

    }
}
