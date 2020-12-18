package com.dicoding.picodiploma.courseon

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GuruModel(
    val nama: String?,
    val tanggal: String?,
    val waktu: String?
): Parcelable{
    constructor() : this("","","")
}