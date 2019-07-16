package com.wanghongli.myapplication.data.entity;

/*
 * created by taofu on 2019-06-20
 **/
public class Banner {

    /**
     *   "desc": "一起来做个App吧",
     *             "id": 10,
     *             "imagePath": "https://www.wanandroid.com/blogimgs/50c115c2-cf6c-4802-aa7b-a4334de444cd.png",
     *             "isVisible": 1,
     *             "order": 2,
     *             "title": "一起来做个App吧",
     *             "type": 0,
     *             "url": "http://www.wanandroid.com/blog/show/2"
     */


    private String desc;
    private String imagePath;
    private String title;
    private String url;

    private int id;
    private int isVisible;
    private int order;
    private int type;


    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIsVisible() {
        return isVisible;
    }

    public void setIsVisible(int isVisible) {
        this.isVisible = isVisible;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
