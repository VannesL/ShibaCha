package com.example.shibacha_app.models;

import android.os.Parcel;
import android.os.Parcelable;

public class CommunityModel implements Parcelable {
    private String communityId;
    private String communityName;
    private String communityDesc;
    private String communityImg;
    private String communityCategory;
    private Integer communityMembers;

    public CommunityModel() {

    }

    public CommunityModel(String communityId, String communityName, String communityDesc, String communityImg, String communityCategory) {
        this.communityId = communityId;
        this.communityName = communityName;
        this.communityDesc = communityDesc;
        this.communityImg = communityImg;
        this.communityCategory = communityCategory;
        this.communityMembers = 1;
    }
    public CommunityModel(String communityId, String communityName, String communityDesc, String communityImg, String communityCategory, Integer communityMembers) {
        this.communityId = communityId;
        this.communityName = communityName;
        this.communityDesc = communityDesc;
        this.communityImg = communityImg;
        this.communityCategory = communityCategory;
        this.communityMembers = communityMembers;
    }

    protected CommunityModel(Parcel in) {
        communityId = in.readString();
        communityName = in.readString();
        communityDesc = in.readString();
        communityImg = in.readString();
        communityCategory = in.readString();
        communityMembers = in.readInt();
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

    public String getCommunityId() {
        return communityId;
    }

    public void setCommunityId(String communityId) {
        this.communityId = communityId;
    }

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

    public String getCommunityCategory() {
        return communityCategory;
    }

    public void setCommunityCategory(String communityCategory) {
        this.communityCategory = communityCategory;
    }

    public Integer getCommunityMembers() { return communityMembers; }

    public void setCommunityMembers(Integer communityMembers) { this.communityMembers = communityMembers; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(communityId);
        parcel.writeString(communityName);
        parcel.writeString(communityDesc);
        parcel.writeString(communityImg);
        parcel.writeString(communityCategory);
        parcel.writeInt(communityMembers);
    }
}