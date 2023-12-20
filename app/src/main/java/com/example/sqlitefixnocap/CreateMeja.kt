package com.example.sqlitefixnocap

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView

class CreateMeja : AppCompatActivity() {
    private lateinit var DB: DBHelper
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_meja)
        DB = DBHelper(this)
        val textkodemeja = findViewById<EditText>(R.id.edIdMeja)
        val textidwarung = findViewById<Spinner>(R.id.idWarungMeja)
        val tambahkan = findViewById<Button>(R.id.btnTambahMeja)

        val idWarungList = DB.getIdWarung()

        val adapterIDWarung = ArrayAdapter(this, android.R.layout.simple_spinner_item, idWarungList)
        adapterIDWarung.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        textidwarung.adapter = adapterIDWarung

        tambahkan.setOnClickListener {
            val kodemeja = textkodemeja.text.toString()
            val idwarungmeja = textidwarung.selectedItem.toString()

            if (kodemeja.isEmpty() || idwarungmeja.isEmpty()) {
                Toast.makeText(this@CreateMeja, "Please enter all the fields", Toast.LENGTH_SHORT).show()
            } else {
                val insert = DB.insertMeja(kodemeja, idwarungmeja)
                if (insert) {
                    Toast.makeText(this@CreateMeja, "Create Meja successfully", Toast.LENGTH_SHORT).show()
                    val intent = Intent(applicationContext, CreateMeja::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this@CreateMeja, "Create Meja failed", Toast.LENGTH_SHORT).show()
                }
            }
        }

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