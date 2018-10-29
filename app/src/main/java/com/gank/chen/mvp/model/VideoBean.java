package com.gank.chen.mvp.model;

/**
 *
 * @author ChenBo
 * @date 2018/5/10
 */

public class VideoBean {
    //     "_id": "58ae6af7421aa957f9dd6dc3",
//             "createdAt": "2017-02-23T12:54:15.286Z",
//             "desc": "\u6211\u5abd\u8981\u6211\u544a\u8a34\u4f60 ",
//             "publishedAt": "2018-03-12T08:44:50.326Z",
//             "source": "chrome",
//             "type": "\u4f11\u606f\u89c6\u9891",
//             "url": "https://v.qq.com/x/page/m0377ib544o.html?start=1",
//             "used": true,
//             "who": "lxxself"

    private String _id;
    private String _icreatedAtd;
    private String desc;
    private String publishedAt;
    private String source;
    private String type;
    private String url;
    private boolean used;
    private String who;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String get_icreatedAtd() {
        return _icreatedAtd;
    }

    public void set_icreatedAtd(String _icreatedAtd) {
        this._icreatedAtd = _icreatedAtd;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }
}
