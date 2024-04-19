package com.app.workshop.data

import android.os.Parcel
import android.os.Parcelable

data class Post(
    var body: String? = null,
    var id: Int? = null,
    var title: String? = null,
    var userId: Int? = null
) : Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readInt(),

    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(body)
        parcel.writeInt(id!!)
        parcel.writeString(title)
        parcel.writeInt(userId!!)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Post> {
        override fun createFromParcel(parcel: Parcel): Post {
            return Post(parcel)
        }

        override fun newArray(size: Int): Array<Post?> {
            return arrayOfNulls(size)
        }
    }

}