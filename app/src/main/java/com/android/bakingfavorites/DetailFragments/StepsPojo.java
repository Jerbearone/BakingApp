package com.android.bakingfavorites.DetailFragments;

import android.os.Parcel;
import android.os.Parcelable;

public class StepsPojo implements Parcelable {
    int id;
    String stepDescription;
    String description;
    String videoURL;
    String thumbnailURL;

    public StepsPojo(int id, String stepDescription, String videoURL, String thumbnailURL, String description) {
        id = this.id;
        stepDescription = this.stepDescription;
        description = this.description;
        videoURL = this.videoURL;
        thumbnailURL = this.thumbnailURL;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStepDescription() {
        return stepDescription;
    }

    public void setStepDescription(String stepDescription) {
        this.stepDescription = stepDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }

    protected StepsPojo(Parcel in) {
        id = in.readInt();
        stepDescription = in.readString();
        description = in.readString();
        videoURL = in.readString();
        thumbnailURL = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(stepDescription);
        dest.writeString(description);
        dest.writeString(videoURL);
        dest.writeString(thumbnailURL);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<StepsPojo> CREATOR = new Parcelable.Creator<StepsPojo>() {
        @Override
        public StepsPojo createFromParcel(Parcel in) {
            return new StepsPojo(in);
        }

        @Override
        public StepsPojo[] newArray(int size) {
            return new StepsPojo[size];
        }
    };
}