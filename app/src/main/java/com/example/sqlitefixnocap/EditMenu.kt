package com.example.sqlitefixnocap

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide

class EditMenu : AppCompatActivity() {
    private lateinit var DB: DBHelper
    private var selectedGambarUri: String? = null
    private var selectedIDWarung: String? = null
    private lateinit var ivGambarMenu: ImageView
    private enum class ImageType {
        GAMBAR
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_menu)

        val btnSaveEdit = findViewById<Button>(R.id.saveEdit)
        val btnPilihGambar = findViewById<Button>(R.id.btnGambarMenu)
        val textidwarung = findViewById<Spinner>(R.id.idWarungEditMenu)
        ivGambarMenu = findViewById(R.id.ivGambarMenu)




        DB = DBHelper(this)
        val idWarungList = DB.getIdWarung()
        val adapterIDWarung = ArrayAdapter(this, android.R.layout.simple_spinner_item, idWarungList)
        adapterIDWarung.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        textidwarung.adapter = adapterIDWarung

        // Mengambil data warung dari Intent
        val idMenu: String? = intent.getStringExtra("MENU_ID")
        val namaMenu: String? = intent.getStringExtra("MENU_NAMA")
        val hargaMenu: String? = intent.getStringExtra("MENU_LOGO")
        val idWarung: String? = intent.getStringExtra("WARUNG_ID")
        val gambarMenu: String? = intent.getStringExtra("MENU_GAMBAR")
        val kategoriMenu: String? = intent.getStringExtra("MENU_KATEGORI")
        val kategoriArray = resources.getStringArray(R.array.options_array)

        // Gunakan nilai-nilai ini untuk menampilkan data pada form edit
        // Misalnya, jika Anda memiliki EditText dengan ID etNamaWarung di layout Anda:
        val etNamaMenu: EditText = findViewById(R.id.etNamaMenu)
        etNamaMenu.setText(namaMenu)
        val etHargaMenu: EditText = findViewById(R.id.etHargaMenu)
        etHargaMenu.setText(hargaMenu)
        val etKategoriMenu: Spinner = findViewById(R.id.etKategoriMenu)
        val posisiWarung = selectedIDWarung ?: idWarung
        kategoriMenu?.let {
            val posisiItem = kategoriArray.indexOf(it)
            if (posisiItem != -1) {
                etKategoriMenu.setSelection(posisiItem)
            }
        }


        Glide.with(this)
            .load(gambarMenu)
            .placeholder(R.drawable.placeholder_image)
            .into(ivGambarMenu)

        btnPilihGambar.setOnClickListener {
            openGambarPicker.launch("image/*")
        }

        idWarung?.let {
            val adapter = textidwarung.adapter as ArrayAdapter<String>
            val posisiItem = adapter.getPosition(it)
            if (posisiItem != -1) {
                textidwarung.setSelection(posisiItem)
            }
        }

        // Lakukan hal yang sama untuk komponen lainnya di dalam form edit

        btnSaveEdit.setOnClickListener{
            val editedNama = etNamaMenu.text.toString()
            val editedHarga = etHargaMenu.text.toString()
            val editedKategori = etKategoriMenu.selectedItem.toString()
            val idwarungmenu = textidwarung.selectedItem.toString()
            val finalGambarUri = selectedGambarUri ?: gambarMenu
            // Ambil nilai-nilai lainnya dari komponen-komponen di form edit
                // Memanggil fungsi updateMenu pada DBHelper dengan gambar baru
            val dbHelper = DBHelper(this)
            dbHelper.updateMenu(idMenu ?: "", editedNama, editedHarga, finalGambarUri ?: "", editedKategori, idwarungmenu)
            // Memanggil fungsi updateWarung pada DBHelper
            val intent = Intent(applicationContext, ViewMenu::class.java)
            startActivity(intent)
        }
    }

    private val openGambarPicker = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        handleImagePickerResult(uri, EditMenu.ImageType.GAMBAR)
    }

    private fun handleImagePickerResult(uri: Uri?, imageType: EditMenu.ImageType) {
        // Handle the result from the image picker
        if (uri != null) {
            when (imageType) {
                EditMenu.ImageType.GAMBAR -> {
                    selectedGambarUri = uri.toString()
                    Glide.with(this)
                        .load(uri)
                        .placeholder(R.drawable.placeholder_image)
                        .into(ivGambarMenu)
                }
            }
        }
    }
}
