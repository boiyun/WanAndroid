package com.gank.chen.mvp.model;

import java.util.Date;

/**
 * @author chen
 * @date 2017/12/17
 */

public class MeiZi {
    /**
     * "_id": "5a2f2486421aa90fe2f02cd2",
     * "createdAt": "2017-12-12T08:36:22.670Z",
     * "desc": "12-12",
     * "publishedAt": "2017-12-15T08:59:11.361Z",
     * "source": "chrome",
     * "type": "\u798f\u5229",
     * "url": "http://7xi8d6.com1.z0.glb.clouddn.com/20171212083612_WvLcTr_Screenshot.jpeg",
     * "used": true,
     * "who": "daimajia"
     */
    public boolean used;
    public String type;
    public String url;
    public String who;
    public String desc;
    public Date updatedAt;
    public Date createdAt;
    public Date publishedAt;


    /**
     * imageview的宽
     */
    public int width;
    /**
     * imageview的高
     */
    public int height;

    /**
     * 闲读的bean
     */
    public String _id;
    public String id;
    public String en_name;
    public String name;
    public String title;
    public String created_at;
    public String icon;
    public int rank;

}
