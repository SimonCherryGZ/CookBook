package com.simoncherry.cookbook.model;

/**
 * Created by Simon on 2017/3/27.
 */

public class MobAPIResult<T> {

    private int retCode;
    private String msg;
    private T result;

    public int getRetCode() {
        return retCode;
    }

    public void setRetCode(int retCode) {
        this.retCode = retCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "MobAPIResult{" +
                "retCode=" + retCode +
                ", msg='" + msg + '\'' +
                ", result=" + result +
                '}';
    }
}
