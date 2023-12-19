package com.example.sqlitefixnocap

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide

// DetailWarungActivity
// DetailWarungActivity
class DetailMenu : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_menu)
        val btnEdit = findViewById<Button>(R.id.btnEdit)
        val btnDelete = findViewById<Button>(R.id.btnDelete)

        val idMenu: String? = intent.getStringExtra("MENU_ID")
        val updatedGambar: String? = intent.getStringExtra("MENU_GAMBAR")

        // Dapatkan data warung dengan ID yang diterima dari Intent
        val dbHelper = DBHelper(this)
        val menu: DBHelper.Menu = dbHelper.cariMenu(idMenu) // Mendapatkan objek Warung dari DBHelper

        val idMenuTextView: TextView = findViewById(R.id.textDetailIdMenu)
        val namaMenuTextView: TextView = findViewById(R.id.textDetailNamaMenu)
        val imageDetailLogo: ImageView = findViewById(R.id.imageLogo)
        val imageUrl = menu.gambarmenu
        Log.d("DetailMenu", "Image URL: $imageUrl")

        idMenuTextView.text = "ID Warung: ${menu.idmenu}"
        namaMenuTextView.text = "Nama Warung: ${menu.namamenu}"
        // Set gambar/logo dari warung disini, misalnya menggunakan Picasso atau Glide
        // Contoh menggunakan Picasso:
        // Picasso.get().load(warung.logo).placeholder(R.drawable.default_logo).into(logoImageView)
        // Ganti warung.logo dengan atribut gambar/logo di kelas Warung

        if (!updatedGambar.isNullOrBlank()) {
            Glide.with(this)
                .load(updatedGambar)
                .placeholder(R.drawable.placeholder_image)
                .into(imageDetailLogo)
        } else {
            // Load a default image or handle the case where the URL is empty
            Glide.with(this)
                .load(menu.gambarmenu)
                .placeholder(R.drawable.placeholder_image)
                .into(imageDetailLogo)
        }

        btnEdit.setOnClickListener {
            val intent = Intent(applicationContext, EditMenu::class.java)

            // Mengirim data warung ke EditWarungActivity
            intent.putExtra("MENU_ID", menu.idmenu)
            intent.putExtra("MENU_NAMA", menu.namamenu)
            intent.putExtra("MENU_LOGO", menu.hargamenu)
            intent.putExtra("MENU_GAMBAR", menu.gambarmenu)
            intent.putExtra("MENU_KATEGORI", menu.kategorimenu)

            startActivity(intent)
        }

        btnDelete.setOnClickListener {
            val dbHelper = DBHelper(this)
            dbHelper.hapusMenu(idMenu ?: "")
            Toast.makeText(this@DetailMenu, "Delete successful", Toast.LENGTH_SHORT).show()

            val intent = Intent(applicationContext, ViewMenu::class.java)
            startActivity(intent)
            finish() // Menutup aktivitas DetailWarung setelah penghapusan data
        }
    }
}

