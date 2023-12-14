package com.example.sqlitefixnocap

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class HomeActivity : AppCompatActivity() {
    private lateinit var textidwarung: EditText
    private lateinit var textnamawarung: EditText
    private lateinit var textlogo: EditText
    private lateinit var btnUploadImage: Button // Changed from EditText to Button
    private lateinit var tambahkan: Button
    private lateinit var lihatdata: Button
    private lateinit var DB: DBHelper

    private val PICK_IMAGE_REQUEST = 1

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        textidwarung = findViewById(R.id.edIdWarung)
        textnamawarung = findViewById(R.id.edNamaWarung)
        textlogo = findViewById(R.id.logoWarung)
        btnUploadImage = findViewById(R.id.btnUploadImage) // Changed from EditText to Button
        tambahkan = findViewById(R.id.btnAdd)
        lihatdata = findViewById(R.id.btnView)
        DB = DBHelper(this)

        btnUploadImage.setOnClickListener {
            openGallery()
        }

        tambahkan.setOnClickListener {
            val idwarung = textidwarung.text.toString()
            val namawarung = textnamawarung.text.toString()
            val logowarung = textlogo.text.toString()

            if (idwarung.isEmpty() || namawarung.isEmpty() || logowarung.isEmpty() || selectedImageUri == null) {
                Toast.makeText(this@HomeActivity, "Please enter all the fields", Toast.LENGTH_SHORT).show()
            } else {
                val gambarwarung = selectedImageUri.toString() // Use the image URI
                val insert = DB.insertWarung(idwarung, namawarung, logowarung, gambarwarung)

                if (insert) {
                    Toast.makeText(this@HomeActivity, "Create Warung successfully", Toast.LENGTH_SHORT).show()
                    val intent = Intent(applicationContext, ViewActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this@HomeActivity, "Create Warung failed", Toast.LENGTH_SHORT).show()
                }
            }
        }

        lihatdata.setOnClickListener {
            val intent = Intent(applicationContext, ViewActivity::class.java)
            startActivity(intent)
        }
    }

    private var selectedImageUri: Uri? = null

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            selectedImageUri = data.data
            // Optionally, display the selected image's path or name in a TextView or EditText
            // Example: textgambar.setText(selectedImageUri.toString())
        }
    }
}
