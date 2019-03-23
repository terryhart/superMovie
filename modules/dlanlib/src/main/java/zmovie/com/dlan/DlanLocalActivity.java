package zmovie.com.dlan;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.yanbo.lib_screen.manager.ClingManager;

import org.fourthline.cling.support.model.DIDLObject;
import org.fourthline.cling.support.model.ProtocolInfo;
import org.fourthline.cling.support.model.Res;
import org.fourthline.cling.support.model.container.Container;
import org.fourthline.cling.support.model.item.Item;
import org.fourthline.cling.support.model.item.VideoItem;
import org.seamless.util.MimeType;

import java.util.List;

public class DlanLocalActivity extends AppCompatActivity {
    //设置本地投屏的信息
    private List<DIDLObject> objectList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dlan_local);
        RecyclerView list= findViewById(R.id.listLocal);


//        String creator = "unknow";
//        String resolution = "unknow";
//        String metadata = null;
//        VideoItem videoItem = new VideoItem("", "0", "", creator, res);
//        metadata = createItemMetadata(videoItem);
//
//
//        final DIDLObject object = objectList.get(0);
//
//        if (object instanceof Container) {
//            //得到本地文件夹
//            Container container = (Container) object;
//            //点进文件夹刷新数据List<DIDLObject> objectList
//            ClingManager.getInstance().searchLocalContent(containerId);
//        } else if (object instanceof Item) {
//            //得到本地文件
//            Item item = (Item) object;
//            // 设置本地投屏的信息
//            ClingManager.getInstance().setLocalItem(item);
//        }

    }
}
