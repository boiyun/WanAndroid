package com.gank.chen.mvp.model;

/**
 * Creat by chen on 2018/10/10
 * Describe: Rxbus通用bean
 *
 * @author chenbo
 */
public class Event<T> {
    public static final int EVENT_CLOSE_ALL_ACTIVITY = 10001;
    /**
     * 注册成功
     */
    public static final int EVENT_REGRISTER_SUCCESS = 10002;
    /**
     * 登录成功
     */
    public static final int EVENT_LOGIN_SUCCESS = 10003;

    private T data;

    private int eventCode = -1;

    public Event(int eventCode) {
        this(eventCode, null);
    }

    public Event(int eventCode, T data) {
        this.eventCode = eventCode;
        this.data = data;
    }

    /**
     * get event code
     *
     * @return
     */
    public int getCode() {
        return this.eventCode;
    }

    /**
     * get event reserved data
     *
     * @return
     */
    public T getData() {
        return this.data;
    }
}
