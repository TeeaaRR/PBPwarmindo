package com.example.sqlitefixnocap

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MejaWarung : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var dbHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meja_warung)

        recyclerView = findViewById(R.id.recyclerView)
        dbHelper = DBHelper(this)
        val idWarung: String? = intent.getStringExtra("WARUNG_ID")
        val warung: DBHelper.Warung = dbHelper.cariWarung(idWarung)
        val btnCreateMeja = findViewById<FloatingActionButton>(R.id.btnCreateMeja)
        val mejaList: List<DBHelper.Meja> = dbHelper.getMejaByWarungID(idWarung ?: "")

        // Inisialisasi adapter untuk RecyclerView
        val adapter = MejaAdapter(this, mejaList)

        // Atur layout manager untuk RecyclerView menjadi GridLayoutManager dengan 2 kolom
        recyclerView.layoutManager = GridLayoutManager(this, 4)

        // Set adapter ke RecyclerView
        recyclerView.adapter = adapter

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
        }

        btnCreateMeja.setOnClickListener{
            val intent = Intent(applicationContext, CreateMeja::class.java)
            val bundle = Bundle()

            bundle.putString("WARUNG_ID", warung.id)

            intent.putExtras(bundle)
            startActivity(intent)
        }

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView2)
        bottomNavigationView.setSelectedItemId(R.id.nav_meja)

        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId){
                R.id.nav_meja -> {
                    true
                }
                R.id.nav_detail -> {
                    val intent = Intent(this, DetailWarung::class.java)
                    val bundle = Bundle()

                    bundle.putString("WARUNG_ID", warung.id)
                    bundle.putString("WARUNG_NAMA", warung.nama)
                    bundle.putString("WARUNG_LOGO", warung.logo) // Use "WARUNG_LOGO" here
                    bundle.putString("WARUNG_GAMBAR", warung.gambar)

                    intent.putExtras(bundle)
                    startActivity(intent)
                    overridePendingTransition(0, 0)
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
