package com.example.sqlitefixnocap

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class EditMenu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_menu)
        val btnSaveEdit = findViewById<Button>(R.id.saveEdit)

        // Mengambil data warung dari Intent
        val idMenu: String? = intent.getStringExtra("MENU_ID")
        val namaMenu: String? = intent.getStringExtra("MENU_NAMA")
        val hargaMenu: String? = intent.getStringExtra("MENU_LOGO")
        val gambarMenu: String? = intent.getStringExtra("MENU_GAMBAR")
        val kategoriMenu: String? = intent.getStringExtra("MENU_GAMBAR")

        // Gunakan nilai-nilai ini untuk menampilkan data pada form edit
        // Misalnya, jika Anda memiliki EditText dengan ID etNamaWarung di layout Anda:
        val etNamaMenu: EditText = findViewById(R.id.etNamaMenu)
        etNamaMenu.setText(namaMenu)
        val etHargaMenu: EditText = findViewById(R.id.etHargaMenu)
        etHargaMenu.setText(hargaMenu)
        val etGambarMenu: EditText = findViewById(R.id.etGambarMenu)
        etGambarMenu.setText(gambarMenu)
        val etKategoriMenu: EditText = findViewById(R.id.etKategoriMenu)
        etKategoriMenu.setText(kategoriMenu)
        // Lakukan hal yang sama untuk komponen lainnya di dalam form edit

        btnSaveEdit.setOnClickListener{
            val editedNama = etNamaMenu.text.toString()
            val editedHarga = etHargaMenu.text.toString()
            val editedGambar = etGambarMenu.text.toString()
            val editedKategori = etKategoriMenu.text.toString()
            // Ambil nilai-nilai lainnya dari komponen-komponen di form edit

            // Memanggil fungsi updateWarung pada DBHelper
            val dbHelper = DBHelper(this)
            dbHelper.updateMenu(idMenu ?: "", editedNama, editedHarga, editedGambar, editedKategori)
            val intent = Intent(applicationContext, ViewMenu::class.java)
            startActivity(intent)
        }
    }
}
