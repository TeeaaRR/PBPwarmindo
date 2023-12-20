package com.example.sqlitefixnocap

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class TransaksiActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var dbHelper: DBHelper

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaksi)

        recyclerView = findViewById(R.id.recyclerView)
        dbHelper = DBHelper(this)

        val transaksiList: List<DBHelper.Transaksi> = dbHelper.getAllTransaksi()

        // Inisialisasi adapter untuk RecyclerView
        val adapter = TransaksiAdapter(this, transaksiList)

        // Atur layout manager untuk RecyclerView menjadi GridLayoutManager dengan 2 kolom
        recyclerView.layoutManager = GridLayoutManager(this, 1)

        // Set adapter ke RecyclerView
        recyclerView.adapter = adapter

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.setSelectedItemId(R.id.nav_transaksi)

        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId){
                R.id.nav_transaksi -> {
                    true
                }
                R.id.nav_warung -> {
                    val intent = Intent(this, ViewActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(0, 0)
                    true
                }
                R.id.nav_menu -> {
                    val intent = Intent(this, ViewMenu::class.java)
                    startActivity(intent)
                    overridePendingTransition(0, 0)
                    true
                }
                R.id.nav_meja -> {
                    val intent = Intent(this, CreateMeja::class.java)
                    startActivity(intent)
                    overridePendingTransition(0, 0)
                    true
                }
                else -> false
            }
        }
    }
}
