package com.example.sqlitefixnocap

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sqlitefixnocap.DBHelper
import com.example.sqlitefixnocap.R

class ViewMenu : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var dbHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_menu)
        val btnCreateMenu = findViewById<Button>(R.id.btnCreateMenu)
        val btnWarung = findViewById<Button>(R.id.btnWarung)

        recyclerView = findViewById(R.id.recyclerView)
        dbHelper = DBHelper(this)

        val menuList: List<DBHelper.Menu> = dbHelper.getAllMenus()

        // Inisialisasi adapter untuk RecyclerView
        val adapter = MenuAdapter(this, menuList)

        // Atur layout manager untuk RecyclerView menjadi GridLayoutManager dengan 2 kolom
        recyclerView.layoutManager = GridLayoutManager(this, 2)

        // Set adapter ke RecyclerView
        recyclerView.adapter = adapter

        btnCreateMenu.setOnClickListener {
            val intent = Intent(applicationContext, CreateMenu::class.java)
            startActivity(intent)
        }

        btnWarung.setOnClickListener {
            val intent = Intent(applicationContext, ViewActivity::class.java)
            startActivity(intent)
        }
    }
}
