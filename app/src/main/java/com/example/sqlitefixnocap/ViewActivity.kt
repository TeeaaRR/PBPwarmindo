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

class ViewActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var dbHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view)
        val btnCreateWarung = findViewById<Button>(R.id.btnCreateWarung)
        val btnMenu = findViewById<Button>(R.id.btnMenu)

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

        btnMenu.setOnClickListener {
            val intent = Intent(applicationContext, ViewMenu::class.java)
            startActivity(intent)
        }
    }
}
