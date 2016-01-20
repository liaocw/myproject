package com.gxu.gxuproject.home;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.gxu.gxuproject.R;
import com.gxu.gxuproject.beans.SingleQuestion;
import com.gxu.gxuproject.net.RestClient;
import com.gxu.gxuproject.utils.ExamApplication;
import com.gxu.gxuproject.utils.SharedPreferencesUtils;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SingleActivity extends Activity {
    private ProgressDialog prDlg;
    private List<SingleQuestion> singleQuestionList;
    private SingleQuestion singleQuestion;
    private TextView tv_question, tv_title;
    private ImageView iv_back;
    private RadioButton[] radioButtons = new RadioButton[4];
    private Button btn_privious, btn_next;
    private RadioGroup radioGroup;
    private int count, current;
    private Chronometer chronometer_time;
    private SharedPreferencesUtils sp;
    private int single_code, multy_code;
    private int test_id;
    private String time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_single);
        ExamApplication.getInstance().addActivity(new SingleActivity());
        sp = new SharedPreferencesUtils(SingleActivity.this);
        initView();
        Intent intent = getIntent();
        time = intent.getStringExtra("time");
        String single = intent.getStringExtra("single_code");
        single_code = Integer.parseInt(single);
        String multy = intent.getStringExtra("multy_code");
        multy_code = Integer.parseInt(multy);
        Log.i("tag", "Single:single_code=" + single_code + ",multy_code=" + multy_code);
        String testid = intent.getStringExtra("test_id");
        test_id = Integer.parseInt(testid);

        new AlertDialog.Builder(SingleActivity.this)
                .setTitle("注意事项")
                .setMessage("1、考试时间为" + time + "分钟，请注意时间进行答题\n2、多选题多选或答错一项均不给分\n3、超时未提交，系统将自动存盘")
                .setCancelable(false)
                .setPositiveButton("开始答题", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        initData();
                    }
                })
                .show();
        initEvent();
    }

    private void initEvent() {
        //下一题
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //判断是否选择了答案
                SingleQuestion ques = singleQuestionList.get(current);
                if (ques.getSelectedAnswer() == -1) {
                    Toast.makeText(SingleActivity.this, "你尚未选择答案", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (current < count - 1) {
                    current++;
                    SingleQuestion q = singleQuestionList.get(current);
                    //更新题目
                    tv_question.setText((current + 1) + "." + q.getQuestion());
                    radioButtons[0].setText("A:" + q.getAnswerA());
                    radioButtons[1].setText("B:" + q.getAnswerB());
                    radioButtons[2].setText("C:" + q.getAnswerC());
                    radioButtons[3].setText("D:" + q.getAnswerD());
                    radioGroup.clearCheck();
                    if (q.getSelectedAnswer() != -1) {
                        radioButtons[q.getSelectedAnswer()].setChecked(true);
                    }
                } else {
                    //做完所有单选题的情况下，检查答案，算出分数
                    new AlertDialog.Builder(SingleActivity.this)
                            .setTitle("提示")
                            .setMessage("即将进入多选题")
                            .setCancelable(false)
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    //单选题得分
                                    int singleGrade = getSingleGrade();
                                    String grade = Integer.toString(singleGrade);
                                    long base = chronometer_time.getBase();
                                    String currentTime = Long.toString(base);
                                    Intent intent = new Intent(SingleActivity.this, MultipleActivity.class);
                                    intent.putExtra("currentTime", currentTime);
                                    intent.putExtra("grade", grade);
                                    String multy = multy_code + "";
                                    intent.putExtra("multy_code", multy);
                                    String id = test_id + "";
                                    intent.putExtra("test_id", id);
                                    startActivity(intent);
                                    finish();
                                }
                            })
                            .show();
                }
            }
        });

        //上一题
        btn_privious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (current > 0) {
                    current--;
                    //更新题目
                    SingleQuestion q = singleQuestionList.get(current);
                    tv_question.setText((current + 1) + "." + q.getQuestion());
                    radioButtons[0].setText("A:" + q.getAnswerA());
                    radioButtons[1].setText("B:" + q.getAnswerB());
                    radioButtons[2].setText("C:" + q.getAnswerC());
                    radioButtons[3].setText("D:" + q.getAnswerD());
                    radioGroup.clearCheck();
                    if (q.getSelectedAnswer() != -1) {
                        radioButtons[q.getSelectedAnswer()].setChecked(true);
                    }
                } else {
                    Toast.makeText(SingleActivity.this, "已经是第一题", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //对radioGroup的监听事件
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                for (int j = 0; j < 4; j++) {
                    if (radioButtons[j].isChecked()) {
                        //数字0123与ABCD对应的转换
                        String str = j + "";
                        char temp = str.charAt(0);
                        int asc = temp + 17;
                        char ch = (char) asc;
                        String answer = String.valueOf(ch);
                        singleQuestionList.get(current).setSelectedAnswer(j);
                        singleQuestionList.get(current).setMyAnswer(answer);
                        break;
                    }
                }
            }
        });

        //定时器的监听事件
        chronometer_time.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {

                if (chronometer_time.getText().toString().equals("50:00")) {
                    //提示剩余时间
                    new AlertDialog.Builder(SingleActivity.this)
                            .setTitle("提示")
                            .setMessage("剩余时间：10分钟")
                            .setCancelable(false)
                            .setPositiveButton("确定", null)
                            .show();
                } else if (chronometer_time.getText().toString().equals("60:00")) {
                    //时间到，系统自动为用户提交,存档
                    new AlertDialog.Builder(SingleActivity.this)
                            .setTitle("提示")
                            .setMessage("时间到，系统自动提交答案")
                            .setCancelable(false)
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    //单选题得分
                                    int singleGrade = getSingleGrade();
                                    //提交
                                    submitGrade(singleGrade, 0);
                                    //finish();
                                }
                            })
                            .show();
                    chronometer_time.stop();
                }

            }
        });

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBackDialog();
            }
        });
    }

    /**
     * 获取所选的答案
     */
    private int getSingleGrade() {
        List<String> list = new ArrayList<>();
        int count = 0;
        for (int i = 0; i < singleQuestionList.size(); i++) {
            String myanswer = singleQuestionList.get(i).getMyAnswer();
            if (!TextUtils.isEmpty(myanswer)) {
                list.add(myanswer);
            }
            String trueanswer = singleQuestionList.get(i).getAnswer();
            Log.i("tag", "myanswer:" + myanswer + "--" + "trueanswer:" + trueanswer);
            if (!TextUtils.isEmpty(myanswer) && myanswer.equals(trueanswer)) {
                count++;
            }
        }
        //返回得分
        return count * single_code;
    }

    /**
     * 提交分数到服务器
     */
    private void submitGrade(int grade_single, int grade_multiple) {
        prDlg = ProgressDialog.show(SingleActivity.this, "同步中", "正在提交数据，请稍候");
        RequestParams params = new RequestParams();
        params.put("stu_id", sp.getUsername());
        params.put("single_grade", grade_single);
        params.put("multiple_grade", grade_multiple);
        params.put("test_id", test_id);
        String testtime = chronometer_time.getText().toString();
        String[] times = testtime.split(":");
        String time = times[0];
        params.put("time", time);
        RestClient.post("submitGrade", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                prDlg.dismiss();
                try {
                    if ((response.getInt("status")) == 1) {
                        Toast.makeText(SingleActivity.this, "提交成绩成功", Toast.LENGTH_SHORT).show();
                        chronometer_time.stop();
                        finish();
                    } else {
                        new AlertDialog.Builder(SingleActivity.this)
                                .setTitle("提示")
                                .setMessage("你已经提交过答案了")
                                .setCancelable(false)
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Intent intent = new Intent(SingleActivity.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                })
                                .show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                prDlg.dismiss();
                Toast.makeText(SingleActivity.this, "保存失败，请检查网络状况", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                prDlg.dismiss();
                Toast.makeText(SingleActivity.this, "保存失败，请检查网络状况", Toast.LENGTH_SHORT).show();
            }
        });



    }

    private void initView() {
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("单选题");
        chronometer_time = (Chronometer) findViewById(R.id.chronometer_time);
        tv_question = (TextView) findViewById(R.id.tv_question);
        radioButtons[0] = (RadioButton) findViewById(R.id.answerA);
        radioButtons[1] = (RadioButton) findViewById(R.id.answerB);
        radioButtons[2] = (RadioButton) findViewById(R.id.answerC);
        radioButtons[3] = (RadioButton) findViewById(R.id.answerD);
        btn_privious = (Button) findViewById(R.id.btn_previous);
        btn_next = (Button) findViewById(R.id.btn_next);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setVisibility(View.VISIBLE);
    }

    private void initData() {
        prDlg = ProgressDialog.show(SingleActivity.this, "同步中", "正在同步数据，请稍候");
        RequestParams params = new RequestParams();
        params.put("test_id", test_id);
        RestClient.post("getSingleQuestions", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                prDlg.dismiss();
                chronometer_time.setBase(SystemClock.elapsedRealtime());
                chronometer_time.start();
                int length = response.length();
                singleQuestionList = new ArrayList<SingleQuestion>();

                for (int i = 0; i < length; i++) {
                    try {

                        singleQuestion = new SingleQuestion();
                        JSONObject obj = response.getJSONObject(i);
                        String qt = obj.optString("question");
                        String answerA = obj.optString("option1");
                        String answerB = obj.optString("option2");
                        String answerC = obj.optString("option3");
                        String answerD = obj.optString("option4");
                        String answer2 = obj.optString("answer");
                        String answer = answer2.replace(",", "");
                        Log.i("tag-", "answer2:" + answer2 + ",answer:" + answer);


                        singleQuestion.setQuestion(qt);
                        singleQuestion.setAnswerA(answerA);
                        singleQuestion.setAnswerB(answerB);
                        singleQuestion.setAnswerC(answerC);
                        singleQuestion.setAnswerD(answerD);
                        singleQuestion.setAnswer(answer);
                        singleQuestion.setSelectedAnswer(-1);
                        singleQuestionList.add(singleQuestion);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                //给第一题赋值
                current = 0;
                count = singleQuestionList.size();
                SingleQuestion q = singleQuestionList.get(0);
                tv_question.setText((current + 1) + "." + q.getQuestion());
                radioButtons[0].setText("A:" + q.getAnswerA());
                radioButtons[1].setText("B:" + q.getAnswerB());
                radioButtons[2].setText("C:" + q.getAnswerC());
                radioButtons[3].setText("D:" + q.getAnswerD());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
             //   prDlg.dismiss();
                Toast.makeText(SingleActivity.this, "同步失败，请检查网络状况", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                prDlg.dismiss();
                Toast.makeText(SingleActivity.this, "同步失败，请检查网络状况", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        showBackDialog();
    }

    private void showBackDialog() {
        new AlertDialog.Builder(SingleActivity.this)
                .setTitle("警告")
                .setMessage("是否放弃答题？")
                .setCancelable(false)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                })
                .setNegativeButton("取消", null)
                .show();
    }

}

