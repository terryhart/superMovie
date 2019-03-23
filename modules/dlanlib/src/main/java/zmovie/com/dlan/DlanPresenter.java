package zmovie.com.dlan;

import android.animation.Animator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.yanbo.lib_screen.entity.ClingDevice;
import com.yanbo.lib_screen.entity.RemoteItem;
import com.yanbo.lib_screen.manager.ClingManager;
import com.yanbo.lib_screen.manager.DeviceManager;
import com.yanbo.lib_screen.service.upnp.ClingContentDirectoryService;

import per.goweii.anylayer.AnimHelper;
import per.goweii.anylayer.AnyLayer;

/**
 * creator huangyong
 * createTime 2019/3/18 下午9:47
 * path com.yanbo.videodlnascreen
 * description:
 */
public class DlanPresenter {

    private Context context;


    /**
     * 初始化投屏管理类
     *
     * @param context
     */
    public DlanPresenter(Context context) {
        this.context = context;
    }

    /**
     * 初始化投屏服务，搜索可用设备
     */
    public void initService() {
        ClingManager.getInstance().startClingService();
    }

    /**
     * 网络投屏
     *
     * @param remoteItem
     */
    public void startPlay(RemoteItem remoteItem) {
        ClingManager.getInstance().setRemoteItem(remoteItem);
        context.startActivity(new Intent(context,MediaPlayActivity.class));
    }

    public void startPlay(String path, String title) {
        RemoteItem itemurl1 = new RemoteItem(title, "425703", "张杰",
                107362668, "00:04:33", "1280x720", path);
        startPlay(itemurl1);
    }

    /**
     * 本地投屏
     *
     * @param remoteItem
     */
    public void startPlayLocal(RemoteItem remoteItem) {
        ClingManager.getInstance().setRemoteItem(remoteItem);
        MediaPlayActivity.startSelf((Activity) context);
    }

    /**
     * 退出并停止投屏服务
     */
    public void stopService() {
        ClingManager.getInstance().stopClingService();
    }

    public boolean hasDeviceConnect() {
        if (DeviceManager.getInstance().getClingDeviceList().size() > 0 && DeviceManager.getInstance().getCurrClingDevice() != null) {
            return true;
        }
        return false;
    }

    public void showDialogTip(final Context context, final String path, final String title) {

        final AnyLayer anyLayer =  AnyLayer.with(context)
                .contentView(R.layout.dlan_tip_layout)
                .backgroundBlurPercent(0.15f)
                .backgroundColorInt(Color.parseColor("#33ffffff"))
                .gravity(Gravity.BOTTOM)
                .cancelableOnTouchOutside(true)
                .cancelableOnClickKeyBack(true)
                .contentAnim(new AnyLayer.IAnim() {
                    @Override
                    public Animator inAnim(View content) {

                        return AnimHelper.createTopAlphaInAnim(content);
                    }

                    @Override
                    public Animator outAnim(View content) {

                        return AnimHelper.createTopAlphaOutAnim(content);
                    }
                });
        final RecyclerView deviceList = anyLayer.getView(R.id.dlan_device_list);
        deviceList.setLayoutManager(new LinearLayoutManager(context));
        final ClingDeviceAdapter adapter = new ClingDeviceAdapter(context);
        deviceList.setAdapter(adapter);
        anyLayer.show();

        anyLayer.getView(R.id.dlan_refresh).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (deviceList!=null){
                    adapter.refresh();
                }
            }
        });
        final TextView tips =  anyLayer.getView(R.id.dlan_statu_text);
       final Button confirm =  anyLayer.getView(R.id.confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hasDeviceConnect()){
                    anyLayer.dismiss();
                    startPlay(path,title);
                }
            }
        });
        adapter.setItemClickListener(new ClingDeviceAdapter.OnDeviceCheckedListener() {
            @Override
            public void onItemChecked(int position, Object object) {
                ClingDevice device = (ClingDevice) object;
                ClingDevice currClingDevice = DeviceManager.getInstance().getCurrClingDevice();
                if (currClingDevice==device){
                    return;
                }
                DeviceManager.getInstance().setCurrClingDevice(device);
                Toast.makeText(context,"选择了设备 " + device.getDevice().getDetails().getFriendlyName(),Toast.LENGTH_LONG).show();
                refresh(adapter,deviceList);
            }

            @Override
            public void onItemCancelChose(int position, Object device) {
                refresh(adapter, deviceList);
            }

            @Override
            public void onRefreshed() {
                if (hasDeviceConnect()){
                    tips.setText("已连接设备，点击确定按钮开始投屏");
                    confirm.setEnabled(true);
                }else {
                    tips.setText("当前没有设备连接，请点击刷新按钮搜索设备");
                    confirm.setEnabled(false);
                }
            }

        });
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
