package com.gank.chen.mvp.model;

/**
 * Creat by chen on 2018/10/18
 * Describe:
 * @author chenbo
 */
public class RegisterModel {
//     "chapterTops": [],
//             "collectIds": [],
//             "email": "",
//             "icon": "",
//             "id": 11772,
//             "password": "123456",
//             "token": "",
//             "type": 0,
//             "username": "13391999591"

    private String username;
    private String token;
    private int type;
    private String password;
    private String passwordNative;
    private int id;
    private String icon;
    private String email;

    public String getPasswordNative() {
        return passwordNative;
    }

    public void setPasswordNative(String passwordNative) {
        this.passwordNative = passwordNative;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
