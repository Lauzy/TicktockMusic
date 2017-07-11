package com.freedom.lauzy.model;

/**
 * Desc : StatusBean
 * Author : Lauzy
 * Date : 2017/7/7
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class StatusBean {
    public int error_code;
    public String error_msg;

    private static class ErrorCode {
        static final int CODE_SUCCESS = 22000;
    }

    public boolean isInvalidCode() {
        return error_code != ErrorCode.CODE_SUCCESS;
    }
}
