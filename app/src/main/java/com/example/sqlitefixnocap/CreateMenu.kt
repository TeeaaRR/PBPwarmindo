package com.example.sqlitefixnocap

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast

class CreateMenu : AppCompatActivity() {
    private lateinit var DB: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_menu)

        DB = DBHelper(this)
        val textidmenu = findViewById<EditText>(R.id.edIdMenu)
        val textnamamenu = findViewById<EditText>(R.id.edNamaMenu)
        val textgambar = findViewById<EditText>(R.id.gambarMenu)
        val textharga = findViewById<EditText>(R.id.hargaMenu)
        val tambahkan = findViewById<Button>(R.id.btnAddMenu)
        val textkategori = findViewById<Spinner>(R.id.kategoriMenu)

        tambahkan.setOnClickListener {
            val idmenu = textidmenu.text.toString()
            val namamenu = textnamamenu.text.toString()
            val hargamenu = textharga.text.toString()
            val gambarmenu = textgambar.text.toString()
            val kategorimenu = textkategori.selectedItem.toString()

            if (idmenu.isEmpty() || namamenu.isEmpty() || hargamenu.isEmpty() || gambarmenu.isEmpty() || kategorimenu.isEmpty()) {
                Toast.makeText(this@CreateMenu, "Please enter all the fields", Toast.LENGTH_SHORT).show()
            } else {
                val insert = DB.insertMenu(idmenu, namamenu, hargamenu, gambarmenu, kategorimenu)
                if (insert) {
                    Toast.makeText(this@CreateMenu, "Create Menu successfully", Toast.LENGTH_SHORT).show()
                    val intent = Intent(applicationContext, ViewMenu::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this@CreateMenu, "Create Menu failed", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}