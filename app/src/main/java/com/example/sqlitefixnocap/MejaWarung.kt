package com.example.sqlitefixnocap

import android.os.Bundle
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

        val mejaList: List<DBHelper.Meja> = dbHelper.getMejaByWarungID(idWarung ?: "")

        // Inisialisasi adapter untuk RecyclerView
        val adapter = MejaAdapter(this, mejaList)

        // Atur layout manager untuk RecyclerView menjadi GridLayoutManager dengan 2 kolom
        recyclerView.layoutManager = GridLayoutManager(this, 1)

        // Set adapter ke RecyclerView
        recyclerView.adapter = adapter
    }
}
