package com.gank.chen.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 *
 * @author ChenBo
 * @date 2018/5/30
 */

public class NativeShareUtil {
    /**
     * 分享图片
     */
    public static void shareImage(Context context, Uri uri, String title) {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
        shareIntent.setType("image/*");
        //添加分享内容标题
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "分享到");
        shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(Intent.createChooser(shareIntent, title));
    }
}
