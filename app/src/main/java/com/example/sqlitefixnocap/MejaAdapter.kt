package com.example.sqlitefixnocap

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
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
        private val mejaImageView: ImageView = itemView.findViewById(R.id.meja)


        fun bind(meja: DBHelper.Meja) {
            idMenuTextView.text = meja.kodemeja.toString()

            // Set a click listener to show a confirmation dialog
            mejaImageView.setOnClickListener {
                showConfirmationDialog(meja)
            }
        }

        private fun showConfirmationDialog(meja: DBHelper.Meja) {
            val alertDialog = AlertDialog.Builder(context)
            alertDialog.setTitle("Confirm Delete")
            alertDialog.setMessage("Are you sure you want to delete this item?")

            alertDialog.setPositiveButton("Yes") { _, _ ->
                // Delete the item from the database
                val dbHelper = DBHelper(context)
                dbHelper.hapusMeja(meja.kodemeja)
                val intent = Intent(context, ViewActivity::class.java)
                context.startActivity(intent)
                notifyDataSetChanged()
            }

            alertDialog.setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }

            alertDialog.create().show()
        }
    }
}