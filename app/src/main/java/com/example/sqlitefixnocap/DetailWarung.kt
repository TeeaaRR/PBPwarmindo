package com.example.sqlitefixnocap

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView

class DetailWarung : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_warung)
        val btnEdit = findViewById<Button>(R.id.btnEdit)
        val btnDelete = findViewById<Button>(R.id.btnDelete)
        val updatedLogo: String? = intent?.getStringExtra("WARUNG_LOGO") // Use "WARUNG_LOGO" here
        val idWarung: String? = intent.getStringExtra("WARUNG_ID")
        val dbHelper = DBHelper(this)
        val warung: DBHelper.Warung = dbHelper.cariWarung(idWarung)

        val idWarungTextView: TextView = findViewById(R.id.textDetailIdWarung)
        val namaWarungTextView: TextView = findViewById(R.id.textDetailNamaWarung)
        val imageDetailLogo: ImageView = findViewById(R.id.GambarWarung)
        val imageUrl = warung.gambar
        Log.d("DetailWarung", "Image URL: $imageUrl")

        idWarungTextView.text = "${warung.id}"
        namaWarungTextView.text = "${warung.nama}"

        Glide.with(this)
            .load(warung.gambar)
            .placeholder(R.drawable.placeholder_image)
            .into(imageDetailLogo)

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
            Toast.makeText(this@DetailWarung, "Delete successful", Toast.LENGTH_SHORT).show()

            val intent = Intent(applicationContext, ViewActivity::class.java)
            startActivity(intent)
            finish() // Close the DetailWarung activity after deleting data
        }

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
        }

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView2)
        bottomNavigationView.setSelectedItemId(R.id.nav_detail)

        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId){
                R.id.nav_detail -> {
                    true
                }
                R.id.nav_menu -> {
                    val intent = Intent(this, MenuID::class.java)
                    val bundle = Bundle()

                    bundle.putString("WARUNG_ID", warung.id)

                    intent.putExtras(bundle)
                    startActivity(intent)
                    overridePendingTransition(0, 0)
                    true
                }
                R.id.nav_meja -> {
                    val intent = Intent(this, MejaWarung::class.java)
                    val bundle = Bundle()

                    bundle.putString("WARUNG_ID", warung.id)

                    intent.putExtras(bundle)
                    startActivity(intent)
                    overridePendingTransition(0, 0)
                    true
                }
                else -> false
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                // Handle the back button click
                val intent = Intent(this, ViewActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}

