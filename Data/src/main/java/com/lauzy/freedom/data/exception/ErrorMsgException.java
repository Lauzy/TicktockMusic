package com.lauzy.freedom.data.exception;

/**
 * Desc : 错误信息
 * Author : Lauzy
 * Date : 2017/7/7
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class ErrorMsgException extends RuntimeException {

    private int mErrCode;

    public ErrorMsgException(int errCode, String message) {
        super(message);
        mErrCode = errCode;
    }

    public int getErrCode() {
        return mErrCode;
    }

    public void setErrCode(int errCode) {
        mErrCode = errCode;
    }
}
