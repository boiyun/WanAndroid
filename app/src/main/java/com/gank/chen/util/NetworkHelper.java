package com.gank.chen.util;

import android.app.AlertDialog.Builder;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;

/**
 * @author chenbo
 */
public class NetworkHelper {
    private static ConnectivityManager connManager = null;
    private static final int NETWORN_NONE = 0;
    private static final int NETWORN_WIFI = 1;
    private static final int NETWORN_MOBILE = 2;

    private static boolean isDialogShowing;

    public static int getNetworkState(Context context) {
        ConnectivityManager connManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        try {
            // Wifi
            State state = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                    .getState();
            if (state == State.CONNECTED || state == State.CONNECTING) {
                return NETWORN_WIFI;
            }

            // 3G
            state = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
                    .getState();
            if (state == State.CONNECTED || state == State.CONNECTING) {
                return NETWORN_MOBILE;
            }

            return NETWORN_NONE;
        } catch (Exception e) {
            e.printStackTrace();
            return NETWORN_NONE;
        }
    }


    /**
     * 常用检查策略
     * @param context
     */
//    public static void checkNetWork(Context context){
//        if (!NetworkHelper.isNetworkConnected(context)){
//            NetworkHelper.alertSetNetwork(context);
//        }
//    }


    /**
     * 网络是否已经连接
     *
     * @return true, 可用； false， 不可用
     */
    public static boolean isNetworkConnected(Context context) {
        if (isNetworkAvailable(context)) {
            int type = getConnectionType(context);
            return type == 2 || type == 1;
        }
        return false;
    }

    /**
     * 对网络连接是否可用
     *
     * @return true, 可用； false， 不可用
     */
    public static boolean isNetworkAvailable(Context context) {
        if (context != null) {
            connManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connManager.getActiveNetworkInfo();
            if (networkInfo != null) {
                return networkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * 对wifi连接状态进行判断
     *
     * @return true, 可用； false， 不可用
     */
    public static boolean isWifiConnected(Context context) {
        if (context != null) {
            connManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo wifiInfo = connManager
                    .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (wifiInfo != null) {
                return wifiInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * 对MOBILE网络连接状态进行判断
     *
     * @return true, 可用； false， 不可用
     */
    public static boolean isMobileConnected(Context context) {
        if (context != null) {
            connManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mobileInfo = connManager
                    .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (mobileInfo != null) {
                return mobileInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * 获取当前网络连接的类型信息
     *
     * @return one of TYPE_MOBILE, TYPE_WIFI, TYPE_WIMAX, TYPE_ETHERNET,
     * TYPE_BLUETOOTH, or other types defined by ConnectivityManager
     * int值分别为：0、1、6、9、7
     */
    public static int getConnectionType(Context context) {
        if (context != null) {
            connManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isAvailable()) {
                return networkInfo.getType();
            }
        }
        return -1;
    }

    /**
     * 提示设置网络连接
     */
    public static void alertSetNetwork(final Context context) {
        if (isDialogShowing) {
            return;
        }

        Builder builder = new Builder(context);
        builder.setTitle("提示：网络异常").setMessage("是否对网络进行设置?");

        builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = null;
                try {
                    int sdkVersion = android.os.Build.VERSION.SDK_INT;
                    if (sdkVersion > 10) {
                        intent = new Intent(
                                android.provider.Settings.ACTION_WIRELESS_SETTINGS);
                    } else {
                        intent = new Intent();
                        ComponentName comp = new ComponentName(
                                "com.android.settings",
                                "com.android.settings.WirelessSettings");
                        intent.setComponent(comp);
                        intent.setAction("android.intent.action.VIEW");
                    }
                    context.startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                isDialogShowing = false;

            }
        });
        builder.setNegativeButton("否", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                isDialogShowing = false;
            }
        });
        builder.show();
        isDialogShowing = true;
    }

    public static void destroy() {
        if (connManager != null) {
            connManager = null;
        }
    }
}
