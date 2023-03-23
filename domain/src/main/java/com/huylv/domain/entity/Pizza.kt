//package com.huylv.domain.entity
//
//import android.os.Parcel
//import android.os.Parcelable
//
//data class Pizza(
//    val flavors: List<Flavor>?,
//    val price: Int
//) : Parcelable {
//    constructor(parcel: Parcel) : this(
//        parcel.createTypedArrayList(Flavor),
//        parcel.readInt()
//    ) {
//    }
//
//    override fun writeToParcel(parcel: Parcel, flags: Int) {
//        parcel.writeTypedList(flavors)
//        parcel.writeInt(price)
//    }
//
//    override fun describeContents(): Int {
//        return 0
//    }
//
//    companion object CREATOR : Parcelable.Creator<Pizza> {
//        override fun createFromParcel(parcel: Parcel): Pizza {
//            return Pizza(parcel)
//        }
//
//        override fun newArray(size: Int): Array<Pizza?> {
//            return arrayOfNulls(size)
//        }
//    }
//}