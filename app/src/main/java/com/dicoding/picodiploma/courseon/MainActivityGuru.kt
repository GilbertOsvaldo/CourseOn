package com.dicoding.picodiploma.courseon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main_guru.*

class MainActivityGuru : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_guru)
        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bn_menu)
        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        img_add_matpel.setOnClickListener{
            startActivity(Intent(applicationContext, TambahJadwal::class.java))
        }
    }
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.nav_home -> {
//                startActivity(Intent(applicationContext, MainActivityGuru::class.java))
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
//    fun logout(view: View) {
//        FirebaseAuth.getInstance().signOut()
//        startActivity(Intent(this, Login::class.java))
//        finish()
//    }
}