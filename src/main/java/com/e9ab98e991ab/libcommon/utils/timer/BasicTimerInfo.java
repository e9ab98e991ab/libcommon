package com.e9ab98e991ab.libcommon.utils.timer;



final class BasicTimerInfo {
    /***
     * 初始计数值
     */
    int init;
    /***
     * 间隔计数，即基于基础定时的倍率
     */
    int count;
    /***
     * 是否为单次定时
     */
    boolean oneshot;
    /***
     * 定时回调
     */
    Object cb;
}

