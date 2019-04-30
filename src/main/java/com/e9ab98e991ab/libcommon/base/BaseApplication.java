package com.e9ab98e991ab.libcommon.base;


import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.apkfuns.logutils.LogLevel;
import com.apkfuns.logutils.LogUtils;
import com.e9ab98e991ab.libcommon.utils.toast.ToastyUtils;

import java.util.Locale;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import me.jessyan.autosize.AutoSize;
import me.jessyan.autosize.AutoSizeConfig;
import me.jessyan.autosize.onAdaptListener;


/**
 * @author gaoxin 18-10-31 下午3:52
 * @version V1.0.0
 * @name BaseApplication
 */
public class BaseApplication extends Application implements HasActivityInjector {
    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

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
        /***
         * Toast
         */
        ToastyUtils.getConfig().setShouldTint(false).init(getApplicationContext());
        /**
         * 以下是 AndroidAutoSize 可以自定义的参数, {@link AutoSizeConfig} 的每个方法的注释都写的很详细
         * 使用前请一定记得跳进源码，查看方法的注释, 下面的注释只是简单描述!!!
         */
        AutoSizeConfig.getInstance()

                //是否让框架支持自定义 Fragment 的适配参数, 由于这个需求是比较少见的, 所以须要使用者手动开启
                //如果没有这个需求建议不开启
                .setCustomFragment(true)

                //屏幕适配监听器
                .setOnAdaptListener(new onAdaptListener() {
                    @Override
                    public void onAdaptBefore(Object target, Activity activity) {
                        LogUtils.d(String.format(Locale.ENGLISH, "%s onAdaptBefore!", target.getClass().getName()));
                    }

                    @Override
                    public void onAdaptAfter(Object target, Activity activity) {
                        LogUtils.d(String.format(Locale.ENGLISH, "%s onAdaptAfter!", target.getClass().getName()));
                    }
                })

        //是否打印 AutoSize 的内部日志, 默认为 true, 如果您不想 AutoSize 打印日志, 则请设置为 false
//                .setLog(false)

        //是否使用设备的实际尺寸做适配, 默认为 false, 如果设置为 false, 在以屏幕高度为基准进行适配时
        //AutoSize 会将屏幕总高度减去状态栏高度来做适配, 如果设备上有导航栏还会减去导航栏的高度
        //设置为 true 则使用设备的实际屏幕高度, 不会减去状态栏以及导航栏高度
//                .setUseDeviceSize(true)

        //是否全局按照宽度进行等比例适配, 默认为 true, 如果设置为 false, AutoSize 会全局按照高度进行适配
//                .setBaseOnWidth(false)

        //设置屏幕适配逻辑策略类, 一般不用设置, 使用框架默认的就好
//                .setAutoAdaptStrategy(new AutoAdaptStrategy())
        ;
        //当 App 中出现多进程, 并且您需要适配所有的进程, 就需要在 App 初始化时调用 initCompatMultiProcess()
        //在 Demo 中跳转的三方库中的 DefaultErrorActivity 就是在另外一个进程中, 所以要想适配这个 Activity 就需要调用 initCompatMultiProcess()
        AutoSize.initCompatMultiProcess(this);

        LogUtils.getLogConfig()
                .configAllowLog(true)
                .configTagPrefix("AM")
                .configFormatTag("%d{HH:mm:ss:SSS} %t %c{-5}")
                .configShowBorders(true)
                //.configMethodOffset(1)
                //.addParserClass(OkHttpResponseParse.class)
                .configLevel(LogLevel.TYPE_VERBOSE);



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

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }
}
