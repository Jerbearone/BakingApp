package com.android.bakingfavorites.DetailFragments;

import android.os.Parcel;
import android.os.Parcelable;

public class IngredientsPojo implements Parcelable {
    int mQuantity;
    String mMeasurement;
    String mingredient;

    public IngredientsPojo (int quantity, String measurement, String ingredient) {
        quantity = this.mQuantity;
        measurement = this.mMeasurement;
        ingredient = this.mingredient;

    }

    public int getmQuantity() {
        return mQuantity;
    }

    public void setmQuantity(int mQuantity) {
        this.mQuantity = mQuantity;
    }

    public String getmMeasurement() {
        return mMeasurement;
    }

    public void setmMeasurement(String mMeasurement) {
        this.mMeasurement = mMeasurement;
    }

    public String getMingredient() {
        return mingredient;
    }

    public void setMingredient(String mingredient) {
        this.mingredient = mingredient;
    }



    protected IngredientsPojo(Parcel in) {
        mQuantity = in.readInt();
        mMeasurement = in.readString();
        mingredient = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mQuantity);
        dest.writeString(mMeasurement);
        dest.writeString(mingredient);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<IngredientsPojo> CREATOR = new Parcelable.Creator<IngredientsPojo>() {
        @Override
        public IngredientsPojo createFromParcel(Parcel in) {
            return new IngredientsPojo(in);
        }

        @Override
        public IngredientsPojo[] newArray(int size) {
            return new IngredientsPojo[size];
        }
    };
}
