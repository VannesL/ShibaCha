package com.example.shibacha_app.models

import android.os.Parcel
import android.os.Parcelable

class UserHobbyModel(var userId: String?, var categoryName: String?): Parcelable {

    constructor(): this(null,null){}

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(userId)
        parcel.writeString(categoryName)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UserHobbyModel> {
        override fun createFromParcel(parcel: Parcel): UserHobbyModel {
            return UserHobbyModel(parcel)
        }

        override fun newArray(size: Int): Array<UserHobbyModel?> {
            return arrayOfNulls(size)
        }
    }
}