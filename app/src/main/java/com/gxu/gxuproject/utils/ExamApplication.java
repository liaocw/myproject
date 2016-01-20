package com.gxu.gxuproject.utils;

import android.app.Activity;
import android.app.Application;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lcw on 2015/8/15 0015.
 */
public class ExamApplication extends Application {
    private List<Activity> activityList = new ArrayList<Activity>();
    private static ExamApplication instance;

    private static final String TAG = "ExamApplication";


    private ExamApplication() {

    }

    // 单例模式，获取唯一的Activity实例 ,懒汉式,双重检查
    public static ExamApplication getInstance() {
        if (instance == null) {
            synchronized (ExamApplication.class) {
                if (instance == null) {
                    instance = new ExamApplication();
                }
            }
        }
        return instance;
    }

    // 添加Activity到容器中
    public void addActivity(Activity activity) {
        activityList.add(activity);
    }

    // 遍历所有activity，并退出
    public void exitAll() {
        for (Activity act : activityList) {
            act.finish();
            Log.i(TAG, act + " finish");
        }
        System.exit(0);
    }


}
