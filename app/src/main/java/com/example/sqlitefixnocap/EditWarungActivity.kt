// EditWarungActivity.kt

package com.example.sqlitefixnocap

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class EditWarungActivity : AppCompatActivity() {

    private lateinit var etNamaWarung: EditText
    private lateinit var ivLogoWarung: ImageView
    private lateinit var ivGambarWarung: ImageView
    private lateinit var previewNama : TextView
    private lateinit var previewId : TextView
    private lateinit var btnPilihLogo: Button
    private lateinit var btnPilihGambar: Button
    private lateinit var btnSaveEdit: Button

    private var selectedLogoUri: String? = null
    private var selectedGambarUri: String? = null
    private enum class ImageType {
        LOGO, GAMBAR
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_warung)

        etNamaWarung = findViewById(R.id.etNamaWarung)
        ivLogoWarung = findViewById(R.id.ivLogoWarung)
        ivGambarWarung = findViewById(R.id.ivGambarWarung)
        previewNama = findViewById(R.id.previewNama)
        previewId = findViewById(R.id.previewId)
        btnPilihLogo = findViewById(R.id.btnLogoWarung)
        btnPilihGambar = findViewById(R.id.btnGambarWarung)
        btnSaveEdit = findViewById(R.id.saveEdit)

        val idWarung: String? = intent.getStringExtra("WARUNG_ID")
        val namaWarung: String? = intent.getStringExtra("WARUNG_NAMA")
        val logoWarung: String? = intent.getStringExtra("WARUNG_LOGO")
        val gambarWarung: String? = intent.getStringExtra("WARUNG_GAMBAR")

        previewNama.text = "${namaWarung}"
        previewId.text = "${idWarung}"
        etNamaWarung.setText(namaWarung)

        // Load existing logo and gambar using Glide
        Glide.with(this)
            .load(logoWarung)
            .placeholder(R.drawable.placeholder_image)
            .into(ivLogoWarung)

        Glide.with(this)
            .load(gambarWarung)
            .placeholder(R.drawable.placeholder_image)
            .into(ivGambarWarung)

        btnPilihLogo.setOnClickListener {
            openLogoPicker.launch("image/*")
        }

        btnPilihGambar.setOnClickListener {
            openGambarPicker.launch("image/*")
        }

        btnSaveEdit.setOnClickListener {
            val editedNama = etNamaWarung.text.toString()
            val finalLogoUri = selectedLogoUri ?: logoWarung
            val finalGambarUri = selectedGambarUri ?: gambarWarung


            val dbHelper = DBHelper(this)
            dbHelper.updateWarung(idWarung ?: "", editedNama, finalLogoUri ?: "", finalGambarUri ?: "")
            Toast.makeText(this@EditWarungActivity, "Edit Data Successful", Toast.LENGTH_SHORT).show()

            val intent = Intent(applicationContext, ViewActivity::class.java)
            startActivity(intent)
        }
    }

    private val openLogoPicker = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        handleImagePickerResult(uri, ImageType.LOGO)
    }

    private val openGambarPicker = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        handleImagePickerResult(uri, ImageType.GAMBAR)
    }

    private fun handleImagePickerResult(uri: Uri?, imageType: ImageType) {
        // Handle the result from the image picker
        if (uri != null) {
            when (imageType) {
                ImageType.LOGO -> {
                    selectedLogoUri = uri.toString()
                    Glide.with(this)
                        .load(uri)
                        .placeholder(R.drawable.placeholder_image)
                        .into(ivLogoWarung)
                }
                ImageType.GAMBAR -> {
                    selectedGambarUri = uri.toString()
                    Glide.with(this)
                        .load(uri)
                        .placeholder(R.drawable.placeholder_image)
                        .into(ivGambarWarung)
                }
            }
        }
    }
}
