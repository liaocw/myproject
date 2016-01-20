package com.gxu.gxuproject.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

import com.gxu.gxuproject.R;
import com.gxu.gxuproject.utils.ExamApplication;
import com.gxu.gxuproject.utils.SharedPreferencesUtils;

/**
 * Created by lcw on 2015/8/15 0015.
 */
public class SplashActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);
        ExamApplication.getInstance().addActivity(new SplashActivity());
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferencesUtils sp = new SharedPreferencesUtils(SplashActivity.this);
                Intent intent;
                if (sp.getLogin()) {
                    intent = new Intent(SplashActivity.this, MainActivity.class);
                } else {
                    intent = new Intent(SplashActivity.this, LoginActivity.class);
                }
                startActivity(intent);
                finish();
            }
        }, 2000);
    }
}
