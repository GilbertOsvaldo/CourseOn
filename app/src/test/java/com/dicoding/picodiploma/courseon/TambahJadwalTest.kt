package com.dicoding.picodiploma.courseon

import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.tambah_jadwal.*
import org.junit.Assert.*
import org.junit.Test

class TestFirebase{
//    val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
//    private val db = FirebaseFirestore.getInstance()
//    private val collectionReference = db.collection("jadwal")


    @Test
    fun saveData1(){
        val dummyMatpel = "IPA"
        val dummyTanggal = ""
        val dummyWaktu = "17.8"
        var model = PemesananModel()
//        val objek = TambahJadwal()
//        objek.saveData(dummyName,dummyMatpel,dummyTanggal,dummyWaktu)
        model.matpel = dummyMatpel
        model.tanggal = dummyTanggal
        model.waktu = dummyWaktu
        assertEquals(dummyMatpel, model.matpel)
        assertEquals(dummyTanggal, model.tanggal)
        assertEquals(dummyWaktu, model.waktu)
        }

    @Test
    fun saveData2(){
        val dummyMatpel = "IPA"
        val dummyTanggal = "17/11/2020"
        val dummyWaktu = "17.8"
        var model = PemesananModel()
//        val objek = TambahJadwal()
//        objek.saveData(dummyName,dummyMatpel,dummyTanggal,dummyWaktu)
        model.matpel = dummyMatpel
        model.tanggal = dummyTanggal
        model.waktu = dummyWaktu
        assertEquals(dummyMatpel, model.matpel)
        assertEquals(dummyTanggal, model.tanggal)
        assertEquals(dummyWaktu, model.waktu)
    }

    @Test
    fun saveData3(){
        val dummyMatpel = "IPA"
        val dummyTanggal = "17/11/2020"
        val dummyWaktu = "17.8"
        var model = PemesananModel()
//        val objek = TambahJadwal()
//        objek.saveData(dummyName,dummyMatpel,dummyTanggal,dummyWaktu)
        model.matpel = dummyMatpel
        model.tanggal = dummyTanggal
        model.waktu = dummyWaktu
        assertEquals(dummyMatpel, model.matpel)
        assertEquals(dummyTanggal, model.tanggal)
        assertEquals(dummyWaktu, model.waktu)
    }

    @Test
    fun showDialogOffline1(){
        val dummyAlamat = "Koja"
        var model = PemesananModel()
//        val objek = TambahJadwal()
//        objek.saveData(dummyName,dummyMatpel,dummyTanggal,dummyWaktu)

        model.alamat_pemesanan = dummyAlamat
        assertEquals(dummyAlamat, model.matpel)

    }

    @Test
    fun showDialogOffline2(){
        val dummyAlamat = "Koja"
        var model = PemesananModel()
//        val objek = TambahJadwal()
//        objek.saveData(dummyName,dummyMatpel,dummyTanggal,dummyWaktu)

        model.alamat_pemesanan = dummyAlamat
        assertEquals(dummyAlamat, model.matpel)
    }
}