package com.gank.chen.mvp.model;

import java.util.List;

/**
 * @author chenbo
 */
public class MainDataModel {
    private int code;
    private String msg;
    private Data data;


    public void setCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }


    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }


    public void setData(Data data) {
        this.data = data;
    }

    public Data getData() {
        return data;
    }

    public class Banner {

        private String name;
        private String url;


        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }


        public void setUrl(String url) {
            this.url = url;
        }

        public String getUrl() {
            return url;
        }

    }

    public class Center {

        private String name;
        private String url;


        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }


        public void setUrl(String url) {
            this.url = url;
        }

        public String getUrl() {
            return url;
        }

    }

    public class Recommend {

        private String name;
        private String url;


        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }


        public void setUrl(String url) {
            this.url = url;
        }

        public String getUrl() {
            return url;
        }

    }

    public class Data {

        private List<Banner> banner;
        private List<Center> center;
        private List<Recommend> recommend;


        public void setBanner(List<Banner> banner) {
            this.banner = banner;
        }

        public List<Banner> getBanner() {
            return banner;
        }


        public void setCenter(List<Center> center) {
            this.center = center;
        }

        public List<Center> getCenter() {
            return center;
        }


        public void setRecommend(List<Recommend> recommend) {
            this.recommend = recommend;
        }

        public List<Recommend> getRecommend() {
            return recommend;
        }

    }
}
