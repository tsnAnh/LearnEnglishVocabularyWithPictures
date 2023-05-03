package dev.tsnanh.education.englishvocabularywithpictures.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "vocabularies")
public class Vocabulary implements Parcelable {
    @Id @Property(nameInDb = "id")
    private long id;
    @Property(nameInDb = "image")
    private String image;
    @Property(nameInDb = "en_us")
    private String en_us;
    @Property (nameInDb = "en_us_type")
    private String en_us_type;
    @Property (nameInDb = "en_us_pr")
    private String en_us_pr;
    @Property (nameInDb = "en_us_audio")
    private String en_us_audio;
    @Property (nameInDb = "en_us_mean")
    private String en_us_mean;
    @Property (nameInDb = "en_us_ex")
    private String en_us_ex;
    @Property (nameInDb = "vi_vn")
    private String vi_vn;
    @Property (nameInDb = "en_uk_pr")
    private String en_uk_pr;
    @Property (nameInDb = "en_uk_audio")
    private String en_uk_audio;
    @Property (nameInDb = "de_de")
    private String de_de;
    @Property (nameInDb = "es_es")
    private String es_es;
    @Property (nameInDb = "updated")
    private String updated;
    @Property (nameInDb = "more_images")
    private String more_images;
    @Property (nameInDb = "liked")
    private int liked;
    @Property (nameInDb = "learned")
    private int learned;
    @Property (nameInDb = "reported")
    private int reported;
    @Property (nameInDb = "status")
    private int status;
    @Property (nameInDb = "like")
    private int like;
    @Property (nameInDb = "note")
    private String note;
    @Property (nameInDb = "learn")
    private int learn;
    @Property (nameInDb = "sync")
    private int sync;
    @Property (nameInDb = "numb_vote")
    private int numb_vote;
    @Property (nameInDb = "is_pro")
    private int is_pro;

    @Generated(hash = 1375417697)
    public Vocabulary(long id, String image, String en_us, String en_us_type,
            String en_us_pr, String en_us_audio, String en_us_mean, String en_us_ex,
            String vi_vn, String en_uk_pr, String en_uk_audio, String de_de,
            String es_es, String updated, String more_images, int liked,
            int learned, int reported, int status, int like, String note, int learn,
            int sync, int numb_vote, int is_pro) {
        this.id = id;
        this.image = image;
        this.en_us = en_us;
        this.en_us_type = en_us_type;
        this.en_us_pr = en_us_pr;
        this.en_us_audio = en_us_audio;
        this.en_us_mean = en_us_mean;
        this.en_us_ex = en_us_ex;
        this.vi_vn = vi_vn;
        this.en_uk_pr = en_uk_pr;
        this.en_uk_audio = en_uk_audio;
        this.de_de = de_de;
        this.es_es = es_es;
        this.updated = updated;
        this.more_images = more_images;
        this.liked = liked;
        this.learned = learned;
        this.reported = reported;
        this.status = status;
        this.like = like;
        this.note = note;
        this.learn = learn;
        this.sync = sync;
        this.numb_vote = numb_vote;
        this.is_pro = is_pro;
    }

    @Generated(hash = 272700973)
    public Vocabulary() {
    }

    protected Vocabulary(Parcel in) {
        id = in.readLong();
        image = in.readString();
        en_us = in.readString();
        en_us_type = in.readString();
        en_us_pr = in.readString();
        en_us_audio = in.readString();
        en_us_mean = in.readString();
        en_us_ex = in.readString();
        vi_vn = in.readString();
        en_uk_pr = in.readString();
        en_uk_audio = in.readString();
        de_de = in.readString();
        es_es = in.readString();
        updated = in.readString();
        more_images = in.readString();
        liked = in.readInt();
        learned = in.readInt();
        reported = in.readInt();
        status = in.readInt();
        like = in.readInt();
        note = in.readString();
        learn = in.readInt();
        sync = in.readInt();
        numb_vote = in.readInt();
        is_pro = in.readInt();
    }

