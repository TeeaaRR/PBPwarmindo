package com.example.sqlitefixnocap

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class MenuAdapter(private val context: Context, private val menuList: List<DBHelper.Menu>) :
    RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_menu, parent, false)
        return MenuViewHolder(view)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        val menu = menuList[position]
        holder.bind(menu)

        holder.itemView.setOnClickListener {
            val intent = Intent(context, DetailMenu::class.java)
            val bundle = Bundle()

            bundle.putString("MENU_ID", menu.idmenu)
            bundle.putString("MENU_NAMA", menu.namamenu)
            bundle.putString("MENU_HARGA", menu.hargamenu)
            bundle.putString("MENU_GAMBAR", menu.gambarmenu)
            bundle.putString("MENU_KATEGORI", menu.kategorimenu)

            intent.putExtras(bundle)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return menuList.size
    }

    inner class MenuViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val namaMenuTextView: TextView = itemView.findViewById(R.id.textNamaMenu)
        private val hargaMenuTextView: TextView = itemView.findViewById(R.id.hargaMenu)
        private val logoImageView: ImageView = itemView.findViewById(R.id.imageLogo)

        fun bind(menu: DBHelper.Menu) {
            hargaMenuTextView.text = menu.hargamenu.toString()
            namaMenuTextView.text = menu.namamenu

            Glide.with(context)
                .load(menu.gambarmenu)
                .placeholder(R.drawable.placeholder_image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(logoImageView)

            Log.d("WarungAdapter", "Image URL: ${menu.gambarmenu}")
            // Set gambar/logo dari warung disini, misalnya menggunakan Picasso atau Glide
            // Contoh menggunakan Picasso:
            // Picasso.get().load(warung.logoUrl).placeholder(R.drawable.default_logo).into(logoImageView)
            // Ganti warung.logoUrl dengan atribut gambar/logo di kelas Warung
        }
    }
}