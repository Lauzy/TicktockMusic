package com.lauzy.freedom.data.exception;

import com.freedom.lauzy.exception.ErrorBundle;
import com.freedom.lauzy.model.ErrorBean;

/**
 * Desc : 错误信息
 * Author : Lauzy
 * Date : 2017/7/7
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class ErrorMsgException extends Exception implements ErrorBundle {

    private static final String LTAG = ErrorMsgException.class.getSimpleName();
    private ErrorBean mErrorBean;

    public ErrorMsgException(ErrorBean errorBean) {
        super("The error code is " + errorBean.error_code +
                " ; the error message is " + errorBean.error_msg);
        mErrorBean = errorBean;
    }

    @Override
    public Exception getException() {
        return mErrorBean == null ? new Exception(LTAG + " : api error. ") : new ErrorMsgException(mErrorBean);
    }

    @Override
    public String getErrorMessage() {
        return mErrorBean == null ? LTAG + " : api error. " : new ErrorMsgException(mErrorBean).getMessage();
    }
}
