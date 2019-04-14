package dev.baofeng.com.supermovie.view;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.huangyong.downloadlib.db.HistoryDao;
import com.huangyong.downloadlib.domain.HistoryInfo;
import com.yanzhenjie.recyclerview.OnItemMenuClickListener;
import com.yanzhenjie.recyclerview.SwipeMenu;
import com.yanzhenjie.recyclerview.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.SwipeMenuItem;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

import java.util.List;

import app.huangyong.com.common.widget.timeline.itemdecoration.DotItemDecoration;
import app.huangyong.com.common.widget.timeline.itemdecoration.SpanIndexListener;
import butterknife.BindView;
import butterknife.ButterKnife;
import dev.baofeng.com.supermovie.R;
import dev.baofeng.com.supermovie.adapter.HistoryAdapter;
import dev.baofeng.com.supermovie.utils.Util;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

public class HistoryActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.rv_his_list)
    SwipeRecyclerView rvHisList;
    @BindView(R.id.root)
    LinearLayout root;
    private HistoryAdapter adapter;
    private List<HistoryInfo> historyInfos;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_layout);
        ButterKnife.bind(this);

        initData();

    }

    private void initData() {
        // 创建菜单：
        SwipeMenuCreator mSwipeMenuCreator = new SwipeMenuCreator() {
            @Override
            public void onCreateMenu(SwipeMenu leftMenu, SwipeMenu rightMenu, int position) {
                SwipeMenuItem deleteItem = new SwipeMenuItem(HistoryActivity.this);
                deleteItem.setText("删除");
                deleteItem.setBackgroundColor(Color.parseColor("#F34127"));
                deleteItem.setTextColor(Color.WHITE);
                deleteItem.setHeight(MATCH_PARENT);
                deleteItem.setWidth(Util.Dp2px(HistoryActivity.this, 90));
                // 各种文字和图标属性设置。
                rightMenu.addMenuItem(deleteItem);
                // 在Item左侧添加一个菜单。
            }
        };
        // 设置监听器。
        rvHisList.setSwipeMenuCreator(mSwipeMenuCreator);

        // 菜单点击监听。
        rvHisList.setOnItemMenuClickListener(mItemMenuClickListener);

        initHistory();


    }

    private void initHistory() {
        HistoryDao dao = HistoryDao.getInstance(getApplicationContext());
        historyInfos = dao.queryAll();

        if (historyInfos != null && historyInfos.size() > 0) {
            rvHisList.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
//            rvHisList.addItemDecoration(new ItemDecoration(HistoryActivity.this, 100));


            DotItemDecoration mItemDecoration = new DotItemDecoration
                    .Builder(this)
                    .setOrientation(DotItemDecoration.VERTICAL)//if you want a horizontal item decoration,remember to set horizontal orientation to your LayoutManager
                    .setItemStyle(DotItemDecoration.STYLE_DRAW)
                    .setTopDistance(30)//dp
                    .setItemInterVal(60)//dp
                    .setItemPaddingLeft(20)//default value equals to item interval value
                    .setItemPaddingRight(20)//default value equals to item interval value
                    .setDotColor(Color.RED)
                    .setDotRadius(2)//dp
                    .setDotPaddingTop(0)
                    .setDotInItemOrientationCenter(false)//set true if you want the dot align center
                    .setLineColor(Color.WHITE)
                    .setLineWidth(1)//dp
                    .setEndText("END")
                    .setTextColor(Color.WHITE)
                    .setTextSize(10)//sp
                    .setDotPaddingText(2)//dp.The distance between the last dot and the end text
                    .setBottomDistance(40)//you can add a distance to make bottom line longer
                    .create();
            mItemDecoration.setSpanIndexListener(new SpanIndexListener() {
                @Override
                public void onSpanIndexChange(View view, int spanIndex) {
                    Log.i("Info","view:"+view+"  span:"+spanIndex);
                    //设置item的背景图，其实也可以在item布局文件里搞
                    //view.setBackgroundResource(spanIndex == 0 ? R.drawable.pop_left : R.drawable.pop_right);
                }
            });
            rvHisList.addItemDecoration(mItemDecoration);



            adapter = new HistoryAdapter(HistoryActivity.this, historyInfos);
            rvHisList.setAdapter(adapter);
        } else {
            Toast.makeText(this, "暂无观看记录哦", Toast.LENGTH_SHORT).show();
        }
    }

    OnItemMenuClickListener mItemMenuClickListener = new OnItemMenuClickListener() {
        @Override
        public void onItemClick(SwipeMenuBridge menuBridge, int position) {
            // 任何操作必须先关闭菜单，否则可能出现Item菜单打开状态错乱。
            menuBridge.closeMenu();

            // 左侧还是右侧菜单：
            int direction = menuBridge.getDirection();
            // 菜单在Item中的Position：
            int menuPosition = menuBridge.getPosition();
            if (historyInfos != null && historyInfos.size() > menuPosition) {

                HistoryDao dao = HistoryDao.getInstance(getApplicationContext());
                if (historyInfos.size() > 0 && historyInfos.get(position) != null) {
                    dao.delete(historyInfos.get(position).getId());

                    Toast.makeText(HistoryActivity.this, "已删除", Toast.LENGTH_SHORT).show();
                    historyInfos.remove(menuPosition);
                    adapter.notifyDataSetChanged();
                }
            }

        }
    };

    @Override
    public void onClick(View v) {

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setIcon(R.mipmap.icon)
                .setTitle("提示！")
                .setMessage("是否清空历史记录")
                //设置对话框的按钮
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        HistoryDao dao = HistoryDao.getInstance(getApplicationContext());
                        dao.deleteAll();
                        if (adapter != null) {
                            adapter.clear();
                            adapter.notifyDataSetChanged();
                        }
                        dialog.dismiss();
                    }
                }).create();
        dialog.show();


    }
}
