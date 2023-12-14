package com.example.sqlitefixnocap

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationView

class Navigation : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        bottomNavigationView.setSelectedItemId(R.id.nav_warung)

        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId){
                R.id.nav_warung -> true
                else -> false
            }
        }
    }
}