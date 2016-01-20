package com.gxu.gxuproject.home;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gxu.gxuproject.R;
import com.gxu.gxuproject.beans.MultipleQuestion;
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


public class MultipleActivity extends Activity {
    private ProgressDialog prDlg;
    private List<MultipleQuestion> questionList;
    private MultipleQuestion multipleQuestion;
    private TextView tv_question, tv_title;
    private Button btn_privious, btn_next, btn_home;
    private LinearLayout layout_onebutton, layout_twobutton;

    private CheckBox[] checkBoxs = new CheckBox[5];

    private int count, current;
    private int singleGrade, multipleGrade;

    private Chronometer chronometer_time;
    private ImageView iv_back;

    private SharedPreferencesUtils sp;

    private List<String> myAnswerList = new ArrayList<>();

    private boolean flag = false;

    private int multy_code;
    private int test_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_multiple);
        ExamApplication.getInstance().addActivity(new MultipleActivity());
        sp = new SharedPreferencesUtils(MultipleActivity.this);
        initView();
        initData();
        initEvent();
    }

    private void initEvent() {
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //设置上一题的答案
                setMyAnswer();
                //判断是否选择了答案
                MultipleQuestion ques = questionList.get(current);
                if (TextUtils.isEmpty(ques.getSelecteAnswer())) {
                    Toast.makeText(MultipleActivity.this, "你尚未选择答案", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (current < count - 1) {
                    current++;
                    MultipleQuestion q = questionList.get(current);
                    //更新题目
                    tv_question.setText((current + 1) + "." + q.getQuestion());
                    checkBoxs[0].setText("A:" + q.getAnswerA());
                    checkBoxs[1].setText("B:" + q.getAnswerB());
                    checkBoxs[2].setText("C:" + q.getAnswerC());
                    checkBoxs[3].setText("D:" + q.getAnswerD());
                    checkBoxs[4].setText("E:" + q.getAnswerE());

                    for (int i = 0; i < 5; i++) {
                        checkBoxs[i].setChecked(false);
                    }

                    String str = q.getSelecteAnswer();

                    if (!("".equals(str))) {
                        String selectId = q.getSelecteAnswer();
                        char[] chars = selectId.toCharArray();
                        int length = chars.length;
                        for (int j = 0; j < length; j++) {
                            int id = chars[j] - 48;
                            checkBoxs[id].setChecked(true);
                        }
                    }

                } else {
                    new AlertDialog.Builder(MultipleActivity.this)
                            .setTitle("提示")
                            .setMessage("答题完毕，是否提交全部答案？")
                            .setCancelable(false)
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    //提交分数
                                    multipleGrade = getMultipleGrade();
                                    submitGrade(singleGrade, multipleGrade);
                                    flag = true;
                                    Log.i("tag", "Multiple_AnswerList:" + myAnswerList.toString());
                                }
                            })
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    //finish();
                                    myAnswerList.remove(current);
                                }
                            })
                            .show();
                }
            }
        });


        btn_privious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (current > 0) {
                    current--;
                    myAnswerList.remove(current);
                    //更新题目
                    MultipleQuestion q = questionList.get(current);
                    tv_question.setText((current + 1) + "." + q.getQuestion());
                    checkBoxs[0].setText("A:" + q.getAnswerA());
                    checkBoxs[1].setText("B:" + q.getAnswerB());
                    checkBoxs[2].setText("C:" + q.getAnswerC());
                    checkBoxs[3].setText("D:" + q.getAnswerD());
                    checkBoxs[4].setText("E:" + q.getAnswerE());

                    for (int i = 0; i < 5; i++) {
                        checkBoxs[i].setChecked(false);
                    }
                    if (!TextUtils.isEmpty(q.getSelecteAnswer())) {
                        String selectId = q.getSelecteAnswer();
                        char[] chars = selectId.toCharArray();
                        for (int j = 0; j < chars.length; j++) {
                            int id = chars[j] - 48;
                            checkBoxs[id].setChecked(true);
                        }
                    }
                } else {
                    Toast.makeText(MultipleActivity.this, "已经是第一题", Toast.LENGTH_SHORT).show();
                }
            }
        });


        //定时器的监听事件
        chronometer_time.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                if (chronometer_time.getText().toString().equals("50:00")) {
                    new AlertDialog.Builder(MultipleActivity.this)
                            .setTitle("提示")
                            .setMessage("剩余时间：10分钟")
                            .setCancelable(false)
                            .setPositiveButton("确定", null)
                            .show();
                } else if (chronometer_time.getText().toString().equals("60:00")) {
                    new AlertDialog.Builder(MultipleActivity.this)
                            .setTitle("提示")
                            .setMessage("时间到，系统自动提交答案")
                            .setCancelable(false)
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    //提交得分
                                    multipleGrade = getMultipleGrade();
                                    submitGrade(singleGrade, multipleGrade);
                                }
                            })
                            .show();
                    chronometer_time.stop();
                }

            }
        });

        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MultipleActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
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
    private int getMultipleGrade() {
        List<String> list = new ArrayList<>();
        int count = 0;
        for (int i = 0; i < questionList.size(); i++) {
            String myanswer = questionList.get(i).getMyAnswer();
            if (!TextUtils.isEmpty(myanswer)) {
                list.add(myanswer);
            }
            String trueanswer = questionList.get(i).getAnswer();
            Log.i("tag", "myanswer:" + myanswer + "--" + "trueanswer:" + trueanswer);
            if (!TextUtils.isEmpty(myanswer) && myanswer.equals(trueanswer)) {
                count++;
            }
        }
        //返回得分
        return count * multy_code;
    }

    private void setMyAnswer() {
        String myAnswer = "";
        String selectAnswer = "";
        for (int i = 0; i < checkBoxs.length; i++) {
            if (checkBoxs[i].isChecked()) {
                String str = i + "";
                char temp = str.charAt(0);
                int asc = temp + 17;
                char ch = (char) asc;
                String answer = String.valueOf(ch);
                myAnswer += answer;
                selectAnswer += i;
            }
        }
        MultipleQuestion question = questionList.get(current);
        question.setSelecteAnswer(selectAnswer);
        question.setMyAnswer(myAnswer);
        myAnswerList.add(myAnswer);
    }

    /**
     * 提交分数到服务器
     */
    private void submitGrade(int grade_single, int grade_multiple) {
        prDlg = ProgressDialog.show(MultipleActivity.this, "同步中", "正在提交数据，请稍候");
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
                        Toast.makeText(MultipleActivity.this, "提交成绩成功", Toast.LENGTH_SHORT).show();
                        chronometer_time.stop();
                        layout_twobutton.setVisibility(View.GONE);
                        layout_onebutton.setVisibility(View.VISIBLE);
                    } else {
                        new AlertDialog.Builder(MultipleActivity.this)
                                .setTitle("提示")
                                .setMessage("你已经提交过答案了")
                                .setCancelable(false)
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Intent intent = new Intent(MultipleActivity.this, MainActivity.class);
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
                Toast.makeText(MultipleActivity.this, "保存失败，请检查网络状况", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                prDlg.dismiss();
                Toast.makeText(MultipleActivity.this, "保存失败，请检查网络状况", Toast.LENGTH_SHORT).show();
            }
        });

    }

    /**
     * 验证答案
     *
     * @param list
     * @return
     */
    private List<Integer> checkAnswer(List<MultipleQuestion> list) {
        List<Integer> wrongList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (!list.get(i).getMyAnswer().equals(list.get(i).getAnswer())) {
                wrongList.add(i);
            }
        }
        return wrongList;
    }

    private void initView() {

        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("多选题");
        chronometer_time = (Chronometer) findViewById(R.id.chronometer_time);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setVisibility(View.VISIBLE);
        tv_question = (TextView) findViewById(R.id.tv_question);
        checkBoxs[0] = (CheckBox) findViewById(R.id.answerA);
        checkBoxs[1] = (CheckBox) findViewById(R.id.answerB);
        checkBoxs[2] = (CheckBox) findViewById(R.id.answerC);
        checkBoxs[3] = (CheckBox) findViewById(R.id.answerD);
        checkBoxs[4] = (CheckBox) findViewById(R.id.answerE);
        btn_privious = (Button) findViewById(R.id.btn_previous);
        btn_next = (Button) findViewById(R.id.btn_next);
        layout_onebutton = (LinearLayout) findViewById(R.id.layout_onebutton);
        layout_twobutton = (LinearLayout) findViewById(R.id.layout_twobutton);
        btn_home = (Button) findViewById(R.id.btn_home);
    }

    private void initData() {
        Intent intent = getIntent();
        String currentTime = intent.getStringExtra("currentTime");
        String grade = intent.getStringExtra("grade");
        singleGrade = Integer.parseInt(grade);
        String multy = intent.getStringExtra("multy_code");
        multy_code = Integer.parseInt(multy);

        Log.i("tag", "Multy:multy_code=" + multy_code);

        String testid = intent.getStringExtra("test_id");
        test_id = Integer.parseInt(testid);

        final long base = Long.parseLong(currentTime);
        prDlg = ProgressDialog.show(MultipleActivity.this, "同步中", "正在同步数据，请稍候");
        RequestParams params = new RequestParams();
        params.put("test_id", test_id);
        RestClient.post("getMultipleQuestions", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                prDlg.dismiss();
                chronometer_time.setBase(base);
                chronometer_time.start();
                int length = response.length();
                questionList = new ArrayList<MultipleQuestion>();
                for (int i = 0; i < length; i++) {
                    try {
                        multipleQuestion = new MultipleQuestion();
                        JSONObject obj = response.getJSONObject(i);
                        String qt = obj.optString("question");
                        String answerA = obj.optString("option1");
                        String answerB = obj.optString("option2");
                        String answerC = obj.optString("option3");
                        String answerD = obj.optString("option4");
                        String answerE = obj.optString("option5");
                        String answer2 = obj.optString("answer");
                        String answer = answer2.replace(",", "");
                        Log.i("tag-", "answer2:" + answer2 + ";answer:" + answer);

                        multipleQuestion.setQuestion(qt);
                        multipleQuestion.setAnswerA(answerA);
                        multipleQuestion.setAnswerB(answerB);
                        multipleQuestion.setAnswerC(answerC);
                        multipleQuestion.setAnswerD(answerD);
                        multipleQuestion.setAnswerE(answerE);
                        multipleQuestion.setAnswer(answer);
                        multipleQuestion.setSelecteAnswer("");
                        questionList.add(multipleQuestion);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                //给第一题赋值
                current = 0;
                count = questionList.size();
                MultipleQuestion q = questionList.get(0);
                tv_question.setText((current + 1) + "." + q.getQuestion());
                checkBoxs[0].setText("A:" + q.getAnswerA());
                checkBoxs[1].setText("B:" + q.getAnswerB());
                checkBoxs[2].setText("C:" + q.getAnswerC());
                checkBoxs[3].setText("D:" + q.getAnswerD());
                checkBoxs[4].setText("E:" + q.getAnswerE());

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                prDlg.dismiss();
                Toast.makeText(MultipleActivity.this, "同步失败，请检查网络状况", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                prDlg.dismiss();
                Toast.makeText(MultipleActivity.this, "同步失败，请检查网络状况", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (!flag) {
            showBackDialog();
        }
    }

    private void showBackDialog() {
        new AlertDialog.Builder(MultipleActivity.this)
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

