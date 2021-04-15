package com.droidiot.demoProject.data.model

import android.os.Parcel
import android.os.Parcelable

data class SenderModel(
    val first_name: String?,
    val last_name: String?,
    val picture: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(first_name)
        parcel.writeString(last_name)
        parcel.writeString(picture)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SenderModel> {
        override fun createFromParcel(parcel: Parcel): SenderModel {
            return SenderModel(parcel)
        }

        override fun newArray(size: Int): Array<SenderModel?> {
            return arrayOfNulls(size)
        }
    }
}