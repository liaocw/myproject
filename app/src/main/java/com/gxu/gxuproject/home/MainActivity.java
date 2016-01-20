package com.gxu.gxuproject.home;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.InputType;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.gxu.gxuproject.R;
import com.gxu.gxuproject.beans.TestInfo;
import com.gxu.gxuproject.net.RestClient;
import com.gxu.gxuproject.utils.ExamApplication;
import com.gxu.gxuproject.utils.SharedPreferencesUtils;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by lcw on 2015/8/23 0023.
 */
public class MainActivity extends Activity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    private static long firstTime;
    // 页卡数量
    private static final int PAGER_COUNT = 4;
    private int image_id[] = {R.drawable.gxu01, R.drawable.gxu02, R.drawable.gxu03,
            R.drawable.gxu04};
    private boolean isLoop = true;
    private LinearLayout mLayoutPoints;
    // viewpager播放间隔
    private static final long PAGER_PLAY_TIME = 2000;
    private int previousSelectPosition = 0;
    private ViewPager viewPager;
    private List<View> viewList;

    private TextView tv_1, tv_2, tv_3, tv_4, tv_5, tv_6, tv_right,tv_title;

    private PopupWindow mPopupWindow;

    private ProgressDialog prDlg;
    private List<TestInfo> testInfoList;
    private TestInfo testInfo;


    protected ProgressDialog mProgressDialog;
    protected DownloadManager manager;
    protected DownloadCompleteReceiver receiver;
    private static String version;
    private static String apkDownUrl;
    private static String packageName;
    private static String path;
    protected long downloadId;

    private SharedPreferencesUtils sp;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        ExamApplication.getInstance().addActivity(new MainActivity());
        sp = new SharedPreferencesUtils(MainActivity.this);
        //版本更新
        updateVersion();

        initView();
        initData();
        initEvent();


    }

    /**
     * 检查更新
     */
    private void updateVersion() {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);// 设置水平进度条
        mProgressDialog.setCancelable(true);// 设置是否可以通过点击Back键取消
        mProgressDialog.setCanceledOnTouchOutside(false);// 设置在点击Dialog外是否取消Dialog进度条
        mProgressDialog.setIcon(R.drawable.bywf);// 设置提示的title的图标，默认是没有的
        mProgressDialog.setTitle("下载进度");
        mProgressDialog.setMax(100);

        manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        receiver = new DownloadCompleteReceiver();
        this.getApplicationContext().registerReceiver(receiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        RestClient.get("http://zsjs.oss-cn-shenzhen.aliyuncs.com/zsjs.json", null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                version = response.optString("version");
                Log.i("tag", "version:" + version);
                apkDownUrl = response.optString("apkDownUrl");
                String PackageName = getPackageName();
                PackageInfo info = null;
                try {
                    info = getPackageManager().getPackageInfo(PackageName, 0);
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
                int versionCode = info.versionCode;
                packageName = info.applicationInfo.packageName;
                if (versionCode < Integer.valueOf(version)) {
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("程序有新的版本")
                            .setMessage("是否现在更新？")
                            .setCancelable(false)
                            .setPositiveButton("确定",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.dismiss();
                                            downloadapk();
                                        }
                                    })
                            .setNegativeButton("取消",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int whichButton) {
                                            dialog.dismiss();
                                        }
                                    }).show();
                } else {
//                    Toast toast = Toast.makeText(MainActivity.this, "当前版本为" + versionName + ",已是最新版本，无需更新", Toast.LENGTH_LONG);
//                    toast.show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                //   prDlg.dismiss();
            }
        });
    }


    /**
     * 下载APK
     */
    private void downloadapk() {
        String sddir = Environment.getExternalStorageDirectory().getPath();
        path = sddir + "/" + packageName;
        File destDir = new File(sddir + "/" + packageName);
        if (!destDir.exists()) {
            destDir.mkdirs();
        }
        File file = new File(path);
        Uri dstUri = Uri.fromFile(file);
        DownloadManager.Request dwreq = new DownloadManager.Request(
                Uri.parse(apkDownUrl));
        dwreq.setDestinationUri(dstUri);
        downloadId = manager.enqueue(dwreq);
        mProgressDialog.show();
        Timer myTimer = new Timer();
        myTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                DownloadManager.Query q = new DownloadManager.Query();
                q.setFilterById(downloadId);
                Cursor cursor = manager.query(q);
                cursor.moveToFirst();
                int bytes_downloaded = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                int bytes_total = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
                cursor.close();
                final int dl_progress = (bytes_downloaded * 100 / bytes_total);
                if (dl_progress >= 100) this.cancel();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mProgressDialog.setProgress(dl_progress);
                    }
                });
            }
        }, 0, 10);
    }

    class DownloadCompleteReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
                mProgressDialog.dismiss();
                long downId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                DownloadManager.Query query = new DownloadManager.Query();
                query.setFilterById(downId);
                DownloadManager download = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
                Cursor cursor = download.query(query);
                try {
                    if (cursor.moveToFirst()) {
                        int fileNameIdx = cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_FILENAME);
                        String fileName = cursor.getString(fileNameIdx);
                        Intent install = new Intent(Intent.ACTION_VIEW);
                        install.setDataAndType(Uri.fromFile(new File(fileName)), "application/vnd.android.package-archive");
                        install.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(install);
                    }
                } finally {
                    cursor.close();
                }

            }
        }
    }


    private void initData() {
        prDlg = ProgressDialog.show(MainActivity.this, "同步中", "正在同步数据，请稍候");
        RestClient.post("getTestInfo", null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                prDlg.dismiss();
                int length = response.length();
                testInfoList = new ArrayList<TestInfo>();
                //暂时先取第一个
                for (int i = 0; i < 1; i++) {
                    try {
                        testInfo = new TestInfo();
                        JSONObject obj = response.getJSONObject(i);
                        int test_id = obj.optInt("test_id");
                        String title = obj.optString("test_name");
                        int time = obj.optInt("test_time");
                        int single_code = obj.optInt("single_code");
                        int multy_code = obj.optInt("multy_code");

                        testInfo.setTest_id(test_id);
                        testInfo.setTitle(title);
                        testInfo.setTime(time);
                        testInfo.setSingle_code(single_code);
                        testInfo.setMulty_code(multy_code);
                        testInfoList.add(testInfo);

                        tv_1.setText(title);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                prDlg.dismiss();
                Toast.makeText(MainActivity.this, "同步失败，请检查网络状况", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                prDlg.dismiss();
                Toast.makeText(MainActivity.this, "同步失败，请检查网络状况", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void initEvent() {
        tv_1.setOnClickListener(this);
        tv_2.setOnClickListener(this);
        tv_3.setOnClickListener(this);
        tv_4.setOnClickListener(this);
        tv_5.setOnClickListener(this);
        tv_6.setOnClickListener(this);
        tv_right.setOnClickListener(this);

        viewPager.setOnPageChangeListener(this);


    }

    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        mLayoutPoints = (LinearLayout) findViewById(R.id.ll_points);
        tv_1 = (TextView) findViewById(R.id.tv_1);
        tv_2 = (TextView) findViewById(R.id.tv_2);
        tv_3 = (TextView) findViewById(R.id.tv_3);
        tv_4 = (TextView) findViewById(R.id.tv_4);
        tv_5 = (TextView) findViewById(R.id.tv_5);
        tv_6 = (TextView) findViewById(R.id.tv_6);
        tv_right = (TextView) findViewById(R.id.tv_right);
        tv_right.setBackgroundResource(R.drawable.icon_menu);

        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("知识竞赛");



        viewList = new ArrayList<View>();
        LayoutInflater inflater = LayoutInflater.from(this);
        for (int i = 0; i < PAGER_COUNT; i++) {
            View v = inflater.inflate(R.layout.activity_main_viewpager, null);
            ImageView image = (ImageView) v.findViewById(R.id.img);
            image.setBackgroundResource(image_id[i]);
            viewList.add(v);
            //添加圆点指示器
            addPointView();
        }

        // 设置viewpager
        setViewPager();

    }


    /**
     * 添加点view对象
     */
    private void addPointView() {
        View view = new View(this);
        view.setBackgroundResource(R.drawable.point_bg);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(25, 25);
        lp.leftMargin = 10;
        view.setLayoutParams(lp);
        view.setEnabled(false);
        mLayoutPoints.addView(view);
    }

    /**
     * 设置viewpager的适配器,以实现循环滚动
     */
    private void setViewPager() {
        viewPager.setAdapter(new PagerAdapter() {
            /**
             * 该方法将返回所包含的 Item总个数。为了实现一种循环滚动的效果，返回了基本整型的最大值，这样就会创建很多的Item,
             */
            @Override
            public int getCount() {
                return Integer.MAX_VALUE;
            }

            /**
             * 判断出去的view是否等于进来的view 如果为true直接复用
             */
            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(viewList.get(position % viewList.size()), 0);
                return viewList.get(position % viewList.size());
            }

            /**
             * 销毁预加载以外的view对象, 会把需要销毁的对象的索引位置传进来，就是position，
             * 因为mImageViewList只有五条数据，而position将会取到很大的值，
             * 所以使用取余数的方法来获取每一条数据项。
             */
            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(viewList.get(position % viewList.size()));
            }
        });
        mLayoutPoints.getChildAt(previousSelectPosition).setEnabled(true);

        /**
         * 2147483647 / 2 = 1073741820 - 1
         * 设置ViewPager的当前项为一个比较大的数，以便一开始就可以左右循环滑动
         */

        int n = Integer.MAX_VALUE / 2 % viewList.size();
        int itemPosition = Integer.MAX_VALUE / 2 - n;
        viewPager.setCurrentItem(itemPosition);

        //循环播放
        loopPagerView();

    }

    /**
     * 循环播放viewpager
     */
    private void loopPagerView() {
        // 自动切换页面功能
        new Thread(new Runnable() {

            @Override
            public void run() {
                while (isLoop) {
                    // SystemClock.sleep(PAGER_PLAY_TIME);
                    try {
                        Thread.sleep(PAGER_PLAY_TIME);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    handler.sendEmptyMessage(0);
                }
            }
        }).start();
    }

    /**
     * handler 接收消息
     */
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);


        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_1:
                Intent intent = new Intent(MainActivity.this,SingleActivity.class);
                intent.putExtra("time",testInfo.getTime()+"");
                intent.putExtra("single_code",testInfo.getSingle_code()+"");
                intent.putExtra("multy_code",testInfo.getMulty_code()+"");
                Log.i("tag","Main:single_code="+testInfo.getSingle_code()+",multy_code="+testInfo.getMulty_code());
                intent.putExtra("test_id",testInfo.getTest_id()+"");
                startActivity(intent);
                //finish();
                break;
            case R.id.tv_2:
                Toast.makeText(MainActivity.this,"待开发",Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_3:
                Toast.makeText(MainActivity.this,"待开发",Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_4:
                Toast.makeText(MainActivity.this,"待开发",Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_5:
                Toast.makeText(MainActivity.this,"待开发",Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_6:
                Toast.makeText(MainActivity.this,"待开发",Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_right:
                showPupupWindow(view);
                break;
        }
    }


    /**
     * 显示popupWindow
     */
    private void showPupupWindow(View v) {

        //得到屏幕宽度
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.activity_pop, null);
         mPopupWindow = new PopupWindow(view, screenWidth / 3, WindowManager.LayoutParams.WRAP_CONTENT, true);
        //设置属性
        mPopupWindow.setTouchable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
        //设置动画
        // mPopupWindow.setAnimationStyle(R.style.FindDoctor_Pop);
        //设置透明度
        final WindowManager.LayoutParams params = getWindow().getAttributes();
        params.alpha = 0.8f;
        getWindow().setAttributes(params);
        //弹出位置，正下方
        mPopupWindow.showAsDropDown(v,-180,20);

        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                params.alpha = 1f;
                getWindow().setAttributes(params);
            }
        });

        //退出登陆
        LinearLayout layou_logout = (LinearLayout) view.findViewById(R.id.layout_logout);
        layou_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                SharedPreferencesUtils sp = new SharedPreferencesUtils(MainActivity.this);
                sp.setLogin(false);
            }
        });

        //退出登陆
        LinearLayout layou_mygrade = (LinearLayout) view.findViewById(R.id.layout_mygrade);
        layou_mygrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPopupWindow.dismiss();
                getMyGrade();


            }
        });

        //修改密码
        LinearLayout layou_updatepwd = (LinearLayout) view.findViewById(R.id.layout_updatepwd);
        layou_updatepwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPopupWindow.dismiss();
                showDialog_changePassword();


            }
        });


    }


    /**
     * 修改密码
     */
    private void showDialog_changePassword() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        final EditText newPassword = new EditText(this);
        final EditText repeat_newPassword = new EditText(this);
        newPassword.setHint("请输入新的密码");
        repeat_newPassword.setHint("确认密码");

        newPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        repeat_newPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        newPassword.setPadding(20, 30, 10, 30);
        repeat_newPassword.setPadding(20, 30, 10,30);

        linearLayout.addView(newPassword);
        linearLayout.addView(repeat_newPassword);

        alert.setView(linearLayout);
        alert.setTitle("修改密码");

        alert.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                final String newpassword = newPassword.getText().toString().trim();
                String repeatNewPassword = repeat_newPassword.getText().toString().trim();
                if (!TextUtils.isEmpty(newpassword)) {
                    if (repeatNewPassword.equals(newpassword)) {
                        //修改密码

                        //得到本地存储的登陆名和密码
                        String stu_id = sp.getUsername();

                        RequestParams params = new RequestParams();
                        params.put("stu_id", stu_id);
                        params.put("newPassword", newpassword);
                        prDlg = ProgressDialog.show(MainActivity.this, "同步中", "正在同步数据，请稍候");
                        RestClient.post("updatePassword", params, new JsonHttpResponseHandler() {
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                                prDlg.dismiss();
                                try {
                                    if (response.getInt("status") == 1) {
                                        Toast mToast = Toast.makeText(MainActivity.this, "密码修改成功，请重新登陆", Toast.LENGTH_LONG);
                                        mToast.show();
                                        sp.setLogin(false);
                                        sp.setPassword(newpassword);
                                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast mToast = Toast.makeText(MainActivity.this, "修改密码失败", Toast.LENGTH_LONG);
                                        mToast.show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                                prDlg.dismiss();
                                Toast mToast = Toast.makeText(MainActivity.this, "网络连接故障，修改密码", Toast.LENGTH_LONG);
                                mToast.show();
                            }
                        });

                    } else {
                        Toast.makeText(MainActivity.this, "请确认密码是否相同", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                }
            }

        });

        alert.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // what ever you want to do with No option.
            }
        });

        alert.show();
    }

    int totalGrade;

    public void getMyGrade() {
        RequestParams params = new RequestParams();
        String name = sp.getUsername();
        params.put("stu_id", name);
      //  params.put("password", pwd);
        prDlg = ProgressDialog.show(MainActivity.this, "同步中", "正在同步数据，请稍候");
        RestClient.post("getGrade", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                prDlg.dismiss();
                int length = response.length();
                for (int i = 0; i < length; i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);
                        int test_id = obj.optInt("test_id");
                        totalGrade = obj.optInt("code");
                        Log.d("MainActivity", "test_id:"+test_id);
                        Log.d("MainActivity", "code:" + totalGrade);

                        new AlertDialog.Builder(MainActivity.this)
                                .setTitle(testInfo.getTitle())
                                .setMessage("得分：" + totalGrade)
                                .setCancelable(false)
                                .setPositiveButton("确定",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.dismiss();

                                            }
                                        }).show();



                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                prDlg.dismiss();
                Toast.makeText(MainActivity.this, "同步失败，请检查网络状况", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                prDlg.dismiss();
                Toast.makeText(MainActivity.this, "同步失败，请检查网络状况", Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onPageSelected(int position) {
        // 切换选中的点,把前一个点置为normal状态
        mLayoutPoints.getChildAt(previousSelectPosition).setEnabled(false);
        // 把当前选中的position对应的点置为enabled状态
        mLayoutPoints.getChildAt(position % viewList.size()).setEnabled(true);
        previousSelectPosition = position % viewList.size();
    }


    @Override
    public void onBackPressed() {
        if (firstTime + 2000 > System.currentTimeMillis()) {
            ExamApplication.getInstance().exitAll();
            finish();
        } else {
            Toast.makeText(MainActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
        }
        firstTime = System.currentTimeMillis();
    }


}
