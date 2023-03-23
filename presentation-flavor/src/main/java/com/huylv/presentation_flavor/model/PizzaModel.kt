package com.huylv.presentation_flavor.model

import android.os.Parcel
import android.os.Parcelable

class PizzaModel(
    val flavors: List<FlavorModel>?,
    val price: Float
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.createTypedArrayList(FlavorModel),
        parcel.readFloat()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeTypedList(flavors)
        parcel.writeFloat(price)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PizzaModel> {
        override fun createFromParcel(parcel: Parcel): PizzaModel {
            return PizzaModel(parcel)
        }

        override fun newArray(size: Int): Array<PizzaModel?> {
            return arrayOfNulls(size)
        }
    }
}