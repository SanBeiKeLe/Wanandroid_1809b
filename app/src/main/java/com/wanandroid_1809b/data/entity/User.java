package com.wanandroid_1809b.data.entity;

/*
 * created by taofu on 2019-06-11
 **/


public class User {

    /**
     *    "admin": false,
     *         "chapterTops": [],
     *         "collectIds": [],
     *         "email": "",
     *         "icon": "",
     *         "id": 25357,
     *         "password": "",
     *         "token": "",
     *         "type": 0,
     *         "username": "zhangsan8881"
     */


    private boolean admin;

    private int id ;

    private String username;


    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    @Override
    public String toString() {
        return "User{" +
                "admin=" + admin +
                ", id=" + id +
                ", username='" + username + '\'' +
                '}';
    }
}
