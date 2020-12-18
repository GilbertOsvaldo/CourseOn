package com.dicoding.picodiploma.courseon

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.fragment_konfirmasi_tambah_jadwal.*
import kotlinx.android.synthetic.main.fragment_konfirmasi_tambah_jadwal.view.*

class FragmentKonfirmasiTambahJadwal : DialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        val get_teks = this.getArguments()?.getString("SENT_TEXT")
        val get_teks2 = this.getArguments()?.getString("SENT_TEXT2")
        val rootView : View = inflater.inflate(R.layout.fragment_konfirmasi_tambah_jadwal,container,false)
        rootView.tv_tanggal.setText("Tanggal : $get_teks")
        rootView.tv_waktu.setText("Waktu : $get_teks2")

        return rootView
    }
}