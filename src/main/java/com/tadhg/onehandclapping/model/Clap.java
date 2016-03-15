package com.tadhg.onehandclapping.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.tadhg.onehandclapping.R;

import java.util.ArrayList;

/**
 * Created by Tadhg on 09/03/2016.
 */
public class Clap {
//implements Parcelable

    public String cName, pictureRef, clapDate, audioRef;
    private int id, imageId;
    public static final String EXTRA_CONTENT_DETAIL = "contentDetail";

    public Clap() {
        super();
    }

    public Clap(String title, int imageId) {
        super();
        /*this.id = in.readInt();
        this.cName = in.readString();
        this.imageId = in.readInt();*/
        this.cName = title;

        this.imageId = imageId;
       /* this.clapDate = in.readString();
        this.audioRef = in.readString();*/

    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCName() {
        return cName;
    }

    public void setCName(String cName) {
        this.cName = cName;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

   /* public String getClapDate() {
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
    }*/



    @Override
    public String toString() {
        return "Clap [id=" + id + ", clapName=" + cName + ", imageId=" + imageId + "]";
        //", clapDate="
       // + clapDate + ", audioRef=" + audioRef +
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
        Clap other = (Clap) obj;
        if (id != other.id)
            return false;
        return true;
    }

  /*  @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(getId());
        parcel.writeString(getCName());
        parcel.writeInt(getImageId());
        *//*parcel.writeString(getClapDate());
        parcel.writeString(getAudioRef());*//*
    }*/
/*

    public static final Parcelable.Creator<Clap> CREATOR = new Parcelable.Creator<Clap>() {
        public Clap createFromParcel(Parcel in) {
            return new Clap(in);
        }

        public Clap[] newArray(int size) {
            return new Clap[size];
        }
    };
*/

   /* public static ArrayList<Clap> createContactsList(int numContacts) {
        ArrayList<Clap> claps = new ArrayList<Clap>();


        claps.add(new Clap("Clapping", R.drawable.clapping1));
        for (int i = 1; i <= numContacts; i++) {
            contacts.add(new Clap("Person " + ++lastContactId, i <= numContacts / 2));
        }

        return contacts;
    }*/
}

