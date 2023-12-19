package com.example.sqlitefixnocap

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
class DetailWarung : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_warung)
        val btnEdit = findViewById<Button>(R.id.btnEdit)
        val btnDelete = findViewById<Button>(R.id.btnDelete)
        val btnMenu = findViewById<Button>(R.id.btnMenu)
        val updatedLogo: String? = intent?.getStringExtra("WARUNG_LOGO") // Use "WARUNG_LOGO" here
        val idWarung: String? = intent.getStringExtra("WARUNG_ID")
        val dbHelper = DBHelper(this)
        val warung: DBHelper.Warung = dbHelper.cariWarung(idWarung)

        val idWarungTextView: TextView = findViewById(R.id.textDetailIdWarung)
        val namaWarungTextView: TextView = findViewById(R.id.textDetailNamaWarung)
        val imageDetailLogo: ImageView = findViewById(R.id.imageLogo)
        val imageUrl = warung.gambar
        Log.d("DetailWarung", "Image URL: $imageUrl")

        idWarungTextView.text = "${warung.id}"
        namaWarungTextView.text = "${warung.nama}"

        if (!updatedLogo.isNullOrBlank()) {
            Glide.with(this)
                .load(updatedLogo)
                .placeholder(R.drawable.placeholder_image)
                .into(imageDetailLogo)
        } else {
            // Load a default image or handle the case where the URL is empty
            Glide.with(this)
                .load(warung.gambar)
                .placeholder(R.drawable.placeholder_image)
                .into(imageDetailLogo)
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

        btnMenu.setOnClickListener {
            val intentMenu = Intent(this, MenuID::class.java)
            val bundle = Bundle()

            bundle.putString("WARUNG_ID", warung.id)

            intentMenu.putExtras(bundle)
            startActivity(intentMenu)
        }

        btnDelete.setOnClickListener {
            val dbHelper = DBHelper(this)
            dbHelper.hapusWarung(idWarung ?: "")
            Toast.makeText(this@DetailWarung, "Delete successful", Toast.LENGTH_SHORT).show()

            val intent = Intent(applicationContext, ViewActivity::class.java)
            startActivity(intent)
            finish() // Close the DetailWarung activity after deleting data
        }
    }
}

