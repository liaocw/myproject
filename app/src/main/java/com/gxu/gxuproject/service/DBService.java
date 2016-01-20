package com.gxu.gxuproject.service;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.gxu.gxuproject.beans.SingleQuestion;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by lcw on 2015/7/26 0026.
 */
public class DBService {

    private SQLiteDatabase db;

    public DBService() {
        db = SQLiteDatabase.openDatabase("/data/data/com.gxu.gxuproject/database/question.db", null, SQLiteDatabase.OPEN_READWRITE);
    }

    public List<SingleQuestion> getQuestions() {
        List<SingleQuestion> list = new ArrayList<SingleQuestion>();
        Cursor cursor = db.rawQuery("select * from question", null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            int count = cursor.getCount();
            for (int i = 0; i < count; i++) {
                cursor.moveToPosition(i);
                SingleQuestion singleQuestion = new SingleQuestion();
                singleQuestion.setQuestion(cursor.getString(cursor.getColumnIndex("question")));
                singleQuestion.setAnswerA(cursor.getString(cursor.getColumnIndex("answerA")));
                singleQuestion.setAnswerB(cursor.getString(cursor.getColumnIndex("answerB")));
                singleQuestion.setAnswerC(cursor.getString(cursor.getColumnIndex("answerC")));
                singleQuestion.setAnswerD(cursor.getString(cursor.getColumnIndex("answerD")));
                singleQuestion.setExplaination(cursor.getString(cursor.getColumnIndex("explaination")));
                singleQuestion.setID(cursor.getInt(cursor.getColumnIndex("ID")));
                //singleQuestion.setAnswer(cursor.getInt(cursor.getColumnIndex("answer")));
                singleQuestion.setSelectedAnswer(-1);
                list.add(singleQuestion);
            }
        }
        return list;
    }

}
