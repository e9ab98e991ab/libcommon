package com.e9ab98e991ab.libcommon.base;


import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.apkfuns.logutils.LogLevel;
import com.apkfuns.logutils.LogUtils;
import com.e9ab98e991ab.libcommon.BuildConfig;
import com.e9ab98e991ab.libcommon.utils.toast.ToastyUtils;

import me.jessyan.autosize.AutoSize;
import me.jessyan.autosize.AutoSizeConfig;
import me.jessyan.autosize.onAdaptListener;
import me.yokeyword.fragmentation.Fragmentation;
import skin.support.SkinCompatManager;
import skin.support.app.SkinAppCompatViewInflater;
import skin.support.app.SkinCardViewInflater;
import skin.support.constraint.app.SkinConstraintViewInflater;
import skin.support.design.app.SkinMaterialViewInflater;


/**
 * @author gaoxin 18-10-31 下午3:52
 * @version V1.0.0
 * @name BaseApplication
 */
public class BaseApplication extends Application  {


    private static BaseApplication sInstance;

    public static BaseApplication getIns() {
        return sInstance;
    }

    public static Context getContext() {
        return getContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;

        initAutoSize();
        initFragmentation();
        initToastyUtils();
        initLogUtils();

    }
    /***
     * Toast自定义布局框
     */
    private void initToastyUtils(){
        ToastyUtils.getConfig().setShouldTint(false).init(getApplicationContext());
    }
    private void initSkin(){
        SkinCompatManager.withoutActivity(this)
                // 基础控件换肤初始化
                .addInflater(new SkinAppCompatViewInflater())
                // material design 控件换肤初始化[可选]
                .addInflater(new SkinMaterialViewInflater())
                // ConstraintLayout 控件换肤初始化[可选]
                .addInflater(new SkinConstraintViewInflater())
                // CardView v7 控件换肤初始化[可选]
                .addInflater(new SkinCardViewInflater())
                // 关闭状态栏换肤，默认打开[可选]
                .setSkinStatusBarColorEnable(false)
                // 关闭windowBackground换肤，默认打开[可选]
                .setSkinWindowBackgroundEnable(false)
                .loadSkin();
    }

    /***
     * Fragment管理库
     */
    private void initFragmentation() {
        // Fragmentation is recommended to initialize in the Application
        Fragmentation.builder()
                // show stack view. Mode: BUBBLE, SHAKE, NONE
                .stackViewMode(Fragmentation.BUBBLE)
                .debug(BuildConfig.DEBUG)
                .install();
    }

    /***
     * 今日头条适配方案 注意：不适配px 不适配px 不适配px
     */
    private void initAutoSize(){
        AutoSizeConfig.getInstance()
                //设置为 true 则使用设备的实际屏幕高度, 不会减去状态栏以及导航栏高度
                .setUseDeviceSize(true)
                //是否全局按照宽度进行等比例适配, 默认为 true, 如果设置为 false, AutoSize 会全局按照高度进行适配
                .setBaseOnWidth(false)
                .setLog(true)
                .getUnitsManager()
                .setSupportDP(true)
                .setSupportSP(true)
        ;
        /*
         * 当 App 中出现多进程, 并且您需要适配所有的进程, 就需要在 App 初始化时调用 initCompatMultiProcess()
         * 在 Demo 中的三方库中的 DefaultErrorActivity 就是在另外一个进程中, 所以要想适配这个 Activity 就需要调用
         */
        AutoSize.initCompatMultiProcess(this);
    }

    /***
     * LogUtils
     */
    private void initLogUtils(){
        LogUtils.getLogConfig()
                .configAllowLog(true)
                .configTagPrefix("AM")
                .configFormatTag("%d{HH:mm:ss:SSS} %t %c{-5}")
                .configShowBorders(true)
                //.configMethodOffset(1)
                //.addParserClass(OkHttpResponseParse.class)
                .configLevel(LogLevel.TYPE_VERBOSE);
    }

    /***
     * 内存泄露检查工具
     */
    private void initLeakCanary() {
//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            // This process is dedicated to LeakCanary for heap analysis.
//            // You should not init your app in this process.
//            return;
//        }
//        LeakCanary.install(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }


    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }


}
