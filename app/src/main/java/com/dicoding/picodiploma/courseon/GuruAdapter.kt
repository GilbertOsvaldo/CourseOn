package com.dicoding.picodiploma.courseon

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_konfirmasi_tambah_jadwal.view.*
import kotlinx.android.synthetic.main.fragment_pesan_online.view.*
import kotlinx.android.synthetic.main.item_cardview_guru.*
import kotlinx.android.synthetic.main.item_cardview_guru.view.*
import kotlinx.android.synthetic.main.item_matpel.view.*
import kotlinx.android.synthetic.main.tambah_jadwal.*


class GuruAdapter(
    options: FirestoreRecyclerOptions<GuruModel>,
    val mCtx: Context,
    val matpel: String?
) :
    FirestoreRecyclerAdapter<GuruModel, GuruAdapter.GuruAdapterVH>(options) {
    internal var guru = arrayListOf<GuruModel>()
    val mAuth: FirebaseAuth = FirebaseAuth.getInstance()

    //    private val online
    val builder = AlertDialog.Builder(mCtx)

    //    builder.setTitle("Konfirmasi Data")
    val inflater = LayoutInflater.from(mCtx)

    //    val dialogLayout = inflater.inflate(R.layout.fragment_pesan_online, null)
    class GuruAdapterVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var nama = itemView.tv_nama_guru
        var tanggal = itemView.tv_tanggal_pesanan
        var waktu = itemView.tv_waktu_pesanan
        var btnOnline = itemView.btn_online
        var btnOffline = itemView.btn_offline
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GuruAdapterVH {
        return GuruAdapterVH(
            LayoutInflater.from(parent.context).inflate(R.layout.item_cardview_guru, parent, false)
        )
    }

    override fun onBindViewHolder(
        holder: GuruAdapter.GuruAdapterVH,
        position: Int,
        model: GuruModel
    ) {
        holder.nama.text = "Nama: " + model.nama
        holder.tanggal.text = "Tanggal: " + model.tanggal
        holder.waktu.text = "Waktu: " + model.waktu

        holder.btnOnline.setOnClickListener {
            showDialog(model)
        }
        holder.btnOffline.setOnClickListener {
            showDialog(model)
        }
    }

    private fun showDialog(pemesanan: GuruModel) {

        builder.setTitle("Konfirmasi Data")
        val view = inflater.inflate(R.layout.fragment_pesan_online, null)

        val etNamaGuru = view.findViewById<TextView>(R.id.tv_nama_guru_pesanan_online)
        val etTanggal = view.findViewById<TextView>(R.id.tv_tanggal_online)
        val etWaktu = view.findViewById<TextView>(R.id.tv_waktu_online)

        etTanggal.text = pemesanan.tanggal
        etWaktu.text = pemesanan.waktu
        view.tv_matpel_online.text = matpel
        view.tv_jenis_pemesanan_online.text = "Online"
        builder.setView(view)
        builder.setPositiveButton("SIMPAN") { dialogInterface, id ->
//            saveData()
            val user = mAuth.currentUser
            val db = Firebase.firestore
            val pemesanan = hashMapOf(
                "Tipe Pemesanan" to "Online",
                "alamat_pemesanan" to "-",
                "matpel" to matpel,
                "nama" to etNamaGuru.text.toString(),
                "nama_pemesan" to user?.displayName.toString(),
                "status" to "Menunggu Konfirmasi",
                "tanggal" to etTanggal.text.toString(),
                "waktu" to etWaktu.text.toString()
            )

            db.collection("pemesanan")
                .add(pemesanan)
            Toast.makeText(mCtx, "Berhasil menambahkan jadwal", Toast.LENGTH_LONG).show()
//            startActivity(view.context,Intent(mCtx,MainActivityGuru::class.java))
            view.context.startActivity(Intent(mCtx, LihatLogActivity::class.java))
        }
        builder.setNegativeButton("Batalkan") { dialogInterface, id ->
        }
        val alert: AlertDialog = builder.create()
        alert.show()
    }


}