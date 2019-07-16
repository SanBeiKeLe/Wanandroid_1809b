package com.wanghongli.myapplication.todaynews;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Transient;

import java.util.List;
@Entity
public class DataBean {

    @Id
    private Long lid;
/*
        *
         * children : []
         * courseId : 13
         * id : 294
         * name : 完整项目
         * order : 145000
         * parentChapterId : 293
         * userControlSetTop : false
         * visible : 0*/


        private int courseId;
        private int id;
        private String name;
        private int order;
        private int parentChapterId;
        private boolean userControlSetTop;
        private int visible;
        @Transient
        private List<?> children;

        @Generated(hash = 346371924)
        public DataBean(Long lid, int courseId, int id, String name, int order, int parentChapterId, boolean userControlSetTop, int visible) {
            this.lid = lid;
            this.courseId = courseId;
            this.id = id;
            this.name = name;
            this.order = order;
            this.parentChapterId = parentChapterId;
            this.userControlSetTop = userControlSetTop;
            this.visible = visible;
        }

        @Generated(hash = 908697775)
        public DataBean() {
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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getOrder() {
            return order;
        }

        public void setOrder(int order) {
            this.order = order;
        }

        public int getParentChapterId() {
            return parentChapterId;
        }

        public void setParentChapterId(int parentChapterId) {
            this.parentChapterId = parentChapterId;
        }

        public boolean isUserControlSetTop() {
            return userControlSetTop;
        }

        public void setUserControlSetTop(boolean userControlSetTop) {
            this.userControlSetTop = userControlSetTop;
        }

        public int getVisible() {
            return visible;
        }

        public void setVisible(int visible) {
            this.visible = visible;
        }

        public List<?> getChildren() {
            return children;
        }

        public void setChildren(List<?> children) {
            this.children = children;
        }

        public Long getLid() {
            return this.lid;
        }

        public void setLid(Long lid) {
            this.lid = lid;
        }

        public boolean getUserControlSetTop() {
            return this.userControlSetTop;
        }

    }
