package com.e9ab98e991ab.libcommon.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static com.e9ab98e991ab.libcommon.utils.MD5Utils.md5;


/**
 * App相关的辅助类
 */
public class AppUtils {

    private Context context;
    private boolean installFalg;

    public static final String TAG = AppUtils.class.getSimpleName();

    private AppUtils() {
        /**cannot be instantiated **/
        throw new UnsupportedOperationException("cannot be instantiated");

    }

    public AppUtils(Context context) {
        this.context = context;
    }

    //根据apk获取应用包名
    public static String getApkInfo(Context context, String path){
        PackageManager pm = context.getPackageManager();
        PackageInfo info = pm.getPackageArchiveInfo(path, PackageManager.GET_ACTIVITIES);
        ApplicationInfo appInfo = null;
        if (info != null) {
            appInfo = info.applicationInfo;
            return appInfo.packageName;
        }else {
            return null;
        }
    }

    /***
     * 获取apk信息
     * @param context
     * @param path
     * @return
     */
    public static PackageInfo getApkMessage(Context context, String path){
        PackageManager pm = context.getPackageManager();
        PackageInfo info = pm.getPackageArchiveInfo(path, PackageManager.GET_ACTIVITIES);
        return info;
    }

    /***
     * 判断是否安装
     * @param context
     * @param packageName
     * @return
     */
    public static boolean isAppInstalled(Context context, String packageName) {
        final PackageManager packageManager = context.getPackageManager();
        //取得所有的PackageInfo
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        List<String> pName = new ArrayList<>();
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                pName.add(pn);
            }
        }
        //判断包名是否在系统包名列表中
        return pName.contains(packageName);
    }
    public static boolean openAPK(Context mContext, String packagename)
    {
        PackageManager packageManager = mContext.getPackageManager();
        Intent intent=new Intent();
        intent =packageManager.getLaunchIntentForPackage(packagename);
        if(intent==null){
            Toast.makeText(mContext, "未安装", Toast.LENGTH_LONG).show();
            return false;
        }else{
            mContext.startActivity(intent);
            return true;
        }

    }

    /**
     * 获取应用程序名称
     */
    public static String getAppName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取应用版本号
     *
     * @param context
     * @return 当前应用的版本名称
     */
    public static String getVersionName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            return packageInfo.versionName;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 判断网络是否可用
     *
     * @param context Context对象
     */
    public static Boolean isNetworkReachable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo current = cm.getActiveNetworkInfo();
        if (current == null) {
            return false;
        }
        return (current.isAvailable());
    }

    /**
     * 获取view的bitmap
     * @param v
     * @return
     */
    public static Bitmap getBitmapFromView(View v)
    {
        Bitmap b = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.RGB_565);
        Canvas c = new Canvas(b);
        v.layout(v.getLeft(), v.getTop(), v.getRight(), v.getBottom());
        // Draw background
        Drawable bgDrawable = v.getBackground();
        if (bgDrawable != null)
        {
            bgDrawable.draw(c);
        }
        else
        {
            c.drawColor(Color.WHITE);
        }
        // Draw view to canvas
        v.draw(c);
        return b;
    }

    /***
     * 修改字体大小
     * @param va 值
     * @param fontSize 字体大小
     * @param start 从第几位开始
     * @param fontLength 从第几位结束
     * @return
     */
    public static Spannable modifyFontSize(String va, int fontSize, int start, int fontLength) {
        Spannable sp = new SpannableString(va);
        sp.setSpan(new AbsoluteSizeSpan(fontSize, true), start, fontLength, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        return sp;
    }


    /**
     * get the version of the application
     *
     * @param mContext
     * @return version code.
     */
    public static int getAppVersionCode(Context mContext) {
        PackageInfo pInfo = null;
        try {
            pInfo = mContext.getPackageManager().getPackageInfo(
                    mContext.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return pInfo.versionCode;
    }

    /**
     * 获得本机imei md5小写值
     *
     * @param context
     * @return
     */
    public static String getImeiMd5(Context context) {
        return md5(getImei(context)).toLowerCase();
    }

    /**
     * 获取mac
     *
     * @param context
     * @return
     */
    public static String getMac(Context context) {
        android.net.wifi.WifiManager wifi = (android.net.wifi.WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        return wifi.getConnectionInfo().getMacAddress();
    }

    /**
     * 获得imei
     *
     * @param context
     * @return
     */
    public static String getImei(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String deviceId = telephonyManager.getDeviceId();
        if (deviceId == null) {
            //android.provider.Settings;
            deviceId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        }
        return deviceId;
    }

    /**
     * 获取包信息
     *
     * @param context
     * @return
     */
    public static PackageInfo getPackageInfo(Context context) {
        PackageManager pm = context.getPackageManager();
        try {
            return pm.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, e.getLocalizedMessage());
        }
        return new PackageInfo();
    }

    /**
     * 获取手机型号
     *
     * @return
     */
    public static String getPhoneModel() {
        return Build.MODEL; // 手机型号
    }

    /**
     * 获取系统版本
     *
     * @return
     */
    public static String getSystemVersion() {
        return Build.VERSION.RELEASE;
    }


    // 手机制造商
    public static String getProduct() {
        return Build.PRODUCT;
    }

    // 系统定制商 手机品牌
    public static String getBrand() {
        return Build.BRAND;
    }

    // 硬件制造商
    public static String getManufacturer() {
        return Build.MANUFACTURER;
    }

    // 平台信息
    public static String getHardWare() {
        String result = Build.HARDWARE;
        if (result.matches("qcom")) {
            // LogUtils.i(TAG, "Qualcomm platform");
            result = "Qualcomm_" + result;
        } else if (result.matches("mt[0-9]*")) {
            result = "MTK_" + result;
        }
        return result;
    }

    // 型号
    public static String getMode() {
        return Build.MODEL;
    }

    /**
     * 判断是否平板设备
     *
     * @param context
     * @return true:平板,false:手机
     */
    public static boolean isTabletDevice(Context context) {
        return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >=
                Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    // CPU 指令集，可以查看是否支持64位
    public static String getCpuAbi() {
        return Build.CPU_ABI;
    }

    public static boolean isCpu64() {
        boolean result = false;

        if (Build.CPU_ABI.contains("arm64")) {
            result = true;
        }

        return result;
    }

    // 显示模块
    public static String getDisplay() {
        return Build.DISPLAY;
    }

    // SDK 当前版本号
    public static int getCurSDK() {
        return Build.VERSION.SDK_INT;
    }

    /**
     * 获取手机号
     *
     * @param context
     * @return
     */
    public static String getPhoneNumber(Context context) {
        android.telephony.TelephonyManager tm = (android.telephony.TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getLine1Number(); // 手机号码，有的可得，有的不可得
    }

    /**
     * 判断是否是模拟器
     *
     * @return
     */
    public static boolean isSimulator() {
        if (AntiEmulator.CheckDeviceIDS()) {
            Log.e(TAG, "CheckDeviceIDS");
            return true;
        }
        if (AntiEmulator.CheckImsiIDS()) {
            Log.e(TAG, "CheckImsiIDS");
            return true;
        }
        if (AntiEmulator.CheckPhoneNumber()) {
            Log.e(TAG, "CheckPhoneNumber");
            return true;
        }
        if (AntiEmulator.CheckEmulatorBuild()) {
            Log.e(TAG, "CheckEmulatorBuild");
            return true;
        }
        //        if (AntiEmulator.CheckEmulatorFiles()) {
        //            KLog.e("CheckEmulatorFiles");
        //            return true;
        //        }
        if (AntiEmulator.checkPipes()) {
            Log.e(TAG, "checkPipes");
            return true;
        }
        if (AntiEmulator.checkQEmuDriverFile()) {
            Log.e(TAG, "checkQEmuDriverFile");
            return true;
        }
        return false;
    }

    /**
     * 获取AndroidID
     *
     * @param context
     * @return
     */
    public static String getAndroidId(Context context) {
        return Settings.System.getString(context.getContentResolver(), Settings.System.ANDROID_ID);
    }


    /**
     * 获得屏幕宽度
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    /**
     * 获得屏幕高度
     *
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }


    /**
     * 获取当前本地apk的版本
     *
     * @param mContext
     * @return
     */
    public static int getVersionCode(Context mContext) {
        int versionCode = 0;
        try {
            //获取软件版本号，对应AndroidManifest.xml下android:versionCode
            versionCode = mContext.getPackageManager().
                    getPackageInfo(mContext.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    /**
     * 判断当前设备是手机还是平板，代码来自 Google I/O App for Android
     * @param context
     * @return 平板返回 True，手机返回 False
     */
    public static boolean isPad(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    public static String isDevice(Context context){
        if (isPad(context)){
            return "平板";
        }else {
            return "手机";
        }
    }

    /**
     * 获取版本号名称
     *
     * @param context 上下文
     * @return
     */
    public static String getVerName(Context context) {
        String verName = "";
        try {
            verName = context.getPackageManager().
                    getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return verName;
    }


    /**
     * 获取系统属性
     *
     * @param key
     * @param defaultValue
     * @return
     */
    public static String getProperty(String key, String defaultValue) {
        String value = defaultValue;
        try {
            Class<?> c = Class.forName("android.os.SystemProperties");
            Method get = c.getMethod("get", String.class, String.class);
            value = (String) (get.invoke(c, key, "unknown"));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return value;
        }
    }

    /**
     * 设置系统属性
     *
     * @param key
     * @param value
     */
    public static void setProperty(String key, String value) {
        try {
            Class<?> c = Class.forName("android.os.SystemProperties");
            Method set = c.getMethod("set", String.class, String.class);
            set.invoke(c, key, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 判断是否是cts模式
     *
     * @return
     */
    public static boolean isCtsEnable() {
        return getProperty("ro.cts.enable", "false").equals("true");
    }

    /**
     * install Apk
     *
     * @param context
     * @param filePath
     */
    public static void installApp(Context context, String filePath) {

        File _file = new File(filePath);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.fromFile(_file), "application/vnd.android.package-archive");
        context.startActivity(intent);
    }

    public static boolean getUninatllApkInfo(Context context, String filePath) {
        boolean result = false;
        try {
            PackageManager pm = context.getPackageManager();
            Log.e("archiveFilePath", filePath);
            PackageInfo info = pm.getPackageArchiveInfo(filePath, PackageManager.GET_ACTIVITIES);
            if (info != null) {
                //完整
                result = true;
            }
        } catch (Exception e) {
            //不完整
            result = false;
        }
        return result;
    }

}