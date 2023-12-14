package com.example.sqlitefixnocap

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

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
        private val idMenuTextView: TextView = itemView.findViewById(R.id.textIdMenu)
        private val namaMenuTextView: TextView = itemView.findViewById(R.id.textNamaMenu)
        private val logoImageView: ImageView = itemView.findViewById(R.id.imageLogo)

        fun bind(menu: DBHelper.Menu) {
            idMenuTextView.text = menu.idmenu.toString()
            namaMenuTextView.text = menu.namamenu
            // Set gambar/logo dari warung disini, misalnya menggunakan Picasso atau Glide
            // Contoh menggunakan Picasso:
            // Picasso.get().load(warung.logoUrl).placeholder(R.drawable.default_logo).into(logoImageView)
            // Ganti warung.logoUrl dengan atribut gambar/logo di kelas Warung
        }
    }
}