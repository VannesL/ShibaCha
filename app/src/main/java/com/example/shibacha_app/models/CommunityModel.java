package com.example.shibacha_app.models;

import android.os.Parcel;
import android.os.Parcelable;

public class CommunityModel implements Parcelable {
    private String communityName;
    private String communityDesc;
    private String communityImg;

    public CommunityModel() {

    }

    public CommunityModel(String communityName, String communityDesc, String communityImg) {
        this.communityName = communityName;
        this.communityDesc = communityDesc;
        this.communityImg = communityImg;
    }

    protected CommunityModel(Parcel in) {
        communityName = in.readString();
        communityDesc = in.readString();
        communityImg = in.readString();
    }

    public static final Creator<CommunityModel> CREATOR = new Creator<CommunityModel>() {
        @Override
        public CommunityModel createFromParcel(Parcel in) {
            return new CommunityModel(in);
        }

        @Override
        public CommunityModel[] newArray(int size) {
            return new CommunityModel[size];
        }
    };

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    public String getCommunityDesc() {
        return communityDesc;
    }

    public void setCommunityDesc(String communityDesc) {
        this.communityDesc = communityDesc;
    }

    public String getCommunityImg() {
        return communityImg;
    }

    public void setCommunityImg(String communityImg) {
        this.communityImg = communityImg;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(communityName);
        parcel.writeString(communityDesc);
        parcel.writeString(communityImg);
    }
}
