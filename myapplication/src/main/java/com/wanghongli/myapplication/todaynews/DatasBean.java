package com.wanghongli.myapplication.todaynews;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Transient;

import java.util.List;
import org.greenrobot.greendao.annotation.Generated;
@Entity
public class DatasBean {
        /**
         * apkLink :
         * author : TzuChiangLi
         * chapterId : 294
         * chapterName : 完整项目
         * collect : false
         * courseId : 13
         * desc : 当时毕业公司安排我学习Android的开发以快速开发项目，所以在公司一直MVC的模式开发，在看了现在的主流及趋势后，发现MVP是进步路上的必修课，所以就参考了很多大神的项目学习MVP的写法和思路。
         * envelopePic : https://wanandroid.com/blogimgs/bf9ed860-3ab1-4bea-9c9a-6de3c75e861b.png
         * fresh : false
         * id : 8658
         * link : http://www.wanandroid.com/blog/show/2617
         * niceDate : 2019-07-01
         * origin :
         * prefix :
         * projectLink : https://github.com/TzuChiangLi/WanAndroid
         * publishTime : 1561983121000
         * superChapterId : 294
         * superChapterName : 开源项目主Tab
         * tags : [{"name":"项目","url":"/project/list/1?cid=294"}]
         * title : WanAndroid 个人第一个练手项目分享
         * type : 0
         * userId : -1
         * visible : 0
         * zan : 0
         */
        @Id
        private Long llid;

        private String apkLink;
        private String author;
        private int chapterId;
        private String chapterName;
        private boolean collect;
        private int courseId;
        private String desc;
        private String envelopePic;
        private boolean fresh;
        private int id;
        private String link;
        private String niceDate;
        private String origin;
        private String prefix;
        private String projectLink;
        private long publishTime;
        private int superChapterId;
        private String superChapterName;
        private String title;
        private int type;
        private int userId;
        private int visible;
        private int zan;
        @Transient
        private List<TagsBean> tags;

        @Generated(hash = 1371313107)
        public DatasBean(Long llid, String apkLink, String author, int chapterId, String chapterName, boolean collect,
                int courseId, String desc, String envelopePic, boolean fresh, int id, String link, String niceDate,
                String origin, String prefix, String projectLink, long publishTime, int superChapterId, String superChapterName,
                String title, int type, int userId, int visible, int zan) {
            this.llid = llid;
            this.apkLink = apkLink;
            this.author = author;
            this.chapterId = chapterId;
            this.chapterName = chapterName;
            this.collect = collect;
            this.courseId = courseId;
            this.desc = desc;
            this.envelopePic = envelopePic;
            this.fresh = fresh;
            this.id = id;
            this.link = link;
            this.niceDate = niceDate;
            this.origin = origin;
            this.prefix = prefix;
            this.projectLink = projectLink;
            this.publishTime = publishTime;
            this.superChapterId = superChapterId;
            this.superChapterName = superChapterName;
            this.title = title;
            this.type = type;
            this.userId = userId;
            this.visible = visible;
            this.zan = zan;
        }

        @Generated(hash = 128729784)
        public DatasBean() {
        }

        public String getApkLink() {
            return apkLink;
        }

        public void setApkLink(String apkLink) {
            this.apkLink = apkLink;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public int getChapterId() {
            return chapterId;
        }

        public void setChapterId(int chapterId) {
            this.chapterId = chapterId;
        }

        public String getChapterName() {
            return chapterName;
        }

        public void setChapterName(String chapterName) {
            this.chapterName = chapterName;
        }

        public boolean isCollect() {
            return collect;
        }

        public void setCollect(boolean collect) {
            this.collect = collect;
        }

        public int getCourseId() {
            return courseId;
        }

        public void setCourseId(int courseId) {
            this.courseId = courseId;
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

        public boolean isFresh() {
            return fresh;
        }

        public void setFresh(boolean fresh) {
            this.fresh = fresh;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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

        public int getSuperChapterId() {
            return superChapterId;
        }

        public void setSuperChapterId(int superChapterId) {
            this.superChapterId = superChapterId;
        }

        public String getSuperChapterName() {
            return superChapterName;
        }

        public void setSuperChapterName(String superChapterName) {
            this.superChapterName = superChapterName;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
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

        public List<TagsBean> getTags() {
            return tags;
        }

        public void setTags(List<TagsBean> tags) {
            this.tags = tags;
        }

        public Long getLlid() {
            return this.llid;
        }

        public void setLlid(Long llid) {
            this.llid = llid;
        }

        public boolean getCollect() {
            return this.collect;
        }

        public boolean getFresh() {
            return this.fresh;
        }

        public static class TagsBean {
            /**
             * name : 项目
             * url : /project/list/1?cid=294
             */

            private String name;
            private String url;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }
}
