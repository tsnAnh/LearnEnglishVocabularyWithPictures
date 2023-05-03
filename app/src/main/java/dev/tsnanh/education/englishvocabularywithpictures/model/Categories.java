package dev.tsnanh.education.englishvocabularywithpictures.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;


@Entity(nameInDb = "categories")
public class Categories implements Parcelable {
    @Id
    @NotNull
    @Property(nameInDb = "id")
    private long id;
    @Index
    private String title;
    @Property(nameInDb = "parent_id")
    private int parentId;
    @Property(nameInDb = "status")
    private int status;
    @Property(nameInDb = "description")
    private String description;
    private int lft;
    private int rgt;
    @Property(nameInDb = "intro_img")
    private String introImage;

    @Generated(hash = 1851096420)
    public Categories(long id, String title, int parentId, int status,
            String description, int lft, int rgt, String introImage) {
        this.id = id;
        this.title = title;
        this.parentId = parentId;
        this.status = status;
        this.description = description;
        this.lft = lft;
        this.rgt = rgt;
        this.introImage = introImage;
    }

    @Generated(hash = 267348489)
    public Categories() {
    }

    protected Categories(Parcel in) {
        id = in.readLong();
        title = in.readString();
        parentId = in.readInt();
        status = in.readInt();
        description = in.readString();
        lft = in.readInt();
        rgt = in.readInt();
        introImage = in.readString();
    }

    public static final Creator<Categories> CREATOR = new Creator<Categories>() {
        @Override
        public Categories createFromParcel(Parcel in) {
            return new Categories(in);
        }

        @Override
        public Categories[] newArray(int size) {
            return new Categories[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getLft() {
        return lft;
    }

    public void setLft(int lft) {
        this.lft = lft;
    }

    public int getRgt() {
        return rgt;
    }

    public void setRgt(int rgt) {
        this.rgt = rgt;
    }

    public String getIntroImage() {
        return introImage;
    }

    public void setIntroImage(String introImage) {
        this.introImage = introImage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeString(title);
        parcel.writeInt(parentId);
        parcel.writeInt(status);
        parcel.writeString(description);
        parcel.writeInt(lft);
        parcel.writeInt(rgt);
        parcel.writeString(introImage);
    }
}
