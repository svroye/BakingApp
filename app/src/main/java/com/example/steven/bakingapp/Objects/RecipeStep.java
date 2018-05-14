package com.example.steven.bakingapp.Objects;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Steven on 29/04/2018.
 */

public class RecipeStep implements Parcelable {

    private String shortDescription;
    private String fullDescription;
    private String videoUrl;
    private String thumbnailUrl;
    private int id;

    public RecipeStep(String shortDescription, String fullDescription, String videoUrl, String thumbnailUrl, int id) {
        this.shortDescription = shortDescription;
        this.fullDescription = fullDescription;
        this.videoUrl = videoUrl;
        this.thumbnailUrl = thumbnailUrl;
        this.id = id;
    }

    protected RecipeStep(Parcel in) {
        shortDescription = in.readString();
        fullDescription = in.readString();
        videoUrl = in.readString();
        thumbnailUrl = in.readString();
        id = in.readInt();
    }

    public static final Creator<RecipeStep> CREATOR = new Creator<RecipeStep>() {
        @Override
        public RecipeStep createFromParcel(Parcel in) {
            return new RecipeStep(in);
        }

        @Override
        public RecipeStep[] newArray(int size) {
            return new RecipeStep[size];
        }
    };

    public String getShortDescription() {
        return shortDescription;
    }


    public String getFullDescription() {
        return fullDescription;
    }


    public String getVideoUrl() {
        return videoUrl;
    }


    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public int getId() {
        return id;
    }

    public String getUrlToVideo(){
        if (!videoUrl.equals("")){
            return videoUrl;
        } else if (!thumbnailUrl.equals("")){
            return thumbnailUrl;
        } else {
            return null;
        }
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(shortDescription);
        parcel.writeString(fullDescription);
        parcel.writeString(videoUrl);
        parcel.writeString(thumbnailUrl);
        parcel.writeInt(id);
    }
}
