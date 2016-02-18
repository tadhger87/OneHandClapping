package com.tadhg.onehandclapping.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Tadhg on 13/11/2015.
 */
public class ClapItem implements Parcelable{


    private String clapName;
    private int clapThumbnail;
    private int id;
    private String clapDate;
    private String audioRef;
    public static final String EXTRA_CONTENT_DETAIL = "contentDetail";

    public ClapItem() {
        super();
    }

    private ClapItem(Parcel in) {
        super();
        this.id = in.readInt();
        this.clapName = in.readString();
        this.clapThumbnail = in.readInt();
        this.clapDate = in.readString();
        this.audioRef = in.readString();

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClapName() {
        return clapName;
    }

    public void setClapName(String clapName) {
        this.clapName = clapName;
    }

    public int getClapThumbnail() {
        return clapThumbnail;
    }

    public void setClapThumbnail(int clapThumbnail) {
        this.clapThumbnail = clapThumbnail;
    }

    public String getClapDate() {
        return clapDate;
    }

    public void setClapDate(String clapDate) {
        this.clapDate = clapDate;
    }

    public String getAudioRef() {
        return audioRef;
    }

    public void setAudioRef(String audioRef) {
        this.audioRef = audioRef;
    }



    @Override
    public String toString() {
        return "Clap [id=" + id + ", clapName=" + clapName + ", clapThumbnail=" + clapThumbnail + ", clapDate="
                + clapDate + ", audioRef=" + audioRef + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ClapItem other = (ClapItem) obj;
        if (id != other.id)
            return false;
        return true;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(getId());
        parcel.writeString(getClapName());
        parcel.writeInt(getClapThumbnail());
        parcel.writeString(getClapDate());
        parcel.writeString(getAudioRef());
    }

    public static final Parcelable.Creator<ClapItem> CREATOR = new Parcelable.Creator<ClapItem>() {
        public ClapItem createFromParcel(Parcel in) {
            return new ClapItem(in);
        }

        public ClapItem[] newArray(int size) {
            return new ClapItem[size];
        }
    };
}
