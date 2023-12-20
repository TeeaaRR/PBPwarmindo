package com.example.sqlitefixnocap

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.CellIdentityWcdma
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView

class CreateMeja : AppCompatActivity() {
    private lateinit var DB: DBHelper
    private var warungId: String? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_meja)
        DB = DBHelper(this)

        warungId = intent.getStringExtra("WARUNG_ID")
        val textkodemeja = findViewById<EditText>(R.id.edIdMeja)
        val tambahkan = findViewById<Button>(R.id.btnTambahMeja)

        val idWarungList = DB.getIdWarung()

        val adapterIDWarung = ArrayAdapter(this, android.R.layout.simple_spinner_item, idWarungList)
        adapterIDWarung.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        tambahkan.setOnClickListener {
            val kodemeja = textkodemeja.text.toString()
            val idwarung = warungId.toString()

            if (kodemeja.isEmpty() || idwarung.isEmpty()) {
                Toast.makeText(this@CreateMeja, "Please enter all the fields", Toast.LENGTH_SHORT).show()
            } else {
                val insert = DB.insertMeja(kodemeja, idwarung)
                if (insert) {
                    Toast.makeText(this@CreateMeja, "Create Meja successfully", Toast.LENGTH_SHORT).show()
                    val intent = Intent(applicationContext, MejaWarung::class.java)
                    val bundle = Bundle()

                    bundle.putString("WARUNG_ID", idwarung)

                    intent.putExtras(bundle)
                    startActivity(intent)
                } else {
                    Toast.makeText(this@CreateMeja, "Create Meja failed", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}