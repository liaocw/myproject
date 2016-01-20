package com.gxu.gxuproject.home;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gxu.gxuproject.R;
import com.gxu.gxuproject.net.RestClient;
import com.gxu.gxuproject.utils.ExamApplication;
import com.gxu.gxuproject.utils.MD5Cipher;
import com.gxu.gxuproject.utils.SharedPreferencesUtils;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by lcw on 2015/8/15 0015.
 */
public class LoginActivity extends Activity {
    private EditText usrname, password;
    private Button login;

    private ProgressDialog prDlg;
    private SharedPreferencesUtils sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        ExamApplication.getInstance().addActivity(new LoginActivity());
        sp = new SharedPreferencesUtils(LoginActivity.this);
        initView();
        initEvent();
    }

    private void initEvent() {
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkLogin();
            }
        });
    }

    /**
     * 登录验证
     */
    private void checkLogin() {
        final String name = usrname.getText().toString().trim();
        final String pwd = password.getText().toString().trim();
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(pwd)) {
            Toast.makeText(this, "学号或密码不能为空", Toast.LENGTH_SHORT).show();
        } else {
            RequestParams params = new RequestParams();
            params.put("stu_id", name);
            params.put("password", pwd);
            Log.i("tag", "MD5:" + MD5Cipher.md5(pwd));
            prDlg = ProgressDialog.show(LoginActivity.this, "登录中...", "正在登录，请稍候");
            RestClient.post("login", params, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    prDlg.dismiss();
                    try {
                        if (response.getInt("status") == 1) {
                            sp.setUsername(name);
                            sp.setPassword(pwd);
                            JSONObject obj = response.getJSONObject("message");
                            Log.i("tag", sp.getUsername());
                            sp.setSingleGrade(obj.optInt("single_grade"));
                            sp.setMutlipleGrade(obj.optInt("multiple_grade"));

                            //设置为已登陆状态
                            sp.setLogin(true);

                            Toast toast = Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT);
                            toast.show();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast toast = Toast.makeText(LoginActivity.this, "登录失败，请检查账号和密码", Toast.LENGTH_LONG);
                            toast.show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    prDlg.dismiss();
                    Toast toast = Toast.makeText(LoginActivity.this, "网络连接失败", Toast.LENGTH_LONG);
                    toast.show();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    prDlg.dismiss();
                    Toast toast = Toast.makeText(LoginActivity.this, "网络连接失败", Toast.LENGTH_LONG);
                    toast.show();
                }
            });
        }
    }

    private void initView() {
        usrname = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);
        usrname.setText(sp.getUsername());
        password.setText(sp.getPassword());
    }
}
