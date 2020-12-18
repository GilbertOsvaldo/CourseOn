package com.dicoding.picodiploma.courseon

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*


class Chatroom : AppCompatActivity() {
    private var btn_send_msg: Button? = null
    private var input_msg: EditText? = null
    private var chat_conversation: TextView? = null
    private var user_name: String? = null
    private var room_name: String? = null
    private var root: DatabaseReference? = null
    private var temp_key: String? = null
    private lateinit var mAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chatroom)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Chatroom Konsultasi"

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bn_menu)
        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        btn_send_msg = findViewById<View>(R.id.button) as Button
        input_msg = findViewById<View>(R.id.editText) as EditText
        chat_conversation = findViewById<View>(R.id.textView) as TextView
//        user_name = intent.extras!!["user_name"].toString()
//        room_name = intent.extras!!["room_name"].toString()
//        title = "Room - $room_name"
//        root = FirebaseDatabase.getInstance().reference.child(room_name!!)
//        btn_send_msg!!.setOnClickListener {
//            val map: Map<String, Any> =
//                HashMap()
//            temp_key = root!!.push().key
//            root!!.updateChildren(map)
//            val message_root = root!!.child(temp_key!!)
//            val map2: MutableMap<String, Any> =
//                HashMap()
//            map2["name"] = user_name!!
//            map2["msg"] = input_msg!!.text.toString()
//            message_root.updateChildren(map2)
//        }
//        root!!.addChildEventListener(object : ChildEventListener {
//            override fun onChildAdded(
//                dataSnapshot: DataSnapshot,
//                s: String?
//            ) {
//                append_chat_conversatin(dataSnapshot)
//            }
//
//            override fun onChildChanged(
//                dataSnapshot: DataSnapshot,
//                s: String?
//            ) {
//                append_chat_conversatin(dataSnapshot)
//            }
//
//            override fun onChildRemoved(dataSnapshot: DataSnapshot) {}
//            override fun onChildMoved(
//                dataSnapshot: DataSnapshot,
//                s: String?
//            ) {
//            }
//
//            override fun onCancelled(databaseError: DatabaseError) {}
//        })
//    }
//
//    private var chat_msg: String? = null
//    private var chat_user_name: String? = null
//    private fun append_chat_conversatin(dataSnapshot: DataSnapshot) {
//        val i: Iterator<*> = dataSnapshot.children.iterator()
//        while (i.hasNext()) {
//            chat_msg = (i.next() as DataSnapshot).value as String?
//            chat_user_name = (i.next() as DataSnapshot).value as String?
//            chat_conversation!!.append("$chat_user_name : $chat_msg\n")
//        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private val mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    if (getRole(this).equals("Guru")) {
                        startActivity(Intent(this, MainActivityGuru::class.java))
                        return@OnNavigationItemSelectedListener true
                    } else if (getRole(this).equals("Murid")) {
                        startActivity(Intent(applicationContext, MainActivity::class.java))
                        return@OnNavigationItemSelectedListener true
                    }
                }
                R.id.nav_profile -> {
                    startActivity(Intent(applicationContext, ProfileActivity::class.java))
                    return@OnNavigationItemSelectedListener true
                }
                R.id.nav_log -> {
                    if (getRole(this).equals("Guru")) {
                        startActivity(Intent(applicationContext, LihatLogGuruActivity::class.java))
                    } else if (getRole(this).equals("Murid")) {
                        startActivity(Intent(applicationContext, LihatLogActivity::class.java))
                    }
                    return@OnNavigationItemSelectedListener true
                }
            }
            true
        }

    fun getRole(context: Context): String {
        mAuth = FirebaseAuth.getInstance()
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