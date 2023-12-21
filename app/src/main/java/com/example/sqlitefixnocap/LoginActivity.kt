package com.example.sqlitefixnocap

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var username: EditText
    private lateinit var password: EditText
    private lateinit var btnLogin: Button
    private lateinit var DB: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        username = findViewById(R.id.username1)
        password = findViewById(R.id.password1)
        btnLogin = findViewById(R.id.btnsignin1)
        DB = DBHelper(this)

        btnLogin.setOnClickListener {
            val user = username.text.toString()
            val pass = password.text.toString()

            if (user.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this@LoginActivity, "Please enter all the fields", Toast.LENGTH_SHORT).show()
            } else {
                val checkUserPass = DB.checkUsernamePassword(user, pass)
                if (checkUserPass != null) {
                    val role = checkUserPass.role

                    when (role) {
                        "E1" -> {
                            Toast.makeText(this@LoginActivity, "Sign in successful", Toast.LENGTH_SHORT).show()
                            val intent = Intent(applicationContext, ViewActivity::class.java)
                            startActivity(intent)
                        }
                        "E2" -> {
                            Toast.makeText(this@LoginActivity, "Sign in successful", Toast.LENGTH_SHORT).show()
                            val intent = Intent(applicationContext, KasirDashboard::class.java)
                            startActivity(intent)
                        }
                        "E3" -> {
                            Toast.makeText(this@LoginActivity, "Sign in successful", Toast.LENGTH_SHORT).show()
                            val intent = Intent(applicationContext, PengantarDashboard::class.java)
                            startActivity(intent)
                        }
                        "E4" -> {
                            Toast.makeText(this@LoginActivity, "Sign in successful", Toast.LENGTH_SHORT).show()
                            val intent = Intent(applicationContext, DapurDashboard::class.java)
                            startActivity(intent)
                        }
                        // Tambahkan case lain untuk peran lain jika diperlukan
                        else -> {
                            Toast.makeText(this@LoginActivity, "Invalid Role", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Toast.makeText(this@LoginActivity, "Invalid Credentials", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}