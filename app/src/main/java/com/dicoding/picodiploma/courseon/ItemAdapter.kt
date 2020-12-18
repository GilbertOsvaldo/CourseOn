package com.dicoding.picodiploma.courseon

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_matpel.view.*

class ItemAdapter(val itemList: ArrayList<MatpelModel>) :
    RecyclerView.Adapter<ItemAdapter.CardViewHolder>() {
    internal var matpel = arrayListOf<MatpelModel>()

    inner class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(matpel: MatpelModel) {
            with(itemView) {
                Glide.with(itemView.context)
                    .load(matpel.photo)
                    .apply(RequestOptions().override(600, 800))
                    .into(img_matpel)
                tv_matpel.text = matpel.name

                itemView.setOnClickListener {
                    Toast.makeText(
                        itemView.context,
                        "Kamu memilih " + matpel.name,
                        Toast.LENGTH_SHORT
                    ).show()
                    val moveDetail = Intent(itemView.context, MatpelActivity::class.java)
                    moveDetail.putExtra("EXTRA_MATPEL", matpel.name);
                    context.startActivity(moveDetail)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        var view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_matpel, parent, false)
        return CardViewHolder(view)
    }

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

}