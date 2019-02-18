package com.e9ab98e991ab.libcommon.utils;

import java.util.Stack;


public class ActivityManager<T> {

    private static ActivityManager acitivityManager = new ActivityManager();
    /**
     * 堆
     */
    public Stack<T> activities = new Stack<>();

    public static ActivityManager getInstance() {
        return acitivityManager;
    }

    public Stack<T> getActivities() {
        return activities;
    }

    public void addActivity(T activity) {
        if (activity == null) {
            return;
        }
        activities.add(activity);
    }

    public void removeActivity(T activity) {
        if (activity == null) {
            return;
        }
        activities.remove(activity);
    }
}
