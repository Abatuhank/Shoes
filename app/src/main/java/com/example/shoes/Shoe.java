package com.example.shoes;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Shoe implements Parcelable
{
    private String name;
    private int size;
    private double price;
    private String imageUrl;
    private String youUrl;

    public Shoe() {}

    public Shoe(String name, int size, double price, String imageUrl, String youUrl) {
        this.name = name;
        this.size = size;
        this.price = price;
        this.imageUrl = imageUrl;
        this.youUrl = youUrl;
    }

    // Getters and setters

    protected Shoe(Parcel in) {
        name = in.readString();
        size = in.readInt();
        price = in.readDouble();
        imageUrl = in.readString();
        youUrl = in.readString();
    }

    public static final Creator<Shoe> CREATOR = new Creator<Shoe>() {
        @Override
        public Shoe createFromParcel(Parcel in) {
            return new Shoe(in);
        }

        @Override
        public Shoe[] newArray(int size) {
            return new Shoe[size];
        }
    };

    public String getYouUrl() {
        return youUrl;
    }

    public void setYouUrl(String youUrl) {
        this.youUrl = youUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeInt(size);
        parcel.writeDouble(price);
        parcel.writeString(imageUrl);
        parcel.writeString(youUrl);
    }
}
