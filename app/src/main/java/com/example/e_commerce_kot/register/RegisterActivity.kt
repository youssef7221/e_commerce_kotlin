package com.example.e_commerce_kot.register

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.MotionEvent
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.e_commerce_kot.R
import com.example.e_commerce_kot.databinding.ActivityMainBinding
import com.example.e_commerce_kot.databinding.RegisterScreenBinding
import com.example.e_commerce_kot.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {
    private lateinit var emailInput : EditText
    private lateinit var passwordInput : EditText
    private lateinit var nameInput : EditText
    private lateinit var signUpBtn : Button
    private var isPasswordVisible = false
    private var lastClickTime: Long = 0
    private lateinit var binding: RegisterScreenBinding
    private lateinit var fireBaseAuth:FirebaseAuth
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
            binding = RegisterScreenBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root);
        emailInput = findViewById(R.id.email_input)
        passwordInput = findViewById(R.id.password_input)
        nameInput = findViewById(R.id.name_input)
        signUpBtn = findViewById(R.id.signUp_btn)
        fireBaseAuth = FirebaseAuth.getInstance();
        passwordInput.setOnTouchListener { _, event ->
            val drawableEnd = 2 // Index for drawableEnd in LTR layout
            val drawable = passwordInput.compoundDrawables[drawableEnd]

            if (drawable != null && event.action == MotionEvent.ACTION_UP &&
                event.rawX >= (passwordInput.right - drawable.bounds.width())) {

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
        signUpBtn.setOnClickListener{
            if (validateInputs()){
                print(emailInput);
                // Proceed with login logic
                fireBaseAuth.createUserWithEmailAndPassword(emailInput.text.toString(),passwordInput.text.toString()).addOnCompleteListener{
                    if(it.isSuccessful){
                        Toast.makeText(this, "Sign Up Successful!", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this,LoginActivity::class.java);
                            startActivity(intent);
                    }
                    else{
                        Toast.makeText(this,it.exception.toString(),Toast.LENGTH_SHORT).show()
                    }
                };
//
            }
        }
    }
    private fun validateInputs(): Boolean {
        val email = emailInput.text.toString().trim()
        val password = passwordInput.text.toString().trim()
        val name = nameInput.text.toString().trim()
        // Check if the name is empty
        if (TextUtils.isEmpty(name)) {
            nameInput.error = "Name is required"
            return false
        }

        // Check if the name is too short
        if (name.length < 3) {
            nameInput.error = "Name must be at least 3 characters"
            return false
        }
        // Check if the name is too long
        if (name.length > 50) {
            nameInput.error = "Name cannot exceed 50 characters"
            return false
        }
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