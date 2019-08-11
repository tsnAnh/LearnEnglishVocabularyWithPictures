package com.tsnanh.education.learnenglishvocabularywithpictures.model;

import org.greenrobot.greendao.annotation.*;

@Entity
public class Categories {
    @Id @NotNull @Property(nameInDb = "id")
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
}
