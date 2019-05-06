package com.e9ab98e991ab.libcommon.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.text.TextUtils;

import java.io.IOException;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/***
 * 渠道区分
 */
public class ChannelUtils {
    private static final String TAG = "ChannelUtils";
    private static final String XIAOY_CHANNEL = "6-0";

    public static String getXiaoYSDKChannel(Context context){
        return TextUtils.isEmpty(ChannelUtils.getChannelName(context))? XIAOY_CHANNEL:ChannelUtils.getChannelName(context);
    }

    public static String getChannelName(Context context) {
        //从apk包中获取
        ApplicationInfo appInfo = context.getApplicationInfo();
        String sourceDir = appInfo.sourceDir;
        //注意这里：默认放在meta-inf/里， 所以需要再拼接一下
        String key = "META-INF/yh";
        String ret = "";
        ZipFile zipfile = null;
        try {
            zipfile = new ZipFile(sourceDir);
            Enumeration<?> entries = zipfile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = ((ZipEntry) entries.nextElement());
                String entryName = entry.getName();
                if (entryName.startsWith(key)) {
                    ret = entryName;
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (zipfile != null) {
                try {
                    zipfile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        String[] split = ret.split("_");
        String channel = "";
        if (split != null && split.length >= 2) {
            channel = ret.substring(split[0].length() + 1);
        }
//        LogUtils.i("-ChannelName-",channel);
        return channel;
    }
}
