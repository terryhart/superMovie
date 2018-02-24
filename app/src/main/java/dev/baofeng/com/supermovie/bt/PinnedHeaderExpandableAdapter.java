package dev.baofeng.com.supermovie.bt;

import android.content.Context;
import android.graphics.Color;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import dev.baofeng.com.supermovie.R;

public class PinnedHeaderExpandableAdapter extends BaseExpandableListAdapter
        implements PinnedHeaderExpandableListView.HeaderAdapter {

    private Context context;
    private PinnedHeaderExpandableListView listView;
    private LayoutInflater inflater;

    public SwipeLayout openLayout;
    private SwipeLayout swipeLayout;
    private TranslateAnimation taLeft;
    private List<FocusGroupBean> focusGroupList;

    public PinnedHeaderExpandableAdapter(List<FocusGroupBean> focusGroupList,
                                         Context context, PinnedHeaderExpandableListView listView) {
        this.focusGroupList = focusGroupList;

        this.context = context;
        this.listView = listView;
        inflater = LayoutInflater.from(this.context);

        taLeft = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, -1.0f, Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        taLeft.setDuration(600);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        if (focusGroupList != null && focusGroupList.size() > 0) {
            List<VeDetailBean> vehiclelist = focusGroupList.get(groupPosition)
                    .getGroupList();
            if (vehiclelist != null && vehiclelist.size() > 0) {
                return vehiclelist.get(childPosition).getLicensePlate();
            }
        }
        return "";

    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        View view = null;
        if (convertView != null) {
            view = convertView;
        } else {
            view = createChildrenView();
        }

        swipeLayout = (SwipeLayout) view.findViewById(R.id.swipelayout);
        TextView text = (TextView) view.findViewById(R.id.tv_name);
        final TextView itemDelete = (TextView) view
                .findViewById(R.id.focus_item_delete);
        final TextView itemImfor = (TextView) view
                .findViewById(R.id.focus_item_imfor);
        final View animView = view;


        // 点击删除按钮
        itemDelete.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // 开启网络请求 删除车辆数据
                postRequestDeleteVehicle(animView, groupPosition, childPosition);
            }
        });

        swipeLayout.setOnOpenListener(new SwipeLayout.OnOpenListener() {
            @Override
            public void isOpen(boolean isopen, SwipeLayout swipeLayout) {

                // 根据标示判断是打开还是关闭
                if (isopen) {
                    // 打开，保存打开的条目
                    // 如果打开的是另一个条目，判断是否保存过打开的条目，保存，关闭保存条目
                    // 因为自定义控件中的onViewPositionChanged方法会不断的执行，会不断的调用回调，会不断的将swipelayout对象传递过来
                    // 所以可能会出现，当一个条目还没有打开，执行多次onViewPositionChanged方法，回调被调用多次，会执行关闭操作
                    if (openLayout != null && openLayout != swipeLayout) {
                        openLayout.closeLayoutQuick();
                    }

                    openLayout = swipeLayout;
                } else {
                    // 关闭，关闭的打开过的同一个条目
                    if (openLayout == swipeLayout) {
                        openLayout = null;
                    }
                }
            }
        });

        // 点击详情按钮
        itemImfor.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                // 弹出点击提示
                Toast.makeText(context, "点击了" + focusGroupList.get(groupPosition).getGroupName() + "的  " + focusGroupList.get(groupPosition).getGroupList().get(childPosition).getLicensePlate(), Toast.LENGTH_SHORT).show();

            }
        });
        text.setText(focusGroupList.get(groupPosition).getGroupList()
                .get(childPosition).getLicensePlate());
        return view;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if (focusGroupList != null && focusGroupList.size() > 0) {
            FocusGroupBean focusGroupBean = focusGroupList.get(groupPosition);
            List<VeDetailBean> vehiclelist = focusGroupBean.getGroupList();
            if (vehiclelist == null) {
                return 0;
            } else if (vehiclelist.size() == 1
                    && vehiclelist.get(0).getLicensePlate() == null) {
                // 由于当新建空组后重新获取数据则获取到的车辆集合长度为1只是里面的所有字段都为空 所以再次额外添加
                // 判断 如果字段为空的话则将其子条目的数量强制转换成0
                return 0;
            } else if (vehiclelist.size() > 0) {
                return vehiclelist.size();
            }
        }
        return 0;
    }

    @Override
    public Object getGroup(int groupPosition) {
        if (focusGroupList != null && focusGroupList.size() > 0) {
            return focusGroupList.get(groupPosition).getGroupName();
        }
        return "";

    }

    @Override
    public int getGroupCount() {
        if (focusGroupList != null && focusGroupList.size() > 0) {
            return focusGroupList.size();
        }
        return 0;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        View view = null;
        if (convertView != null) {
            view = convertView;
        } else {
            view = createGroupView();
        }

        ImageView arrow = (ImageView) view.findViewById(R.id.arrow_right);

        // 此处用来设置点击分组的时候切换箭头的按钮
        if (isExpanded) {
            arrow.setImageResource(R.drawable.arrow_down);
        } else {
            arrow.setImageResource(R.drawable.arrow_right);
        }

        TextView text = (TextView) view.findViewById(R.id.groupto);
        text.setText(focusGroupList.get(groupPosition).getGroupName());
        if (focusGroupList.get(groupPosition).getIsDefault() == 1) {
            // 如果当前组是默认分组 则将其字体设置为红色
            text.setTextColor(Color.RED);
        } else {
            text.setTextColor(Color.parseColor("#000000"));
        }

        return view;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private View createChildrenView() {
        return inflater.inflate(R.layout.child, null);
    }

    private View createGroupView() {
        return inflater.inflate(R.layout.group, null);
    }

    @Override
    public int getHeaderState(int groupPosition, int childPosition) {
        final int childCount = getChildrenCount(groupPosition);
        if (childPosition == childCount - 1) {
            return PINNED_HEADER_PUSHED_UP;
        } else if (childPosition == -1
                && !listView.isGroupExpanded(groupPosition)) {
            return PINNED_HEADER_GONE;
        } else {
            return PINNED_HEADER_VISIBLE;
        }
    }

    @Override
    public void configureHeader(View header, int groupPosition,
                                int childPosition, int alpha) {

        if (focusGroupList != null && focusGroupList.size() > 0) {
            String groupData = focusGroupList.get(groupPosition)
                    .getGroupName();
            ((TextView) header.findViewById(R.id.groupto)).setText(groupData);
        }

    }

    private SparseIntArray groupStatusMap = new SparseIntArray();

    @Override
    public void setGroupClickStatus(int groupPosition, int status) {
        groupStatusMap.put(groupPosition, status);
    }

    @Override
    public int getGroupClickStatus(int groupPosition) {
        if (groupStatusMap.keyAt(groupPosition) >= 0) {
            return groupStatusMap.get(groupPosition);
        } else {
            return 0;
        }
    }

    /**
     * 定义方法 删除指定车辆的信息
     *
     * @param animView
     * @param childPosition
     * @param groupPosition
     */
    private void postRequestDeleteVehicle(final View animView,
                                          final int groupPosition, final int childPosition) {
        // 获取要删除下载任务的信息
        FocusGroupBean focusGroupBean = focusGroupList.get(groupPosition);
        VeDetailBean veDetailBean = focusGroupBean.getGroupList().get(
                childPosition);
        Toast.makeText(context, "删除任务成功",
                Toast.LENGTH_SHORT).show();
        // 开启动画的监听
        taLeft.setAnimationListener(new AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                // 动画开始的时候
                if (openLayout != null) {
                    openLayout.closeLayoutQuick();
                }
            }

            @Override
            public void onAnimationRepeat(
                    Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // 动画结束之后 删除数据
                List<VeDetailBean> vehiclelist = focusGroupList
                        .get(groupPosition)
                        .getGroupList();
                VeDetailBean veDetailBean = vehiclelist
                        .get(childPosition);
                for (int i = 0, len = vehiclelist.size(); i < len; ++i) {
                    if (vehiclelist
                            .get(i)
                            .getLicensePlate()
                            .equals(veDetailBean
                                    .getLicensePlate())) {
                        vehiclelist.remove(i);
                        --len;
                        --i;
                    }
                }
                // 刷新布局
                notifyDataSetChanged();
            }

        });
        // 开启动画
        animView.startAnimation(taLeft);

    }

}
