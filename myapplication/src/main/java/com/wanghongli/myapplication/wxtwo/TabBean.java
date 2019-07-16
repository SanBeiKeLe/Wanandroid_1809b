package com.wanghongli.myapplication.wxtwo;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Transient;

import java.io.Serializable;
import java.util.List;
import org.greenrobot.greendao.annotation.Generated;
@Entity
public class TabBean implements Parcelable {
    /**
     * children : []
     * courseId : 13
     * id : 408
     * name : 鸿洋
     * order : 190000
     * parentChapterId : 407
     * userControlSetTop : false
     * visible : 1
     */
    @Id
    private Long lid;

    private int courseId;
    private int id;
    private String name;
    private int order;
    public boolean isLike;
    private int parentChapterId;
    private boolean userControlSetTop;
    private int visible;

    @Transient
    private List<?> children;

    public TabBean(int courseId, String name, int order, int parentChapterId, boolean userControlSetTop, int visible, List<?> children) {
        this.courseId = courseId;
        this.name = name;
        this.order = order;
        this.parentChapterId = parentChapterId;
        this.userControlSetTop = userControlSetTop;
        this.visible = visible;
        this.children = children;
    }

    protected TabBean(Parcel in) {
        courseId = in.readInt();
        id = in.readInt();
        name = in.readString();
        order = in.readInt();
        parentChapterId = in.readInt();
        userControlSetTop = in.readByte() != 0;
        visible = in.readInt();
    }



    @Generated(hash = 1045823644)
    public TabBean(Long lid, int courseId, int id, String name, int order, boolean isLike, int parentChapterId, boolean userControlSetTop,
            int visible) {
        this.lid = lid;
        this.courseId = courseId;
        this.id = id;
        this.name = name;
        this.order = order;
        this.isLike = isLike;
        this.parentChapterId = parentChapterId;
        this.userControlSetTop = userControlSetTop;
        this.visible = visible;
    }

    @Generated(hash = 1439219582)
    public TabBean() {
    }

    public static final Creator<TabBean> CREATOR = new Creator<TabBean>() {
        @Override
        public TabBean createFromParcel(Parcel in) {
            return new TabBean(in);
        }

        @Override
        public TabBean[] newArray(int size) {
            return new TabBean[size];
        }
    };

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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(courseId);
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeInt(order);
        dest.writeInt(parentChapterId);
        dest.writeByte((byte) (userControlSetTop ? 1 : 0));
        dest.writeInt(visible);
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

    @Override
    public String toString() {
        return "TabBean{" +
                "lid=" + lid +
                ", courseId=" + courseId +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", order=" + order +
                ", parentChapterId=" + parentChapterId +
                ", userControlSetTop=" + userControlSetTop +
                ", visible=" + visible +
                ", children=" + children +
                '}';
    }

    public boolean getIsLike() {
        return this.isLike;
    }

    public void setIsLike(boolean isLike) {
        this.isLike = isLike;
    }
}
