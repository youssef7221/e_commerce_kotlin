package com.example.e_commerce_kot.data

import android.os.Parcel
import android.os.Parcelable

data class Product(
    val name: String,
    val description: String,
    val productId: Int,   // Corrected to match constructor
    val price: String,
    var isLiked: Boolean,
    var quantity: Int,
    val image: Int // Resource ID for image
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readInt(),  // Read productId as an Int
        parcel.readString().toString(),  // Read price as String
        parcel.readByte() != 0.toByte(),
        parcel.readInt(),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(description)
        parcel.writeInt(productId)  // Write productId
        parcel.writeString(price)  // Write price as String
        parcel.writeByte(if (isLiked) 1 else 0)
        parcel.writeInt(quantity)  // Write quantity
        parcel.writeInt(image)  // Write image resource ID
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Product> {
        override fun createFromParcel(parcel: Parcel): Product {
            return Product(parcel)
        }

        override fun newArray(size: Int): Array<Product?> {
            return arrayOfNulls(size)
        }
    }
}
