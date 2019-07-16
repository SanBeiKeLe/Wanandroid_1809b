package com.wanghongli.myapplication.data.entity;

import java.util.ArrayList;

/*
 * created by taofu on 2019-06-20
 **/
public class ArticleData {


    private int curPage;

    private ArrayList<Article> datas;


    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public ArrayList<Article> getDatas() {
        return datas;
    }

    public void setDatas(ArrayList<Article> datas) {
        this.datas = datas;
    }

    public static class Article{

        /**
         * "apkLink": "",
         *                 "author": "xiaoyang",
         *                 "chapterId": 440,
         *                 "chapterName": "官方",
         *                 "collect": false,
         *                 "courseId": 13,
         *                 "desc": "<p>相信大家多数都能说出自定义的步骤，但是很多细节上的问题其实也需要关注。</p><br><p>针对：</p><br><p>MesureSpec.UNSPECIFIED</p><br><p>1. 这个模式什么时候会遇到？</p><p>2. 遇到后怎么处理？</p><p>3. 有什么注意事项？</p><br><p>以后问答每周 2-3 问，没有人回答会挂稍微久一些，希望大家踊跃参与。</p><br><br><br>",
         *                 "envelopePic": "",
         *                 "fresh": true,
         *                 "id": 8613,
         *                 "link": "https://www.wanandroid.com/wenda/show/8613",
         *                 "niceDate": "13小时前",
         *                 "origin": "",
         *                 "prefix": "",
         *                 "projectLink": "",
         *                 "publishTime": 1560961587000,
         *                 "superChapterId": 440,
         *                 "superChapterName": "问答",
         *                 "tags": [
         *                     {
         *                         "name": "问答",
         *                         "url": "/article/list/0?cid=440"
         *                     }
         *                 ],
         *                 "title": "每日一问  详细的描述下自定义 View 测量时 MesureSpec.UNSPECIFIED",
         *                 "type": 0,
         *                 "userId": 2,
         *                 "visible": 1,
         *                 "zan": 5
         */



        private String author;
        private String chapterName;
        private String desc;
        private String envelopePic;
        private String link;
        private String niceDate;
        private String origin;
        private String prefix;
        private String projectLink;
        private long publishTime;
        private String superChapterName;
        private ArrayList<String> tags;
        private String title;
        private boolean fresh;
        private boolean collect;
        private int chapterId;
        private int courseId;
        private int id;
        private int superChapterId;
        private int type;
        private int userId;
        private int visible;
        private int zan;
        private boolean isTop;



        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getChapterName() {
            return chapterName;
        }

        public void setChapterName(String chapterName) {
            this.chapterName = chapterName;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getEnvelopePic() {
            return envelopePic;
        }

        public void setEnvelopePic(String envelopePic) {
            this.envelopePic = envelopePic;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getNiceDate() {
            return niceDate;
        }

        public void setNiceDate(String niceDate) {
            this.niceDate = niceDate;
        }

        public String getOrigin() {
            return origin;
        }

        public void setOrigin(String origin) {
            this.origin = origin;
        }

        public String getPrefix() {
            return prefix;
        }

        public void setPrefix(String prefix) {
            this.prefix = prefix;
        }

        public String getProjectLink() {
            return projectLink;
        }

        public void setProjectLink(String projectLink) {
            this.projectLink = projectLink;
        }

        public long getPublishTime() {
            return publishTime;
        }

        public void setPublishTime(long publishTime) {
            this.publishTime = publishTime;
        }

        public String getSuperChapterName() {
            return superChapterName;
        }

        public void setSuperChapterName(String superChapterName) {
            this.superChapterName = superChapterName;
        }

        public ArrayList<String> getTags() {
            return tags;
        }

        public void setTags(ArrayList<String> tags) {
            this.tags = tags;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public boolean isFresh() {
            return fresh;
        }

        public void setFresh(boolean fresh) {
            this.fresh = fresh;
        }

        public boolean isCollect() {
            return collect;
        }

        public void setCollect(boolean collect) {
            this.collect = collect;
        }

        public int getChapterId() {
            return chapterId;
        }

        public void setChapterId(int chapterId) {
            this.chapterId = chapterId;
        }

        public int getCourseId() {
            return courseId;
        }

        public void setCourseId(int courseId) {
            this.courseId = courseId;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getSuperChapterId() {
            return superChapterId;
        }

        public void setSuperChapterId(int superChapterId) {
            this.superChapterId = superChapterId;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public int getVisible() {
            return visible;
        }

        public void setVisible(int visible) {
            this.visible = visible;
        }

        public int getZan() {
            return zan;
        }

        public void setZan(int zan) {
            this.zan = zan;
        }


        public boolean isTop() {
            return isTop;
        }

    }

}
