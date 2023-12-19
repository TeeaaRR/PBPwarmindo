package com.example.sqlitefixnocap

import WarungAdapter
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sqlitefixnocap.DBHelper
import com.example.sqlitefixnocap.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ViewActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var dbHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view)
        val btnCreateWarung = findViewById<FloatingActionButton>(R.id.btnCreateWarung)

        recyclerView = findViewById(R.id.recyclerView)
        dbHelper = DBHelper(this)

        val warungList: List<DBHelper.Warung> = dbHelper.getAllWarungs()

        // Inisialisasi adapter untuk RecyclerView
        val adapter = WarungAdapter(this, warungList)

        // Atur layout manager untuk RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Set adapter ke RecyclerView
        recyclerView.adapter = adapter

        btnCreateWarung.setOnClickListener {
            val intent = Intent(applicationContext, HomeActivity::class.java)
            startActivity(intent)
        }

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.setSelectedItemId(R.id.nav_warung)

        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId){
                R.id.nav_warung -> {
                    true
                }
                R.id.nav_menu -> {
                    val intent = Intent(this, ViewMenu::class.java)
                    startActivity(intent)
                    true
                }
                R.id.nav_meja -> {
                    val intent = Intent(this, CreateMeja::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }
}
