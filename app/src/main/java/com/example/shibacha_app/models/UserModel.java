package com.example.shibacha_app.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Vector;

public class UserModel implements Parcelable {
    private String username;
    private String email;
    private String password;
    private String gender;

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    private Integer age;

    public UserModel() {

    }

    public UserModel(String username, String email, String password, String gender, Integer age) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.age = age;
    }

    protected UserModel(Parcel in) {
        username = in.readString();
        email = in.readString();
        password = in.readString();
        gender = in.readString();
        age = in.readInt();
    }

    public static final Creator<UserModel> CREATOR = new Creator<UserModel>() {
        @Override
        public UserModel createFromParcel(Parcel in) {
            return new UserModel(in);
        }

        @Override
        public UserModel[] newArray(int size) {
            return new UserModel[size];
        }
    };

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(username);
        parcel.writeString(email);
        parcel.writeString(password);
        parcel.writeString(gender);
        parcel.writeInt(age);
    }
}
