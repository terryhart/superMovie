package zmovie.com.dlan;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.yanbo.lib_screen.entity.ClingDevice;
import com.yanbo.lib_screen.event.DeviceEvent;
import com.yanbo.lib_screen.manager.DeviceManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 描述：
 *
 * @author Yanbo
 * @date 2018/11/6
 */
public class DeviceListActivityPage extends AppCompatActivity {

    RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private ClingDeviceAdapter adapter;
    public static void startSelf(Activity context) {
        Intent intent = new Intent(context, DeviceListActivityPage.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_device_list);
        recyclerView=findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(this);
        adapter = new ClingDeviceAdapter(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        adapter.setItemClickListener(new ClingDeviceAdapter.OnDeviceCheckedListener() {
            @Override
            public void onItemChecked(int position, Object object) {
                ClingDevice device = (ClingDevice) object;
                ClingDevice currClingDevice = DeviceManager.getInstance().getCurrClingDevice();
                if (currClingDevice==device){
                    return;
                }
                DeviceManager.getInstance().setCurrClingDevice(device);
                Toast.makeText(getBaseContext(),"选择了设备 " + device.getDevice().getDetails().getFriendlyName(),Toast.LENGTH_LONG).show();
                refresh();
            }

            @Override
            public void onItemCancelChose(int position, Object device) {
                refresh();
            }

            @Override
            public void onRefreshed() {

            }

        });
    }

    public void refresh() {
        if (adapter == null) {
            adapter = new ClingDeviceAdapter(this);
            recyclerView.setAdapter(adapter);
        }
        recyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                adapter.refresh();
            }
        },300);

    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBus(DeviceEvent event) {
        refresh();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
}
