package com.lauzy.freedom.data.exception;

/**
 * Desc : 错误信息
 * gson 基类提取，封装后抛多种异常 后在 presentation 中处理，
 * 防止 自定义DisposableObserver造成的多状态多场景无法处理
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
