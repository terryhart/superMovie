package zmovie.com.dlan;

import android.content.Context;

import com.yanbo.lib_screen.VApplication;

/**
 * 描述：
 *
 * @author Yanbo
 * @date 2018/11/6
 */
public class DlanLib {

    public static final int DLAN_MODE_ONLINE = 100;
    public static final int DLAN_MODE_LOCAL = 101;


    protected static Context context;

    public static Context getContext() {
        return context;
    }


    public static void initDlan(Context contexts){
        context = contexts;
        VApplication.init(contexts);
    }

}
