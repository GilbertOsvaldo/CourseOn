package com.dicoding.picodiploma.courseon

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_konfirmasi_tambah_jadwal.*
import kotlinx.android.synthetic.main.fragment_konfirmasi_tambah_jadwal.view.*
import kotlinx.android.synthetic.main.fragment_konfirmasi_tambah_jadwal.view.tv_matpel
import kotlinx.android.synthetic.main.fragment_konfirmasi_tambah_jadwal.view.tv_tanggal
import kotlinx.android.synthetic.main.fragment_konfirmasi_tambah_jadwal.view.tv_waktu
import kotlinx.android.synthetic.main.fragment_pesan_online.view.*
import kotlinx.android.synthetic.main.tambah_jadwal.*
import java.util.*

class TambahJadwal : AppCompatActivity() {
    val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    val user = mAuth.currentUser
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tambah_jadwal)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Tambah Jadwal"
        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bn_menu)
        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        val c = Calendar.getInstance()

        et_pilihtanggal.setOnClickListener() {
            val datePickerDialog =
                DatePickerDialog.OnDateSetListener { datePicker, mYear, mMonth, mDay ->
                    c.set(Calendar.YEAR, mYear)
                    c.set(Calendar.MONTH, mMonth)
                    c.set(Calendar.DAY_OF_MONTH, mDay)
                    et_pilihtanggal.setText("$mDay/$mMonth/$mYear")
                }
            DatePickerDialog(
                this,
                datePickerDialog,
                c.get(Calendar.YEAR),
                c.get(Calendar.MONTH),
                c.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        et_pilihwaktu.setOnClickListener() {
            val timePickerDialog = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                c.set(Calendar.HOUR_OF_DAY, hour)
                c.set(Calendar.MINUTE, minute)
                et_pilihwaktu.setText("$hour:$minute")
            }
            TimePickerDialog(
                this,
                timePickerDialog,
                c.get(Calendar.HOUR_OF_DAY),
                c.get(Calendar.MINUTE),
                true
            ).show()
        }


        btn_TambahJadwal.setOnClickListener {
            val tanggal = et_pilihtanggal.text.toString()
            val waktu = et_pilihwaktu.text.toString()
            val matpel = et_pilihmatpel.text.toString()
            val builder = AlertDialog.Builder(this)
            if (et_pilihtanggal.text.toString().isEmpty()) {
                et_pilihtanggal.error = "Masukkan tanggal"
                et_pilihtanggal.requestFocus()
            } else if (et_pilihwaktu.text.toString().isEmpty()) {
                et_pilihwaktu.error = "Masukkan waktu"
                et_pilihwaktu.requestFocus()
            } else if (et_pilihmatpel.text.toString().isEmpty()) {
                et_pilihmatpel.error = "Masukkan matpel"
                et_pilihmatpel.requestFocus()
            } else {
                val inflater = layoutInflater
                builder.setTitle("Konfirmasi Jadwal")
                val dialogLayout =
                    inflater.inflate(R.layout.fragment_konfirmasi_tambah_jadwal, null)

                builder.setView(dialogLayout)
                dialogLayout.tv_tanggal.text = "Tanggal : " + tanggal
                dialogLayout.tv_waktu.text = "Waktu   : " + waktu
                dialogLayout.tv_matpel.text = "Matpel  : " + matpel

                builder.setPositiveButton("SIMPAN") { dialogInterface, id ->
                    saveData(user?.displayName.toString(),et_pilihmatpel.text.toString(), et_pilihtanggal.text.toString(),et_pilihwaktu.text.toString())
                    Toast.makeText(this, "Berhasil menambahkan jadwal", Toast.LENGTH_LONG).show()
                    startActivity(Intent(applicationContext, MainActivityGuru::class.java))
                }
                builder.setNegativeButton("Batalkan") { dialogInterface, id ->
                }
                builder.show()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private val mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    startActivity(Intent(applicationContext, MainActivityGuru::class.java))
                    return@OnNavigationItemSelectedListener true
                }
                R.id.nav_profile -> {
                    startActivity(Intent(applicationContext, ProfileActivity::class.java))
                    return@OnNavigationItemSelectedListener true

                }
                R.id.nav_log -> {
                    startActivity(Intent(applicationContext, LihatLogGuruActivity::class.java))
                    return@OnNavigationItemSelectedListener true
                }
            }
            true
        }

    fun saveData(nama: String, matpel: String, tanggal: String, waktu: String) {

        val db = Firebase.firestore
        val jadwal = hashMapOf(
            "matpel" to matpel,
            "nama" to nama,
            "tanggal" to tanggal,
            "waktu" to waktu
        )

        db.collection("jadwal")
            .add(jadwal)
    }
}
