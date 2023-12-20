package com.example.sqlitefixnocap

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TransaksiAdapter(private val context: Context, private val transaksiList: List<DBHelper.Transaksi>) :
    RecyclerView.Adapter<TransaksiAdapter.TransaksiViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransaksiViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_transaksi, parent, false)
        return TransaksiViewHolder(view)
    }

    override fun onBindViewHolder(holder: TransaksiViewHolder, position: Int) {
        val transaksi = transaksiList[position]
        holder.bind(transaksi)

        holder.itemView.setOnClickListener {
            val intent = Intent(context, TransaksiActivity::class.java)
            val bundle = Bundle()

            bundle.putString("TRANSAKSI_ID", transaksi.idtransaksi)

            intent.putExtras(bundle)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return transaksiList.size
    }

    inner class TransaksiViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val idTransaksiTextView: TextView = itemView.findViewById(R.id.textIdTransaksi)
        private val namaPelangganTextView: TextView = itemView.findViewById(R.id.textNamaPelanggan)
        private val tanggalTextView: TextView = itemView.findViewById(R.id.textTanggal)
        private val hargaTextView: TextView = itemView.findViewById(R.id.textHarga)

        fun bind(transaksi: DBHelper.Transaksi) {
            idTransaksiTextView.text = transaksi.idtransaksi.toString()
            namaPelangganTextView.text = transaksi.namapelanggan
            tanggalTextView.text = transaksi.tanggal
            hargaTextView.text = transaksi.harga
        }
    }
}