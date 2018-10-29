package com.gank.chen.mvp.model;

/**
 * @author chen
 * @date 2017/12/17
 */

public class BaseModel<T> {
    /**
     * errorCode = 0 代表执行成功,如果为负数则认为错误
     * errorCode = -1001 代表登录失效，需要重新登录。
     */
    public int errorCode;
    public String errorMsg;
    public T data;

    public boolean isOk() {
        return errorCode == 0;
    }
}
