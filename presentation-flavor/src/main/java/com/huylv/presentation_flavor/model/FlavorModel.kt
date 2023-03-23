package com.huylv.presentation_flavor.model

import android.os.Parcel
import android.os.Parcelable

data class FlavorModel(
    val name: String?,
    val price: Float,
    val quantity: Float
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readFloat(),
        parcel.readFloat()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeFloat(price)
        parcel.writeFloat(quantity)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<FlavorModel> {
        override fun createFromParcel(parcel: Parcel): FlavorModel {
            return FlavorModel(parcel)
        }

        override fun newArray(size: Int): Array<FlavorModel?> {
            return arrayOfNulls(size)
        }
    }
}