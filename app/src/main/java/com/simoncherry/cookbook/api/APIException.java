package com.simoncherry.cookbook.api;

/**
 * Created by Simon on 2017/3/27.
 */

public class APIException extends RuntimeException {

    private int code;
    //用于展示的异常信息
    private String displayMessage;

    public APIException(int code, String msg) {
        super();
        this.code = code;
        this.displayMessage = msg;
    }

    public void setDisplayMessage(String displayMessage) {
        this.displayMessage = displayMessage;
    }

    public String getDisplayMessage() {
        return displayMessage;
    }

    public int getCode() {
        return code;
    }
}
