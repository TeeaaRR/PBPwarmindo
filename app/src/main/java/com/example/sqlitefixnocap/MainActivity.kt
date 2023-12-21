package com.example.sqlitefixnocap

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var username: EditText
    private lateinit var password: EditText
    private lateinit var repassword: EditText
    private lateinit var idrole: Spinner
    private lateinit var signup: Button
    private lateinit var DB: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        username = findViewById(R.id.username)
        password = findViewById(R.id.password)
        repassword = findViewById(R.id.repassword)
        idrole = findViewById(R.id.idrole)
        signup = findViewById(R.id.btnsignup)
        DB = DBHelper(this)

        signup.setOnClickListener {
            val user = username.text.toString()
            val pass = password.text.toString()
            val repass = repassword.text.toString()
            val role = idrole.selectedItem.toString()

            if (user.isEmpty() || pass.isEmpty() || repass.isEmpty() || role.isEmpty()) {
                Toast.makeText(this@MainActivity, "Please enter all the fields", Toast.LENGTH_SHORT).show()
            } else {
                if (pass == repass) {
                    val checkuser = DB.checkUsername(user)
                    if (!checkuser) {
                        val insert = DB.insertData(user, pass, role)
                        if (insert) {
                            Toast.makeText(this@MainActivity, "Registered successfully", Toast.LENGTH_SHORT).show()
                            val intent = Intent(applicationContext, MainActivity::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(this@MainActivity, "Registration failed", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(this@MainActivity, "User already exists! please sign in", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@MainActivity, "Passwords not matching", Toast.LENGTH_SHORT).show()
                }
            }
        }

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.setSelectedItemId(R.id.nav_createUser)

        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId){
                R.id.nav_createUser -> {
                    true
                }
                R.id.nav_menu -> {
                    val intent = Intent(this, ViewMenu::class.java)
                    startActivity(intent)
                    overridePendingTransition(0, 0)
                    false
                }
                R.id.nav_transaksi -> {
                    val intent = Intent(this, TransaksiActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(0, 0)
                    false
                }
                R.id.nav_warung -> {
                    val intent = Intent(this, ViewActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(0, 0)
                    false
                }
                else -> false
            }
        }
    }
}