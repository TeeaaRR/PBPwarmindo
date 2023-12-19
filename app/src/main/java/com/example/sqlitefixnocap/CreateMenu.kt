package com.example.sqlitefixnocap

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast

class CreateMenu : AppCompatActivity() {
    private lateinit var DB: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_menu)

        DB = DBHelper(this)
        val textidmenu = findViewById<EditText>(R.id.edIdMenu)
        val textnamamenu = findViewById<EditText>(R.id.edNamaMenu)
        val btnUploadImage = findViewById<Button>(R.id.btnUploadImage)
        val textharga = findViewById<EditText>(R.id.hargaMenu)
        val tambahkan = findViewById<Button>(R.id.btnAddMenu)
        val textkategori = findViewById<Spinner>(R.id.kategoriMenu)
        val textidwarung = findViewById<Spinner>(R.id.idWarungMenu)

        btnUploadImage.setOnClickListener {
            openGalleryGambar()
        }

        val idWarungList = DB.getIdWarung()

        val adapterIDWarung = ArrayAdapter(this, android.R.layout.simple_spinner_item, idWarungList)
        adapterIDWarung.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        textidwarung.adapter = adapterIDWarung

        tambahkan.setOnClickListener {
            val idmenu = textidmenu.text.toString()
            val namamenu = textnamamenu.text.toString()
            val hargamenu = textharga.text.toString()
            val kategorimenu = textkategori.selectedItem.toString()
            val idwarungmenu = textidwarung.selectedItem.toString()

            if (idmenu.isEmpty() || namamenu.isEmpty() || hargamenu.isEmpty() || selectedImageUri == null || kategorimenu.isEmpty() || idwarungmenu.isEmpty()) {
                Toast.makeText(this@CreateMenu, "Please enter all the fields", Toast.LENGTH_SHORT).show()
            } else {
                val gambarmenu = selectedImageUri.toString()
                val insert = DB.insertMenu(idmenu, namamenu, hargamenu, gambarmenu, kategorimenu, idwarungmenu)
                if (insert) {
                    Toast.makeText(this@CreateMenu, "Create Menu successfully", Toast.LENGTH_SHORT).show()
                    val intent = Intent(applicationContext, ViewMenu::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this@CreateMenu, "Create Menu failed", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    var selectedImageUri: Uri? = null

    private val PICK_IMAGE_REQUEST = 1

    private fun openGalleryGambar() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && data != null) {
            when (requestCode) {
                PICK_IMAGE_REQUEST -> {
                    // Ini untuk pemilihan gambar
                    selectedImageUri = data.data // Memperbarui variabel kelas dengan URI gambar yang dipilih
                    // Lakukan sesuatu dengan selectedImageUri
                    // Misalnya: tampilkan path atau nama gambar di TextView
                }
                else -> {
                    // Jika requestCode tidak sesuai dengan yang diharapkan
                    // Tambahkan penanganan kesalahan jika diperlukan
                }
            }
        }
    }
}