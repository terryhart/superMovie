package com.bftv.myapplication.view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bftv.myapplication.R;
import com.bftv.myapplication.util.DensityUtil;


/**
 * Created by Lunger on 2015/03/01
 */
public class PopSpinnerView extends RelativeLayout {

    private final int SPACETOLEFTIMAGE = 12;  //文字距离左边图标像素距离

    /**
     * 自定义属性的参数 开始
     */
    private String textName; //未选择时提示的文字
    private int itemWidth;  //控件宽度
    private Drawable LeftImageDrawable;
    /**
     * 自定义属性的参数 结束
     */

    private ImageView mIv_arrow, mIv_leftIcon;
    private TextView mTv_content;
    private PopupWindow popupWindow;
    private ListView lv;
    private int height;
    private int listSize;
    private int curIndex = -1;
    private NameFilter nameFilter;

    public PopSpinnerView(final Context context) {
        super(context);
        initView(context);
    }

    public PopSpinnerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
        initXmlConfigAttr(attrs);
    }

    public PopSpinnerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
        initXmlConfigAttr(attrs);
    }

    /**
     * 外部调用，用于初始化控件
     *
     * @param size
     * @param itemWidth
     * @param nameFilter
     */
    public void init(int size, int itemWidth, NameFilter nameFilter) {
        this.itemWidth = itemWidth;
        this.listSize = size;
        this.nameFilter = nameFilter;
    }


    private void initView(final Context context) {
        //将控件布局填充进来
        LayoutInflater.from(context).inflate(R.layout.popspinerview, this);
        mIv_arrow = findViewById(R.id.iv_arrow);
        mIv_leftIcon = findViewById(R.id.iv_leftImage);
        mTv_content = findViewById(R.id.tv_content);
        //控件的点击事件
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //弹窗的view
                View view = LayoutInflater.from(context).inflate(R.layout.pop_layout, null);
                initListView(context, view);
                initPopWindow(view, context);
                arrowRotation(0, -180f);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                     //   changeTextBackground(R.drawable.shape_button_pure_white30pxtop_nobottom);
                    }
                }, 220);

            }
        });
    }

    private void initListView(Context context, View view) {
        lv = view.findViewById(R.id.lv);
        lv.setAdapter(new MyAdapter(context));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //记录选中的角标
                curIndex = position;
                mTv_content.setText(nameFilter.filter(position));
                popupWindow.dismiss();
            }
        });
    }

    private void initPopWindow(View view, Context context) {
        height = listSize >= 8 ? 387 : (listSize * 80 - 50) < 80 ? 80 : (listSize * 80 - 75);
        popupWindow = new PopupWindow(view, itemWidth, DensityUtil.dip2px(context, height));
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.showAsDropDown(mTv_content);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                changeTextBackground(R.drawable.shape_button_pure_white30px_normal);
                arrowRotation(-180f, 0);
            }
        });
    }

    /**
     * 设置默认显示的文字
     *
     * @param content
     */
    public void setContent(String content) {
        mTv_content.setText(content);
    }

    public String getContent() {
        CharSequence text = mTv_content.getText();
        if (text == null)
            return "";
        return text.toString();
    }

    class MyAdapter extends BaseAdapter {
        private Context context;

        public MyAdapter(Context context) {
            this.context = context;
        }

        @Override
        public int getCount() {
            return listSize;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(context).inflate(R.layout.item_lv_pop_category, null);
                holder.tv = convertView.findViewById(R.id.tv_name);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.tv.setText(nameFilter.filter(position));
            return convertView;
        }
    }

    class ViewHolder {
        TextView tv;
    }

    /**
     * xml属性
     *
     * @param attrs
     */
    private void initXmlConfigAttr(AttributeSet attrs) {
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.PopSpinnerView);
        LeftImageDrawable = ta.getDrawable(R.styleable.PopSpinnerView_LeftImageDrawable);
        if (LeftImageDrawable != null) {
            mIv_leftIcon.setVisibility(VISIBLE);
            mIv_leftIcon.setImageDrawable(LeftImageDrawable);
            mTv_content.setPadding(LeftImageDrawable.getIntrinsicWidth() + SPACETOLEFTIMAGE + mTv_content.getPaddingLeft(), 0, 0, 0);
        } else {
            mIv_leftIcon.setVisibility(GONE);
        }
        textName = ta.getString(R.styleable.PopSpinnerView_textName);
        if (!TextUtils.isEmpty(textName)) {
            mTv_content.setText(textName);
        }
        itemWidth = ta.getInt(R.styleable.PopSpinnerView_popWidth, 0);

        ta.recycle();
    }

    /**
     * 内容回调接口
     */
    public interface NameFilter {
        String filter(int position);
    }

    /**
     * 获取当前选择的item的index
     *
     * @return
     */
    public int getSelectIndex() {
        return curIndex;
    }

    /**
     * 设置选中的item的index
     *
     * @return
     */
    public void setSelectIndex(int index) {
        curIndex = index;
    }

    private void arrowRotation(float angleStart, float angleEnd) {
        ObjectAnimator anim = ObjectAnimator.ofFloat(mIv_arrow, "rotation", angleStart, angleEnd);
        anim.setDuration(500);
        anim.start();
    }

    private void changeTextBackground(int res) {
        int paddingLeft = mTv_content.getPaddingLeft();
        mTv_content.setBackgroundResource(res);
        mTv_content.setPadding(paddingLeft, 0, 0, 0);
    }
}
