package com.example.steven.bakingapp.Objects;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Steven on 29/04/2018.
 */

public class Ingredient implements Parcelable {

    private String name;
    private String measure;
    private double quantity;

    public Ingredient(String name, String measure, double quantity) {
        this.name = name;
        this.measure = measure;
        this.quantity = quantity;
    }

    protected Ingredient(Parcel in) {
        name = in.readString();
        measure = in.readString();
        quantity = in.readDouble();
    }

    public static final Creator<Ingredient> CREATOR = new Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel in) {
            return new Ingredient(in);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };

    public String getName() {
        return name;
    }

    public String getMeasure() {
        return measure;
    }

    public double getQuantity() {
        return quantity;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(measure);
        parcel.writeDouble(quantity);
    }
}
