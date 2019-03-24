package zmovie.com.dlan;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yanbo.lib_screen.entity.ClingDevice;
import com.yanbo.lib_screen.manager.DeviceManager;

import java.util.List;

/**
 * 描述：
 *
 * @author Yanbo
 * @date 2018/11/6
 */
public class ClingDeviceAdapter extends RecyclerView.Adapter<ClingDeviceAdapter.ClingHolder> {

    private LayoutInflater layoutInflater;
    private List<ClingDevice> clingDevices;
    private OnDeviceCheckedListener clickListener;
    private Context context;

    public ClingDeviceAdapter(Context context) {
        super();
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        clingDevices = DeviceManager.getInstance().getClingDeviceList();
    }

    @NonNull
    @Override
    public ClingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_common_layout, parent, false);
        return new ClingHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClingHolder holder, final int position) {
        final ClingDevice device = clingDevices.get(position);
        if (device != null && device == DeviceManager.getInstance().getCurrClingDevice()) {
            holder.dlanstatu.setText("已连接");
            holder.dlan_signal.setSelected(true);

        } else {
            holder.dlanstatu.setText("已断开");
            holder.dlan_signal.setSelected(false);
        }

        if (device == null) {
            holder.nameView.setText("Unknow device");
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "未知设备，无法连接", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            holder.nameView.setText(device.getDevice().getDetails().getFriendlyName());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (clickListener != null) {
                        clickListener.onItemChecked(position, device);
                    }
                }
            });
        }

        if (clickListener!=null){
            clickListener.onRefreshed();
        }

    }

    @Override
    public int getItemCount() {
        return clingDevices.size();
    }

    public void refresh() {
        clingDevices = DeviceManager.getInstance().getClingDeviceList();
        notifyDataSetChanged();
        if (clickListener!=null){
            clickListener.onRefreshed();
        }
    }

    public void setItemClickListener(OnDeviceCheckedListener listener) {
        this.clickListener = listener;
    }

    static class ClingHolder extends RecyclerView.ViewHolder {
        TextView nameView;
        ImageView iconView;
        TextView dlanstatu;
        ImageView dlan_signal;
        public ClingHolder(View itemView) {
            super(itemView);
            nameView = itemView.findViewById(R.id.text_name);
            iconView = itemView.findViewById(R.id.img_icon);
            dlanstatu = itemView.findViewById(R.id.dlan_statu);
            dlan_signal = itemView.findViewById(R.id.dlan_signal);
        }
    }


    public interface OnDeviceCheckedListener {
        void onItemChecked(int position, Object device);

        void onItemCancelChose(int position, Object device);

        void onRefreshed();
    }

}
