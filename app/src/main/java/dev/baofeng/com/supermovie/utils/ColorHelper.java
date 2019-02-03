package dev.baofeng.com.supermovie.utils;

import android.graphics.Color;
import android.util.Log;

/**
 * creator huangyong
 * createTime 2019/2/2 下午8:40
 * path dev.baofeng.com.supermovie.utils
 * description:
 */
public class ColorHelper {


    /**
     * 颜色加深处理
     *
     * @param RGBValues RGB的值，由alpha（透明度）、red（红）、green（绿）、blue（蓝）构成，
     *                  Android中我们一般使用它的16进制，
     *                  例如："#FFAABBCC",最左边到最右每两个字母就是代表alpha（透明度）、
     *                  red（红）、green（绿）、blue（蓝）。每种颜色值占一个字节(8位)，值域0~255
     *                  所以下面使用移位的方法可以得到每种颜色的值，然后每种颜色值减小一下，在合成RGB颜色，颜色就会看起来深一些了
     * @return
     */
    public static int colorBurn(int RGBValues) {
        int alpha = RGBValues >> 24;
        int red = RGBValues >> 16 & 0xFF;
        int green = RGBValues >> 8 & 0xFF;
        int blue = RGBValues & 0xFF;
        red = (int) Math.floor(red * (1 - 0.4));
        green = (int) Math.floor(green * (1 - 0.4));
        blue = (int) Math.floor(blue * (1 - 0.4));
        Log.e("testcolor", red + "" + green + "" + blue);
        return Color.rgb(red, green, blue);
    }
}
