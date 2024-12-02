package com.example.e_commerce_kot.register

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.e_commerce_kot.R
import com.example.e_commerce_kot.login.LoginActivity

class RegisterActivity : AppCompatActivity() {
    private lateinit var emailInput : EditText
    private lateinit var passwordInput : EditText
    private lateinit var registerBtn : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.register_screen)

        emailInput = findViewById(R.id.email_input)
        passwordInput = findViewById(R.id.password_input)
        registerBtn = findViewById(R.id.login_btn)
        registerBtn.setOnClickListener {
            // Navigate to RegisterActivity
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}