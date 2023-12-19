package com.example.sqlitefixnocap

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView

class CreateMeja : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_meja)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.setSelectedItemId(R.id.nav_meja)

        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId){
                R.id.nav_meja -> {
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
                else -> false
            }
        }
    }
}