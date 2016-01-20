package com.gxu.gxuproject.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by lcw on 2015/9/1 0001.
 */
public class SharedPreferencesUtils {

    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    public SharedPreferencesUtils(Context context) {
        sp = context.getSharedPreferences("Exam", Context.MODE_PRIVATE);
        editor = sp.edit();
    }

    public String getUsername() {
        String username = sp.getString("username", "");
        return username;
    }

    public void setUsername(String username) {
        editor.putString("username", username);
        editor.commit();
    }

    public String getPassword(){
        String password = sp.getString("password","");
        return password;
    }

    public void setPassword(String password){
        editor.putString("password",password);
        editor.commit();
    }


    public int getSingleGrade() {
        int single_grade = sp.getInt("single_grade", 0);
        return single_grade;
    }

    public void setSingleGrade(int single_grade) {
        editor.putInt("single_grade", single_grade);
        editor.commit();
    }

    public int getMultipleGrade() {
        int multiple_grade = sp.getInt("multiple_grade",0);
        return multiple_grade;
    }

    public void setMutlipleGrade(int multiple_grade) {
        editor.putInt("multiple_grade", multiple_grade);
        editor.commit();
    }

    public boolean getLogin(){
        boolean login = sp.getBoolean("login",false);
        return  login;
    }
    public void setLogin(boolean login){
        editor.putBoolean("login",login);
        editor.commit();
    }






}
