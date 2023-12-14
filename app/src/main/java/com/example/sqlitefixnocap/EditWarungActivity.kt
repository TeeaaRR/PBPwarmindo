package com.example.sqlitefixnocap

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class EditWarungActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_warung)
        val btnSaveEdit = findViewById<Button>(R.id.saveEdit)

        // Mengambil data warung dari Intent
        val idWarung: String? = intent.getStringExtra("WARUNG_ID")
        val namaWarung: String? = intent.getStringExtra("WARUNG_NAMA")
        val logoWarung: String? = intent.getStringExtra("WARUNG_LOGO")
        val gambarWarung: String? = intent.getStringExtra("WARUNG_GAMBAR")

        // Gunakan nilai-nilai ini untuk menampilkan data pada form edit
        // Misalnya, jika Anda memiliki EditText dengan ID etNamaWarung di layout Anda:
        val etNamaWarung: EditText = findViewById(R.id.etNamaWarung)
        etNamaWarung.setText(namaWarung)
        val etLogoWarung: EditText = findViewById(R.id.etLogoWarung)
        etLogoWarung.setText(logoWarung)
        val etGambarWarung: EditText = findViewById(R.id.etGambarWarung)
        etGambarWarung.setText(gambarWarung)
        // Lakukan hal yang sama untuk komponen lainnya di dalam form edit

        btnSaveEdit.setOnClickListener{
            val editedNama = etNamaWarung.text.toString()
            val editedLogo = etLogoWarung.text.toString()
            val editedGambar = etGambarWarung.text.toString()
            // Ambil nilai-nilai lainnya dari komponen-komponen di form edit

            // Memanggil fungsi updateWarung pada DBHelper
            val dbHelper = DBHelper(this)
            dbHelper.updateWarung(idWarung ?: "", editedNama, editedLogo, editedGambar)
            val intent = Intent(applicationContext, ViewActivity::class.java)
            startActivity(intent)
        }
    }
}
