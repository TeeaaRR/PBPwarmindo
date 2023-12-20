package com.example.sqlitefixnocap

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

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
