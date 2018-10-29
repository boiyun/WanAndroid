package com.gank.chen.http;

/**
 *
 * @author chen
 * @date 2017/12/17
 */

public class ApiFactory {
    protected static final Object MONITOR = new Object();
    private static ApiUtil apiUtil = null;
    public static ApiUtil getApiUtil(){
        synchronized (MONITOR){
            if (apiUtil == null) {
                apiUtil=new ApiRetrofit().getApi();
            }
            return apiUtil;
        }
    }
}
