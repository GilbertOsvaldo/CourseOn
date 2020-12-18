package com.dicoding.picodiploma.courseon

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.item_cardview_pesanan.view.*
import kotlinx.android.synthetic.main.item_cardview_pesanan.view.ib_cancel_log
import kotlinx.android.synthetic.main.item_cardview_pesanan.view.tv_mata_pelajaran_log
import kotlinx.android.synthetic.main.item_cardview_pesanan.view.tv_nama_guru_log
import kotlinx.android.synthetic.main.item_cardview_pesanan.view.tv_status_log
import kotlinx.android.synthetic.main.item_cardview_pesanan.view.tv_tanggal_log
import kotlinx.android.synthetic.main.item_cardview_pesanan.view.tv_waktu_log
import kotlinx.android.synthetic.main.item_cardview_pesanan_guru.view.*


class PemesananGuruAdapter(
    options: FirestoreRecyclerOptions<PemesananModel>,
    val mCtx: Context
) :
    FirestoreRecyclerAdapter<PemesananModel, PemesananGuruAdapter.PemesananAdapterVH>(options) {
    //    internal var guru = arrayListOf<GuruModel>()
    val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    val builder = AlertDialog.Builder(mCtx)
    val inflater = LayoutInflater.from(mCtx)
    val db = Firebase.firestore

    class PemesananAdapterVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var nama = itemView.tv_nama_guru_log
        var matpel = itemView.tv_mata_pelajaran_log
        var tanggal = itemView.tv_tanggal_log
        var waktu = itemView.tv_waktu_log
        var status = itemView.tv_status_log
        var tipe = itemView.tv_tipe_guru_log
        var btnCancel = itemView.ib_cancel_log
        var btnKonfirmasi = itemView.ib_konfirmasi_log
        var btnSelesai = itemView.ib_selesai_log
    }

    override fun onDataChanged() {
        super.onDataChanged()

        if (itemCount == 0) {
            Toast.makeText(mCtx, "Belum ada pesanan", Toast.LENGTH_LONG).show()
        }
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PemesananGuruAdapter.PemesananAdapterVH {
        var viewHolder: PemesananGuruAdapter
        return PemesananAdapterVH(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_cardview_pesanan_guru, parent, false)
        )
    }

    override fun onBindViewHolder(
        holder: PemesananGuruAdapter.PemesananAdapterVH,
        position: Int,
        model: PemesananModel
    ) {
        holder.nama.text = "Nama: " + model.nama_pemesan
        holder.matpel.text = "Matpel : " + model.matpel
        holder.tanggal.text = "Tanggal: " + model.tanggal
        holder.waktu.text = "Waktu: " + model.waktu
        holder.tipe.text = "Tipe: " + model.tipe_pemesanan
        holder.status.text = model.status

        holder.btnCancel.setVisibility(View.GONE)
        holder.btnKonfirmasi.setVisibility(View.GONE)
        holder.btnSelesai.setVisibility(View.GONE)

        holder.itemView.setOnClickListener {
            Toast.makeText(mCtx, model.tipe_pemesanan.toString(), Toast.LENGTH_SHORT).show()
            if (model.tipe_pemesanan.equals("Online") && model.status.equals("Dikonfirmasi")) {
                mCtx.startActivity(Intent(mCtx, Chatroom::class.java))
            }
        }
        if (model.status.equals("Menunggu Konfirmasi")) {
            holder.btnCancel.setVisibility(View.VISIBLE)
            holder.btnKonfirmasi.setVisibility(View.VISIBLE)
            holder.btnCancel.setOnClickListener {
                showDialogCancel(model)
            }
            holder.btnKonfirmasi.setOnClickListener {
                showDialogKonfirmasi(model)
            }
        } else if (model.status.equals("Dikonfirmasi")) {
            holder.btnSelesai.setVisibility(View.VISIBLE)
            holder.btnSelesai.setOnClickListener {
                showDialogSelesai(model)
            }
        }

    }

    private fun showDialogCancel(pemesanan: PemesananModel) {
        val builder = AlertDialog.Builder(mCtx)
        builder.setTitle("Konfirmasi Pembatalan")
        val view = inflater.inflate(R.layout.fragment_pesan_online, null)
        builder.setPositiveButton("Ya") { dialogInterface, id ->
            db.collection("pemesanan").document(pemesanan.pesananId.toString())
                .update("status", "Dibatalkan")
            db.collection("pemesanan").document(pemesanan.pesananId.toString())
                .update("priority", "9")
            Toast.makeText(mCtx, "Pesanan berhasil dibatalkan", Toast.LENGTH_LONG).show()
            view.context.startActivity(Intent(mCtx, LihatLogGuruActivity::class.java))

        }
        builder.setNegativeButton("Tidak") { dialogInterface, id ->
        }
        val alert: AlertDialog = builder.create()
        alert.show()
    }

    private fun showDialogKonfirmasi(pemesanan: PemesananModel) {
        val builder = AlertDialog.Builder(mCtx)
        builder.setTitle("Konfirmasi")
        val view = inflater.inflate(R.layout.fragment_pesan_online, null)
        builder.setPositiveButton("Ya") { dialogInterface, id ->
            db.collection("pemesanan").document(pemesanan.pesananId.toString())
                .update("status", "Dikonfirmasi")
            db.collection("pemesanan").document(pemesanan.pesananId.toString())
                .update("priority", "2")
            Toast.makeText(mCtx, "Pesanan berhasil dikonfirmasi", Toast.LENGTH_LONG).show()
            view.context.startActivity(Intent(mCtx, LihatLogGuruActivity::class.java))

        }
        builder.setNegativeButton("Tidak") { dialogInterface, id ->
        }
        val alert: AlertDialog = builder.create()
        alert.show()
    }

    private fun showDialogSelesai(pemesanan: PemesananModel) {
        val builder = AlertDialog.Builder(mCtx)
        builder.setTitle("Konfirmasi Penyelesaian")
        val view = inflater.inflate(R.layout.fragment_pesan_online, null)
        builder.setPositiveButton("Ya") { dialogInterface, id ->
            db.collection("pemesanan").document(pemesanan.pesananId.toString())
                .update("status", "Selesai")
            db.collection("pemesanan").document(pemesanan.pesananId.toString())
                .update("priority", "3")
            Toast.makeText(mCtx, "Pesanan berhasil diselesaikan", Toast.LENGTH_LONG).show()
            view.context.startActivity(Intent(mCtx, LihatLogGuruActivity::class.java))
        }
        builder.setNegativeButton("Tidak") { dialogInterface, id ->
        }
        val alert: AlertDialog = builder.create()
        alert.show()
    }


}