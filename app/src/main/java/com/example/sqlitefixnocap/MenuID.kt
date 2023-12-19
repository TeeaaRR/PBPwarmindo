package com.example.sqlitefixnocap

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sqlitefixnocap.DBHelper
import com.example.sqlitefixnocap.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MenuID : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var dbHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_id)

        recyclerView = findViewById(R.id.recyclerView)
        dbHelper = DBHelper(this)
        val idWarung: String? = intent.getStringExtra("WARUNG_ID")

        val menuList: List<DBHelper.Menu> = dbHelper.getMenuByWarungID(idWarung ?: "")

        // Inisialisasi adapter untuk RecyclerView
        val adapter = MenuAdapter(this, menuList)

        // Atur layout manager untuk RecyclerView menjadi GridLayoutManager dengan 2 kolom
        recyclerView.layoutManager = GridLayoutManager(this, 2)

        // Set adapter ke RecyclerView
        recyclerView.adapter = adapter
    }
}
