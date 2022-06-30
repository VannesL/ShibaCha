package com.example.shibacha_app.models

import android.os.Parcel
import android.os.Parcelable

class CommunityMemberModel(var communityId: String?, var userId: String?, var role:String?) : Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(communityId)
        parcel.writeString(userId)
        parcel.writeString(role)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CommunityMemberModel> {
        override fun createFromParcel(parcel: Parcel): CommunityMemberModel {
            return CommunityMemberModel(parcel)
        }

        override fun newArray(size: Int): Array<CommunityMemberModel?> {
            return arrayOfNulls(size)
        }
    }
}