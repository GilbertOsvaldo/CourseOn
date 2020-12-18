package com.dicoding.picodiploma.courseon

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.activity_register.*
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ProfileActivity : AppCompatActivity() {
//    private lateinit var email: String
//    private lateinit var password: String
//    private lateinit var database: FirebaseDatabase
    private lateinit var mAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Profile"
        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bn_menu)
        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        mAuth = FirebaseAuth.getInstance()

        loadUserInformation()
    }

    override fun onStart() {
        super.onStart()
        if (mAuth.currentUser==null){
            finish()
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
//    private fun updateUI(currentUser: FirebaseUser?){
//        if(currentUser != null){
//            startActivity(Intent(this, MainActivity::class.java))
//        }else{
//            Toast.makeText(baseContext, "Login failed",
//                Toast.LENGTH_SHORT).show()
//        }
//    }
private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
    when (item.itemId) {
        R.id.nav_home -> {
            if (getRole(this).equals("Guru")) {
                startActivity(Intent(this, MainActivityGuru::class.java))
                return@OnNavigationItemSelectedListener true
            } else if (getRole(this).equals("Murid")){
                startActivity(Intent(applicationContext, MainActivity::class.java))
                return@OnNavigationItemSelectedListener true
            }
        }
        R.id.nav_profile -> {
//            startActivity(Intent(applicationContext, ProfileActivity::class.java))
            return@OnNavigationItemSelectedListener true
        }
        R.id.nav_log -> {
            if (getRole(this).equals("Guru")) {
                startActivity(Intent(applicationContext, LihatLogGuruActivity::class.java))
            } else if (getRole(this).equals("Murid")){
                startActivity(Intent(applicationContext, LihatLogActivity::class.java))
            }
            return@OnNavigationItemSelectedListener true
        }
    }
    true
}
    private fun loadUserInformation() {
        val user = mAuth.currentUser
        if (user?.uid != null) {
            tv_nama.text = user.displayName
            tv_email.text = user?.email
        }
    }
    fun logout(view: View) {
        FirebaseAuth.getInstance().signOut()
        startActivity(Intent(this, Login::class.java))
        finish()
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
    fun getRole(context: Context): String {
        var role = ""
        val docRef = Firebase.firestore.collection("users").document(mAuth.uid.toString())
        docRef.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                var document: DocumentSnapshot? = task?.result
                if (document!!.exists()) {
                    if (mAuth.currentUser != null) {
                        if (document?.getString("role").toString() == "Guru") {
                            role = "Guru"
                            finish()
                        }
                        if (document?.getString("role").toString() == "Murid") {
                            role = "Murid"
                            finish()
                        }
                    }
                }
            }
        }
        return role
    }
}