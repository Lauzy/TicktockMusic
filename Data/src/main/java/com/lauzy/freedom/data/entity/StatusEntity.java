package com.lauzy.freedom.data.entity;

import com.lauzy.freedom.data.net.constants.NetConstants;

/**
 * Desc : StatusEntity
 * Author : Lauzy
 * Date : 2017/7/7
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class StatusEntity {
    public int error_code;
    public String error_msg;

    public boolean isInvalidCode() {
        return error_code != NetConstants.ErrorCode.CODE_SUCCESS;
    }
}
