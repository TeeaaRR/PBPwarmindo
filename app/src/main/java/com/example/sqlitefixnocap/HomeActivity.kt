package com.example.sqlitefixnocap

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.media.Image
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast

class HomeActivity : AppCompatActivity() {
    private lateinit var textidwarung: EditText
    private lateinit var textnamawarung: EditText
    private lateinit var textlogo: EditText
    private lateinit var btnUploadImage: Button // Changed from EditText to Button
    private lateinit var btnUploadLogo: Button // Changed from EditText to Button
    private lateinit var tambahkan: Button
    private lateinit var lihatdata: Button
    private lateinit var DB: DBHelper

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        textidwarung = findViewById(R.id.edIdWarung)
        textnamawarung = findViewById(R.id.edNamaWarung)
        btnUploadLogo = findViewById(R.id.btnUploadLogo)
        btnUploadImage = findViewById(R.id.btnUploadImage) // Changed from EditText to Button
        tambahkan = findViewById(R.id.btnAdd)
        lihatdata = findViewById(R.id.btnView)
        DB = DBHelper(this)

        btnUploadImage.setOnClickListener {
            openGalleryGambar()
        }

        btnUploadLogo.setOnClickListener {
            openGalleryLogo()
        }

        tambahkan.setOnClickListener {
            val idwarung = textidwarung.text.toString()
            val namawarung = textnamawarung.text.toString()

            if (idwarung.isEmpty() || namawarung.isEmpty() || selectedLogoUri == null || selectedImageUri == null) {
                Toast.makeText(this@HomeActivity, "Please enter all the fields", Toast.LENGTH_SHORT).show()
            } else {
                val gambarwarung = selectedImageUri.toString() // Use the image URI
                val logowarung = selectedLogoUri.toString() // Use the image URI
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

    var selectedImageUri: Uri? = null
    var selectedLogoUri: Uri? = null

    private val PICK_IMAGE_REQUEST = 1
    private val PICK_LOGO_REQUEST = 2

    private fun openGalleryGambar() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    private fun openGalleryLogo() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_LOGO_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && data != null) {
            when (requestCode) {
                PICK_IMAGE_REQUEST -> {
                    // Ini untuk pemilihan gambar
                    selectedImageUri = data.data
                    val previewGambar = findViewById<ImageView>(R.id.previewGambar)
                    previewGambar.setImageURI(selectedImageUri)
                    previewGambar.visibility = View.VISIBLE
                }
                PICK_LOGO_REQUEST -> {
                    // Ini untuk pemilihan logo
                    selectedLogoUri = data.data // Memperbarui variabel kelas dengan URI logo yang dipilih
                    val previewLogo = findViewById<ImageView>(R.id.previewLogo)
                    previewLogo.setImageURI(selectedLogoUri)
                    previewLogo.visibility = View.VISIBLE
                }
                else -> {
                    // Jika requestCode tidak sesuai dengan yang diharapkan
                    // Tambahkan penanganan kesalahan jika diperlukan
                }
            }
        }
    }
}
