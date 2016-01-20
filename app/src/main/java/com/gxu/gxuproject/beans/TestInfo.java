package com.gxu.gxuproject.beans;

/**
 * Created by lcw on 2015/9/6 0006.
 */
public class TestInfo {
    private int test_id;

    public int getTest_id() {
        return test_id;
    }

    public void setTest_id(int test_id) {
        this.test_id = test_id;
    }

    private String title;
    private int time;
    private int single_code;
    private int multy_code;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getSingle_code() {
        return single_code;
    }

    public void setSingle_code(int single_code) {
        this.single_code = single_code;
    }

    public int getMulty_code() {
        return multy_code;
    }

    public void setMulty_code(int multy_code) {
        this.multy_code = multy_code;
    }
}
