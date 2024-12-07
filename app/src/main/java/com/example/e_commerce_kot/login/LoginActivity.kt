package com.example.e_commerce_kot.login

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.text.TextUtils
import android.util.Patterns
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.e_commerce_kot.R
import com.example.e_commerce_kot.register.RegisterActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var emailInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var loginButton: Button
    private lateinit var signUp :TextView
    private var isPasswordVisible = false
    private var lastClickTime: Long = 0 // To handle debounce

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_screen)

        emailInput = findViewById(R.id.email_input)
        passwordInput = findViewById(R.id.password_input)
        loginButton = findViewById(R.id.login_btn)
        signUp = findViewById(R.id.signUp_text)
        // Set OnTouchListener for password visibility toggle with debounce
        passwordInput.setOnTouchListener { _, event ->
            val drawableEnd = 2 // Index for drawableEnd in LTR layout
            if (event.action == MotionEvent.ACTION_UP && event.rawX >= (passwordInput.right - passwordInput.compoundDrawables[drawableEnd].bounds.width())) {
                val currentTime = System.currentTimeMillis()
                if (currentTime - lastClickTime < 500) { // 500ms debounce time
                    return@setOnTouchListener true
                }
                lastClickTime = currentTime
                // Toggle password visibility
                isPasswordVisible = !isPasswordVisible
                if (isPasswordVisible) {
                    // Show password
                    passwordInput.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                    passwordInput.setCompoundDrawablesWithIntrinsicBounds(
                        null, null, ContextCompat.getDrawable(this, R.drawable.ic_visibility), null
                    )
                } else {
                    // Hide password
                    passwordInput.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                    passwordInput.setCompoundDrawablesWithIntrinsicBounds(
                        null, null, ContextCompat.getDrawable(this, R.drawable.ic_visibility_off), null
                    )
                }
                passwordInput.setSelection(passwordInput.text.length) // Maintain cursor position
                return@setOnTouchListener true
            }
            false
        }
        // Set onClickListener for login button
        loginButton.setOnClickListener {
            if (validateInputs()) {
                // Proceed with login logic
                Toast.makeText(this, "Login Successful!", Toast.LENGTH_SHORT).show()
            }
        }

        signUp.setOnClickListener{
            val intent = Intent(this ,RegisterActivity::class.java)
            startActivity(intent)
        }
    }
    private fun validateInputs(): Boolean {
        val email = emailInput.text.toString().trim()
        val password = passwordInput.text.toString().trim()

        if (TextUtils.isEmpty(email)) {
            emailInput.error = "Email is required"
            return false
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailInput.error = "Invalid email format"
            return false
        }

        // Validate password
        if (TextUtils.isEmpty(password)) {
            passwordInput.error = "Password is required"
            return false
        }
        if (password.length < 6) {
            passwordInput.error = "Password must be at least 6 characters"
            return false
        }

        return true
    }
}
