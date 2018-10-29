package com.gank.chen.util;

import android.content.Context;
import android.content.res.Resources;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gank.chen.R;


/**
 * Created by rick
 * @author chenbo
 */
public class ToastUtils {
    private Context context;
    private Resources resources;
    private static Toast mToast;
    private static TextView text;
    private static ImageView img;

    private static String oldMsg;
    protected static Toast toast = null;
    private static long oneTime = 0;
    private static long twoTime = 0;

    public ToastUtils(Context context) {
        this.context = context;
        this.resources = context.getResources();
        View v = LayoutInflater.from(context).inflate(R.layout.toast_warning, null);
        text = v.findViewById(R.id.txt_toast_warning);
        img = v.findViewById(R.id.img_toast_warning);
        mToast = new Toast(context);
        mToast.setView(v);
    }

    public static void showToast(Context context, String s) {
        if (toast == null) {
            toast = Toast.makeText(context, s, Toast.LENGTH_SHORT);
            toast.show();
            oneTime = System.currentTimeMillis();
        } else {
            twoTime = System.currentTimeMillis();
            if (s.equals(oldMsg)) {
                if (twoTime - oneTime > Toast.LENGTH_SHORT) {
                    toast.show();
                }
            } else {
                oldMsg = s;
                toast.setText(s);
                toast.show();
            }
        }
        oneTime = twoTime;
    }




    /**
     * 于屏幕上部显示Toast
     *
     * @param data
     */
    public void toastTop(String data, int imgRes, int ToastLength) {
        int y = PixelFormatUtil.formatDipToPx(context, 20);
        mToast.setGravity(Gravity.TOP, 0, y);
        mToast.setDuration(ToastLength);
        text.setText(data);
        img.setImageResource(imgRes);
        mToast.show();
    }

    public void toastTop(int data, int imgRes, int ToastLength) {
        toastTop(resources.getString(data), imgRes, ToastLength);
    }

    public void toastTop(String data, int ToastLength) {
        int y = PixelFormatUtil.formatDipToPx(context, 20);
        mToast.setGravity(Gravity.TOP, 0, y);
        mToast.setDuration(ToastLength);
        text.setText(data);
        img.setVisibility(View.GONE);
        mToast.show();
    }

    public void toastTop(int data, int ToastLength) {
        toastTop(resources.getString(data), ToastLength);
    }

    /**
     * 于屏幕中央显示Toast
     *
     * @param data
     */
    public void toastCenter(String data, int imgRes, int ToastLength) {
        int y = resources.getDisplayMetrics().heightPixels / 2;
        mToast.setGravity(Gravity.TOP, 0, y);
        mToast.setDuration(ToastLength);
        text.setText(data);
        img.setImageResource(imgRes);
        mToast.show();
    }

    public void toastCenter(int data, int imgRes, int ToastLength) {
        toastCenter(resources.getString(data), imgRes, ToastLength);
    }

    public void toastCenter(String data, int ToastLength) {
        int y = resources.getDisplayMetrics().heightPixels / 2;
        mToast.setGravity(Gravity.TOP, 0, y);
        mToast.setDuration(ToastLength);
        text.setText(data);
        text.setTextSize(14);
        img.setVisibility(View.GONE);
        mToast.show();
    }

    public void toastCenter(int data, int ToastLength) {
        toastCenter(resources.getString(data), ToastLength);
    }

}
