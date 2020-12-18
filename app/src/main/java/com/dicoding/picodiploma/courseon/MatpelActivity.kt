package com.dicoding.picodiploma.courseon

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_konfirmasi_tambah_jadwal.view.*
import kotlinx.android.synthetic.main.fragment_konfirmasi_tambah_jadwal.view.tv_matpel
import kotlinx.android.synthetic.main.fragment_konfirmasi_tambah_jadwal.view.tv_tanggal
import kotlinx.android.synthetic.main.fragment_konfirmasi_tambah_jadwal.view.tv_waktu
import kotlinx.android.synthetic.main.fragment_pesan_online.*
import kotlinx.android.synthetic.main.fragment_pesan_online.view.*
import kotlinx.android.synthetic.main.item_cardview_guru.*
import kotlinx.android.synthetic.main.tambah_jadwal.*

class MatpelActivity : AppCompatActivity() {
    private lateinit var rvGuru: RecyclerView
    private lateinit var adapter: GuruAdapter
    private lateinit var firebaseFirestore: FirebaseFirestore
    private val db = FirebaseFirestore.getInstance()
    private val collectionReference = db.collection("jadwal")
    val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_matpel)
        val matpel = intent.getStringExtra("EXTRA_MATPEL")
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "List Jadwal Guru " + matpel
        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bn_menu)
        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        firebaseFirestore = FirebaseFirestore.getInstance()
        rvGuru = findViewById(R.id.rv_list_guru)
//        if (resources.getStringArray(R.array.nama_matpel) ==

        val query = collectionReference.whereEqualTo("matpel", matpel)
        val options = FirestoreRecyclerOptions.Builder<GuruModel>()
            .setQuery(query, GuruModel::class.java)
            .build()
        adapter = GuruAdapter(options, this, matpel)

        rvGuru.layoutManager =
            LinearLayoutManager(this@MatpelActivity)
        rvGuru.setHasFixedSize(true)
        rvGuru.adapter = adapter
        rvGuru.adapter!!.notifyDataSetChanged()
    }

//    private fun saveData() {
//        val user = mAuth.currentUser
//        val db = Firebase.firestore
//        val jadwal = hashMapOf(
//            "matpel" to et_pilihmatpel.text.toString(),
//            "nama" to user?.displayName.toString(),
//            "tanggal" to et_pilihtanggal.text.toString(),
//            "waktu" to et_pilihwaktu.text.toString(),
//            "jenisPemesanan" to tv_jenis_pemesanan_online.text.toString()
//        )
//
//        db.collection("pemesanan")
//            .add(jadwal)
//    }

    override fun onStart() {
        super.onStart()
        adapter!!.startListening()
    }

    override fun onDestroy() {
        super.onDestroy()
        adapter!!.stopListening()
    }

    private val mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    startActivity(Intent(applicationContext, MainActivity::class.java))
                    return@OnNavigationItemSelectedListener true
                }
                R.id.nav_profile -> {
                    startActivity(Intent(applicationContext, ProfileActivity::class.java))
                    return@OnNavigationItemSelectedListener true
                }
                R.id.nav_log -> {
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}