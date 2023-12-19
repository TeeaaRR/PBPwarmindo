package com.example.sqlitefixnocap

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MejaAdapter(private val context: Context, private val mejaList: List<DBHelper.Meja>) :
    RecyclerView.Adapter<MejaAdapter.MejaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MejaViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_meja, parent, false)
        return MejaViewHolder(view)
    }

    override fun onBindViewHolder(holder: MejaViewHolder, position: Int) {
        val meja = mejaList[position]
        holder.bind(meja)

        holder.itemView.setOnClickListener {
            val intent = Intent(context, ViewActivity::class.java)
            val bundle = Bundle()

            bundle.putString("MEJA_ID", meja.kodemeja)
            bundle.putString("MEJA_STATUS", meja.status)

            intent.putExtras(bundle)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return mejaList.size
    }

    inner class MejaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val idMenuTextView: TextView = itemView.findViewById(R.id.textKodeMeja)
        private val namaMenuTextView: TextView = itemView.findViewById(R.id.textStatus)

        fun bind(meja: DBHelper.Meja) {
            idMenuTextView.text = meja.kodemeja.toString()
            namaMenuTextView.text = meja.status
        }
    }
}