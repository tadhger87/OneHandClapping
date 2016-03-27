package com.tadhg.onehandclapping.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.tadhg.onehandclapping.R;

import java.util.ArrayList;

/**
 * Created by Tadhg on 09/03/2016.
 */
public class Clap {

    //duh
    private String cName;
    private int imageId;
    private String audio;

    public String getCName() {
        return cName;
    }

    public void setCName(String name) {
        this.cName = name;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int image) {
        this.imageId = image;
    }

    public String getAudio(){
        return audio;
    }

    public void setAudio(String mAudio){
        this.audio = mAudio;
    }
    /*public String cName;
    private int id, imageId;
    public static final String EXTRA_CONTENT_DETAIL = "contentDetail";

    public Clap() {
        super();
    }

    public Clap(String title, int imageId) {
        super();

        this.cName = title;

        this.imageId = imageId;


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
*/
}

