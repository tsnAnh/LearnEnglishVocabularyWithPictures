package dev.tsnanh.education.englishvocabularywithpictures.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

@Entity (nameInDb = "voc_cat")
public class VocabularyCategories implements Parcelable {
    @Id @Property(nameInDb = "id")
    private long id;
    @Property(nameInDb = "voc_id")
    private int vocabularyId;
    @Property(nameInDb = "cat_id")
    private int categoryId;

    @Generated(hash = 1377037638)
    public VocabularyCategories(long id, int vocabularyId, int categoryId) {
        this.id = id;
        this.vocabularyId = vocabularyId;
        this.categoryId = categoryId;
    }

    @Generated(hash = 390617595)
    public VocabularyCategories() {
    }

    protected VocabularyCategories(Parcel in) {
        id = in.readLong();
        vocabularyId = in.readInt();
        categoryId = in.readInt();
    }

    public static final Creator<VocabularyCategories> CREATOR = new Creator<VocabularyCategories>() {
        @Override
        public VocabularyCategories createFromParcel(Parcel in) {
            return new VocabularyCategories(in);
        }

        @Override
        public VocabularyCategories[] newArray(int size) {
            return new VocabularyCategories[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeInt(vocabularyId);
        parcel.writeInt(categoryId);
    }
}
