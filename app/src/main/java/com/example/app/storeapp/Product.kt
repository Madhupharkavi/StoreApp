package com.example.app.storeapp

import android.os.Parcelable
import android.provider.ContactsContract.Data
import androidx.versionedparcelable.ParcelField
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    val id : Int,
    val title : String,
    val price : Double,
    val category : String,
    val description : String,
    val image : String,
    val rating : RateClass
):Parcelable

@Parcelize
data class RateClass(val rate:Double,val count:Int):Parcelable
