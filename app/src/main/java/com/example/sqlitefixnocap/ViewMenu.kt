package com.example.sqlitefixnocap

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ViewMenu : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var dbHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_menu)
        val btnCreateMenu = findViewById<FloatingActionButton>(R.id.btnCreateMenu)

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

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.setSelectedItemId(R.id.nav_menu)

        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId){
                R.id.nav_menu -> {
                    true
                }
                R.id.nav_warung -> {
                    val intent = Intent(this, ViewActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(0, 0)
                    true
                }
                R.id.nav_transaksi -> {
                    val intent = Intent(this, TransaksiActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(0, 0)
                    true
                }
                else -> false
            }
        }
    }
}
