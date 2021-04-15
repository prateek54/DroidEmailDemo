package com.droidiot.demoProject.data.model

import android.os.Parcel
import android.os.Parcelable

data class EmailModel(
    val _id: String?,
    val message: String?,
    val subject: String?,
    val time: String?,
    val sender: SenderModel?
) : Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readParcelable(SenderModel::class.java.classLoader)
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(_id)
        parcel.writeString(message)
        parcel.writeString(subject)
        parcel.writeString(time)
        parcel.writeParcelable(sender, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<EmailModel> {
        override fun createFromParcel(parcel: Parcel): EmailModel {
            return EmailModel(parcel)
        }

        override fun newArray(size: Int): Array<EmailModel?> {
            return arrayOfNulls(size)
        }
    }

}