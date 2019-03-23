package zmovie.com.dlan;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;

import com.yanbo.lib_screen.entity.AVTransportInfo;
import com.yanbo.lib_screen.entity.RenderingControlInfo;
import com.yanbo.lib_screen.event.ControlEvent;
import com.yanbo.lib_screen.event.DIDLEvent;
import com.yanbo.lib_screen.manager.ClingManager;

import org.fourthline.cling.support.model.DIDLContent;
import org.fourthline.cling.support.model.DIDLObject;
import org.fourthline.cling.support.model.container.Container;
import org.fourthline.cling.support.model.item.Item;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

public class DlanLocalActivity extends AppCompatActivity {
    //设置本地投屏的信息
    private List<DIDLObject> objectList;
    private DIDLContent content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dlan_local);
        RecyclerView list = findViewById(R.id.listLocal);


        ClingManager.getInstance().getLocalItem();
        ClingManager.getInstance().searchLocalContent("0");
        //final DIDLObject object = objectList.get(0);

//        if (object instanceof Container) {
//            //得到本地文件夹
//            Container container = (Container) object;
//            //点进文件夹刷新数据List<DIDLObject> objectList
//            ClingManager.getInstance().searchLocalContent(container.getId());
//        } else if (object instanceof Item) {
//            //得到本地文件
//            Item item = (Item) object;
//            // 设置本地投屏的信息
//            ClingManager.getInstance().setLocalItem(item);
//        }

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBus(DIDLEvent event) {

        Log.e("findlocal","on result--"+event.content.getCount());

        DIDLContent content = event.content;

        if (content.getItems()!=null&&content.getItems().size()>0){
            List<Item> items = content.getItems();
            for (int j = 0; j < items.size(); j++) {
                Log.e("currentContainer","第一级item有"+items.get(j).getTitle());
            }
        }

        if (content.getContainers()!=null&&content.getContainers().size()>0){
            List<Container> containers = content.getContainers();
            for (int i = 0; i <containers.size() ; i++) {
                Integer childCount = containers.get(i).getChildCount();
                Log.e("currentContainer","文件夹"+i+"有文件夹"+childCount);
                if (containers.get(i).getItems()!=null && containers.get(i).getItems().size()>0){
                    List<Item> items = containers.get(i).getItems();
                    for (int j = 0; j < items.size(); j++) {
                        Log.e("currentContainer","当前item有"+items.get(j).getTitle());
                    }
                }
            }
        }



        this.content = content;
        for (int i = 0; i < content.getCount(); i++) {

        }


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
