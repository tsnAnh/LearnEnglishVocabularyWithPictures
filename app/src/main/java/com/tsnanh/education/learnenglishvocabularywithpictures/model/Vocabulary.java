package com.tsnanh.education.learnenglishvocabularywithpictures.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

@Entity
public class Vocabulary {
    @Id @Property(nameInDb = "id")
    private long id;
    @Property(nameInDb = "voc_id")
    private int vocabularyId;
    @Property(nameInDb = "cat_id")
    private int categoryId;

    @Generated(hash = 1245526045)
    public Vocabulary(long id, int vocabularyId, int categoryId) {
        this.id = id;
        this.vocabularyId = vocabularyId;
        this.categoryId = categoryId;
    }

    @Generated(hash = 272700973)
    public Vocabulary() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getVocabularyId() {
        return vocabularyId;
    }

    public void setVocabularyId(int vocabularyId) {
        this.vocabularyId = vocabularyId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
}