    public static final Creator<Vocabulary> CREATOR = new Creator<Vocabulary>() {
        @Override
        public Vocabulary createFromParcel(Parcel in) {
            return new Vocabulary(in);
        }

        @Override
        public Vocabulary[] newArray(int size) {
            return new Vocabulary[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getEn_us() {
        return en_us;
    }

    public void setEn_us(String en_us) {
        this.en_us = en_us;
    }

    public String getEn_us_type() {
        return en_us_type;
    }

    public void setEn_us_type(String en_us_type) {
        this.en_us_type = en_us_type;
    }

    public String getEn_us_pr() {
        return en_us_pr;
    }

    public void setEn_us_pr(String en_us_pr) {
        this.en_us_pr = en_us_pr;
    }

    public String getEn_us_audio() {
        return en_us_audio;
    }

    public void setEn_us_audio(String en_us_audio) {
        this.en_us_audio = en_us_audio;
    }

    public String getEn_us_mean() {
        return en_us_mean;
    }

    public void setEn_us_mean(String en_us_mean) {
        this.en_us_mean = en_us_mean;
    }

    public String getEn_us_ex() {
        return en_us_ex;
    }

    public void setEn_us_ex(String en_us_ex) {
        this.en_us_ex = en_us_ex;
    }

    public String getVi_vn() {
        return vi_vn;
    }

    public void setVi_vn(String vi_vn) {
        this.vi_vn = vi_vn;
    }

    public String getEn_uk_pr() {
        return en_uk_pr;
    }

    public void setEn_uk_pr(String en_uk_pr) {
        this.en_uk_pr = en_uk_pr;
    }

    public String getEn_uk_audio() {
        return en_uk_audio;
    }

    public void setEn_uk_audio(String en_uk_audio) {
        this.en_uk_audio = en_uk_audio;
    }

    public String getDe_de() {
        return de_de;
    }

    public void setDe_de(String de_de) {
        this.de_de = de_de;
    }

    public String getEs_es() {
        return es_es;
    }

    public void setEs_es(String es_es) {
        this.es_es = es_es;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getMore_images() {
        return more_images;
    }

    public void setMore_images(String more_images) {
        this.more_images = more_images;
    }

    public int getLiked() {
        return liked;
    }

    public void setLiked(int liked) {
        this.liked = liked;
    }

    public int getLearned() {
        return learned;
    }

    public void setLearned(int learned) {
        this.learned = learned;
    }

    public int getReported() {
        return reported;
    }

    public void setReported(int reported) {
        this.reported = reported;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getLearn() {
        return learn;
    }

    public void setLearn(int learn) {
        this.learn = learn;
    }

    public int getSync() {
        return sync;
    }

    public void setSync(int sync) {
        this.sync = sync;
    }

    public int getNumb_vote() {
        return numb_vote;
    }

    public void setNumb_vote(int numb_vote) {
        this.numb_vote = numb_vote;
    }

    public int getIs_pro() {
        return is_pro;
    }

    public void setIs_pro(int is_pro) {
        this.is_pro = is_pro;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeString(image);
        parcel.writeString(en_us);
        parcel.writeString(en_us_type);
        parcel.writeString(en_us_pr);
        parcel.writeString(en_us_audio);
        parcel.writeString(en_us_mean);
        parcel.writeString(en_us_ex);
        parcel.writeString(vi_vn);
        parcel.writeString(en_uk_pr);
        parcel.writeString(en_uk_audio);
        parcel.writeString(de_de);
        parcel.writeString(es_es);
        parcel.writeString(updated);
        parcel.writeString(more_images);
        parcel.writeInt(liked);
        parcel.writeInt(learned);
        parcel.writeInt(reported);
        parcel.writeInt(status);
        parcel.writeInt(like);
        parcel.writeString(note);
        parcel.writeInt(learn);
        parcel.writeInt(sync);
        parcel.writeInt(numb_vote);
        parcel.writeInt(is_pro);
    }
}
