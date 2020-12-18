package com.dicoding.picodiploma.courseon

import android.os.Parcelable
import com.google.firebase.firestore.DocumentReference
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PemesananModel(
    val pesananId: String?,
    var alamat_pemesanan: String?,
    var matpel: String,
    var nama: String?,
    val nama_pemesan: String?,
    val status: String?,
    var tanggal: String?,
    val tipe_pemesanan: String?,
    var waktu: String?,
    val priority: String?
) : Parcelable {
    constructor() : this(
        "", "", "", "",
        "", "", "", "", "", ""
    )
}