package com.example.sqlitefixnocap

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
class DetailWarung : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_warung)
        val btnEdit = findViewById<Button>(R.id.btnEdit)
        val btnDelete = findViewById<Button>(R.id.btnDelete)
        val updatedGambar: String? = intent?.getStringExtra("WARUNG_GAMBAR") // Use "WARUNG_LOGO" here
        val idWarung: String? = intent.getStringExtra("WARUNG_ID")
        val dbHelper = DBHelper(this)
        val warung: DBHelper.Warung = dbHelper.cariWarung(idWarung)

        val idWarungTextView: TextView = findViewById(R.id.textDetailIdWarung)
        val namaWarungTextView: TextView = findViewById(R.id.textDetailNamaWarung)
        val imageDetailGambar: ImageView = findViewById(R.id.imageGambar)
        val imageUrl = warung.gambar
        Log.d("DetailWarung", "Image URL: $imageUrl")

        idWarungTextView.text = "ID Warung: ${warung.id}"
        namaWarungTextView.text = "Nama Warung: ${warung.nama}"

        if (!updatedGambar.isNullOrBlank()) {
            Glide.with(this)
                .load(updatedGambar)
                .placeholder(R.drawable.placeholder_image)
                .into(imageDetailGambar)
        } else {
            // Load a default image or handle the case where the URL is empty
            Glide.with(this)
                .load(warung.gambar)
                .placeholder(R.drawable.placeholder_image)
                .into(imageDetailGambar)
        }

        btnEdit.setOnClickListener {
            val intent = Intent(applicationContext, EditWarungActivity::class.java)

            // Send warung data to EditWarungActivity
            intent.putExtra("WARUNG_ID", warung.id)
            intent.putExtra("WARUNG_NAMA", warung.nama)
            intent.putExtra("WARUNG_LOGO", warung.logo)
            intent.putExtra("WARUNG_GAMBAR", warung.gambar)

            startActivity(intent)
        }

        btnDelete.setOnClickListener {
            val dbHelper = DBHelper(this)
            dbHelper.hapusWarung(idWarung ?: "")

            val intent = Intent(applicationContext, ViewActivity::class.java)
            startActivity(intent)
            finish() // Close the DetailWarung activity after deleting data
        }
    }
}

